//
// Created by Arrebol Lee on 25-9-20.
//

#ifndef MESSAGETEMPLATE_H
#define MESSAGETEMPLATE_H
#include <string>
#include <chrono>
#include "../Untils/Utils.hpp"

// json只有日志用得到那究竟是放Log里面做一个函数式的ToJson还是给LogMessage做一个.2Json呢

class Message {
public:
    // 日志unix时间戳 毫秒
    int64_t time;
    // 消息等级，预留的字段，这会儿没用
    int level;
    // 消息类型，具体的子类再各自确定
    int type;
    // 消息类型映射表，每个子类应该自己实现，否则使用默认的（空）
    std::vector<std::string> messageTypeMap;

    void SetTime() {
        this->time = Utils::GetUnixTimeMilisecond();
    }

    std::string GetType() {
        if (std::find(this->messageTypeMap.begin(), this->messageTypeMap.end(), this->type) != messageTypeMap.end()) {
            return messageTypeMap[type];
        }else {
            return "";
        }
    }

    Message() = default;
};


namespace Message {
    class LogMessage: public Message{
    public:
        LogMessage() : Message() {
            this->level = 2;
            this->type = 0;
            this->SetTime();
        }

        // 日志内容
        std::string content;
    };

    class UserRequireMessage: public Message{
    public:
        UserRequireMessage(const Money::Money initialMoney = Money::Money(), const int type, const std::string& account) : Message() {
            // type: 0: 取钱, 1: 存钱, 2: 转账
            this->level = 2;
            this->type = type;
            this->SetTime();
            this->amount = initialMoney;
            this->account = account;
        }

        // 操作的钱的数量
        Money::Money amount;
        // 操作的账户
        std::string account;
    };
}

#endif //MESSAGETEMPLATE_H