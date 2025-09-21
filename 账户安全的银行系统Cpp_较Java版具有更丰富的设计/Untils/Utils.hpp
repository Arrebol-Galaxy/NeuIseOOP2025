//
// Created by Arrebol Lee on 25-9-20.
//

#ifndef UTILS_H
#define UTILS_H

#include <cstdint>
#include <chrono>

// 前向声明打破循环依赖
namespace TimeTemplate {
    class NLTime;
}

namespace Utils {
    // 跨平台会炸吗只在MacOS测试过了
    inline bool SafeGmtime(const time_t* timep, std::tm* result) {
#ifdef _WIN32
        // Windows: 使用 gmtime_s
        return gmtime_s(result, timep) == 0;
#else
        // macOS/Linux: 使用 POSIX gmtime_r
        return gmtime_r(timep, result) != nullptr;
#endif
    }

    TimeTemplate::NLTime UnixTime2NLTimeMilisecond(const int64_t& unixTime);

    inline int64_t GetUnixTimeMilisecond() {
        const auto ms = std::chrono::duration_cast<std::chrono::milliseconds>(
                std::chrono::system_clock::now().time_since_epoch()
            );

        return ms.count();
    }
}

#endif //UTILS_H