#ifndef USER_HPP
#define USER_HPP

#include <iostream>
#include <string>
#include <memory>
#include "../Template/MoneyTemplate.hpp"
#include "../ThirdParty/httplib.h"
#include "nlohmann/json.hpp"

using json = nlohmann::json;

class User {
public:
    User(const std::string& host, int port) {
        cli_ = std::make_unique<httplib::Client>(host, port);
        cli_->set_connection_timeout(5, 0); // 5 seconds
        cli_->set_read_timeout(30, 0); // 30 seconds for waiting withdrawals
    }

    void Withdraw(Money::Money amount, const std::string& account) {
        json j;
        j["account"] = account;
        j["amount"] = amount.GetBalance();

        auto res = cli_->Post("/withdraw", j.dump(), "application/json");
        HandleResponse(res, "Withdraw");
    }

    void Save(Money::Money amount, const std::string& account) {
        json j;
        j["account"] = account;
        j["amount"] = amount.GetBalance();

        auto res = cli_->Post("/deposit", j.dump(), "application/json");
        HandleResponse(res, "Save");
    }

    void Transfer(Money::Money amount, const std::string& accountOut, const std::string& accountIn) {
        json j;
        j["from"] = accountOut;
        j["to"] = accountIn;
        j["amount"] = amount.GetBalance();

        auto res = cli_->Post("/transfer", j.dump(), "application/json");
        HandleResponse(res, "Transfer");
    }

private:
    void HandleResponse(const httplib::Result& res, const std::string& operation) {
        if (res) {
            if (res->status == 200) {
                 std::cout << operation << " 操作成功。响应: " << res->body << std::endl;
            } else {
                 std::cerr << "线程 " << std::this_thread::get_id() << ": " << operation << " 操作失败。状态码: " << res->status << ", 响应体: " << res->body << std::endl;
            }
        } else {
            auto err = res.error();
            std::cerr << "线程 " << std::this_thread::get_id() << ": " << operation << " 操作失败。HTTP 错误: " << httplib::to_string(err) << std::endl;
        }
    }

    std::unique_ptr<httplib::Client> cli_;
};

#endif