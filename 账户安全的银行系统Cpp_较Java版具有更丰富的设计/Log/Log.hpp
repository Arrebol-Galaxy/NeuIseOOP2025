#ifndef LOG_H
#define LOG_H

#include <string>
#include <fstream>
#include <mutex>
#include <iostream>
#include "../Untils/Utils.hpp"
#include "../Template/TimeTemplate.hpp"

class Log {
public:
    Log(const std::string& filename = "transaction_log.txt") : logFile_(filename, std::ios::app) {
        if (!logFile_.is_open()) {
            std::cerr << "无法打开日志文件: " << filename << std::endl;
        }
    }

    // Cpp的多线程希望不要炸
    void LogTransaction(const std::string& message) {
        std::lock_guard<std::mutex> lock(mtx_);
        if (logFile_.is_open()) {
            auto now = Utils::GetUnixTimeMilisecond();
            TimeTemplate::NLTime nlTime = Utils::UnixTime2NLTimeMilisecond(now);

            // YYYY-MM-DD HH:MM:SS.ms
            char timeBuffer[100];
            snprintf(timeBuffer, sizeof(timeBuffer), "%04d-%02d-%02d %02d:%02d:%02d.%03d",
                     nlTime.year, nlTime.month, nlTime.day,
                     nlTime.hour, nlTime.minute, nlTime.second, nlTime.millisecond);

            logFile_ << "[" << timeBuffer << "] " << message << std::endl;
        }
    }

private:
    std::ofstream logFile_;
    std::mutex mtx_;
};

#endif