//
// Created by Arrebol Lee on 25-9-20.
//

#ifndef TIMETEMPLATE_H
#define TIMETEMPLATE_H

#include "../Untils/Utils.hpp"

namespace TimeTemplate {
    class NLTime {
    public:
        // 自然语言的时间年月日时分秒之类的
        int year{};
        int month{};
        int day{};
        int hour{};
        int minute{};
        int second{};
        int millisecond{};

        // 默认构造函数
        explicit NLTime(int year=1900, int month=1, int day=1, int hour=0, int minute=0, int second=0, int millisecond=0) {
            this->year = year;
            this->day = day;
            this->month = month;
            this->hour = hour;
            this->minute = minute;
            this->second = second;
            this->millisecond = millisecond;
        }

        // 从unix时间戳构造
        explicit NLTime(const int64_t& unixMillisecond) {
            *this = Utils::UnixTime2NLTimeMilisecond(unixMillisecond);
        }
    };
}

#endif