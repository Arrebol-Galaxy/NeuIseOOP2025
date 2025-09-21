//
// Created by Arrebol Lee on 25-9-21.
//

#ifndef ACCOUNTTEMPLATE_H
#define ACCOUNTTEMPLATE_H
#include <string>
#include <unordered_map>
#include "MoneyTemplate.hpp"

namespace Account {
    class Account {
    public:
        std::string userId;
        Money::Money balance;
        std::unordered_map<std::string, std::string> properties; // 这玩意儿好像用不到
    };
}



#endif //ACCOUNTTEMPLATE_H