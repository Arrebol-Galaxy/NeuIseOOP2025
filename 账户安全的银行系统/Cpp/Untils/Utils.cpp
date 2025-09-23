#include "Utils.hpp"
#include "../Template/TimeTemplate.hpp"

namespace Utils {
    TimeTemplate::NLTime UnixTime2NLTimeMilisecond(const int64_t& unixTime) {
        TimeTemplate::NLTime nlTime;

        const auto seconds = static_cast<time_t>(unixTime / 1000);
        const long milliseconds = unixTime % 1000;

        std::tm timeInfo = {};
        if (SafeGmtime(&seconds, &timeInfo)) {
            nlTime.year = timeInfo.tm_year + 1900;
            nlTime.month = timeInfo.tm_mon + 1;
            nlTime.day = timeInfo.tm_mday;
            nlTime.hour = timeInfo.tm_hour;
            nlTime.minute = timeInfo.tm_min;
            nlTime.second = timeInfo.tm_sec;
            nlTime.millisecond = static_cast<int>(milliseconds);
        }

        return nlTime;
    }
}