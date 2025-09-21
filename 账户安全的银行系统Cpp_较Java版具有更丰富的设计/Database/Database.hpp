#ifndef DATABASE_H
#define DATABASE_H

#include <string>
#include <unordered_map>
#include <mutex>
#include <condition_variable>
#include <optional>
#include <memory>
#include "../Template/AccountTemplate.hpp"

struct SafeAccount {
    Account::Account account;
    std::mutex mtx;
    std::condition_variable cv;

    SafeAccount(std::string userId, int initialBalance) {
        account.userId = std::move(userId);
        account.balance = Money::Money(initialBalance);
    }
};

class Database {
public:
    bool CreateAccount(const std::string& userId, int initialBalance = 0) {
        std::lock_guard<std::mutex> lock(dbMutex_);
        if (accounts_.count(userId)) {
            // 账户已存在，创建失败
            return false;
        }
        accounts_[userId] = std::make_shared<SafeAccount>(userId, initialBalance);
        // 账户创建成功
        return true;
    }

    std::shared_ptr<SafeAccount> GetAccount(const std::string& userId) {
        std::lock_guard<std::mutex> lock(dbMutex_);
        auto it = accounts_.find(userId);
        if (it != accounts_.end()) {
            return it->second;
        }
        // 账户不存在，返回空指针
        return nullptr;
    }

private:
    // 下划线约定俗成的私有变量吧
    std::unordered_map<std::string, std::shared_ptr<SafeAccount>> accounts_;
    std::mutex dbMutex_;
};

#endif