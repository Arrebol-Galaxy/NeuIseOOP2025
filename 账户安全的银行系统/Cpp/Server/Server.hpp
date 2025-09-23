#ifndef SERVER_H
#define SERVER_H

#include "../ThirdParty/httplib.h"
#include "../Database/Database.hpp"
#include "../Log/Log.hpp"
#include "nlohmann/json.hpp"
#include <string>
#include <memory>
#include <chrono>

using json = nlohmann::json;

class Server {
public:
    Server(const std::string& host, int port) : svr_(), db_(), log_(), host_(host), port_(port) {
        SetupRoutes();
    }

    void Start() {
        isRunning_ = true;
        svr_.listen(host_.c_str(), port_);
    }

    void Stop() {
        if (svr_.is_running()) {
            svr_.stop();
        }
        isRunning_ = false;
    }

    void WaitUntilReady() const {
        while (!svr_.is_running() && isRunning_) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
        }
    }

    Database& GetDatabase() {
        return db_;
    }

private:
    void SetupRoutes();

    httplib::Server svr_;
    Database db_;
    Log log_;
    std::string host_;
    int port_;
    std::atomic<bool> isRunning_{false};
};

void Server::SetupRoutes() {
    svr_.Post("/deposit", [&](const httplib::Request& req, httplib::Response& res) {
        try {
            auto j = json::parse(req.body);
            std::string accountId = j.at("account");
            int amount = j.at("amount");

            if (amount <= 0) {
                res.status = 400;
                res.set_content(R"({"status":"error", "message":"存款金额必须为正数"})", "application/json");
                return;
            }

            auto safeAccount = db_.GetAccount(accountId);
            if (!safeAccount) {
                res.status = 404;
                res.set_content(R"({"status":"error", "message":"账户未找到"})", "application/json");
                return;
            }

            {
                std::lock_guard<std::mutex> lock(safeAccount->mtx);
                safeAccount->account.balance.Add(amount);
                log_.LogTransaction("存款: 账户 " + accountId + ", 金额: " + std::to_string(amount) + ", 余额: " + std::to_string(safeAccount->account.balance.GetBalance()));
                safeAccount->cv.notify_all(); // Notify waiting withdrawal threads
            }

            json responseJson;
            responseJson["status"] = "success";
            responseJson["account"] = accountId;
            responseJson["new_balance"] = safeAccount->account.balance.GetBalance();
            res.set_content(responseJson.dump(), "application/json");

        } catch (const json::exception& e) {
            res.status = 400;
            res.set_content(std::string(R"({"status":"error", "message":"JSON格式错误: )") + e.what() + R"("})", "application/json");
        }
    });

    svr_.Post("/withdraw", [&](const httplib::Request& req, httplib::Response& res) {
        try {
            auto j = json::parse(req.body);
            std::string accountId = j.at("account");
            int amount = j.at("amount");
            bool waitFlag = j.value("wait", true); // Extended Task: allow user to not wait

            if (amount <= 0) {
                res.status = 400;
                res.set_content(R"({"status":"error", "message":"取款金额必须为正数"})", "application/json");
                return;
            }

            auto safeAccount = db_.GetAccount(accountId);
            if (!safeAccount) {
                res.status = 404;
                res.set_content(R"({"status":"error", "message":"账户未找到"})", "application/json");
                return;
            }

            {
                std::unique_lock<std::mutex> lock(safeAccount->mtx);
                if (safeAccount->account.balance.GetBalance() < amount) {
                    if (!waitFlag) {
                        res.status = 400;
                        res.set_content(R"({"status":"failure", "message":"余额不足且客户端选择不等待"})", "application/json");
                        log_.LogTransaction("取款失败: 账户 " + accountId + ", 金额: " + std::to_string(amount) + ", 原因: 余额不足 (不等待)");
                        return;
                    }

                    log_.LogTransaction("取款等待: 账户 " + accountId + ", 金额: " + std::to_string(amount) + ", 原因: 余额不足，正在等待...");
                    // Wait until balance is sufficient
                    safeAccount->cv.wait(lock, [&] {
                        return safeAccount->account.balance.GetBalance() >= amount;
                    });
                }

                safeAccount->account.balance.Add(-amount);
                log_.LogTransaction("取款成功: 账户 " + accountId + ", 金额: " + std::to_string(amount) + ", 余额: " + std::to_string(safeAccount->account.balance.GetBalance()));
            }

            json responseJson;
            responseJson["status"] = "success";
            responseJson["account"] = accountId;
            responseJson["new_balance"] = safeAccount->account.balance.GetBalance();
            res.set_content(responseJson.dump(), "application/json");

        } catch (const json::exception& e) {
            res.status = 400;
            res.set_content(std::string(R"({"status":"error", "message":"JSON格式错误: )") + e.what() + R"("})", "application/json");
        }
    });

    svr_.Post("/transfer", [&](const httplib::Request& req, httplib::Response& res) {
        try {
            auto j = json::parse(req.body);
            std::string fromId = j.at("from");
            std::string toId = j.at("to");
            int amount = j.at("amount");

            if (amount <= 0) {
                 res.status = 400;
                 res.set_content(R"({"status":"error", "message":"转账金额必须为正数"})", "application/json");
                 return;
            }
            if (fromId == toId) {
                 res.status = 400;
                 res.set_content(R"({"status":"error", "message":"源账户和目标账户不能相同"})", "application/json");
                 return;
            }

            auto fromAccount = db_.GetAccount(fromId);
            auto toAccount = db_.GetAccount(toId);

            if (!fromAccount || !toAccount) {
                 res.status = 404;
                 res.set_content(R"({"status":"error", "message":"一个或两个账户都未找到"})", "application/json");
                 return;
            }

            std::unique_lock<std::mutex> lockFrom(fromAccount->mtx, std::defer_lock);
            std::unique_lock<std::mutex> lockTo(toAccount->mtx, std::defer_lock);
            std::lock(lockFrom, lockTo);

            if (fromAccount->account.balance.GetBalance() < amount) {
                res.status = 400;
                res.set_content(R"({"status":"error", "message":"转账余额不足"})", "application/json");
                log_.LogTransaction("转账失败: 从 " + fromId + " 到 " + toId + ", 金额: " + std::to_string(amount) + ", 原因: 余额不足");
                return;
            }

            fromAccount->account.balance.Add(-amount);
            toAccount->account.balance.Add(amount);

            log_.LogTransaction("转账成功: 从 " + fromId + " 到 " + toId + ", 金额: " + std::to_string(amount));

            toAccount->cv.notify_all();

            json responseJson;
            responseJson["status"] = "success";
            responseJson["from_account"] = {{"id", fromId}, {"new_balance", fromAccount->account.balance.GetBalance()}};
            responseJson["to_account"] = {{"id", toId}, {"new_balance", toAccount->account.balance.GetBalance()}};
            res.set_content(responseJson.dump(), "application/json");

        } catch (const json::exception& e) {
             res.status = 400;
             res.set_content(std::string(R"({"status":"error", "message":"JSON格式错误: )") + e.what() + R"("})", "application/json");
        }
    });
}

#endif //SERVER_H