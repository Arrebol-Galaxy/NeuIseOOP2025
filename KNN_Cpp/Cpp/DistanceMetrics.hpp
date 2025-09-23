#ifndef DISTANCEMETRICS_H
#define DISTANCEMETRICS_H
#include "DataPoint.hpp"
#include <cmath>

class DistanceMetrics {
public:
    virtual double Calculate(const DataPoint& dp1, const DataPoint& dp2) = 0;
    virtual ~DistanceMetrics() = default;
};

class EuclideanDistance : public DistanceMetrics {
public:
    double Calculate(const DataPoint& dp1, const DataPoint& dp2) override {
        auto features1 = dp1.GetFeature();
        auto features2 = dp2.GetFeature();
        
        if (features1.size() != features2.size()) {
            return -1;
        }
        
        double sum = 0.0;
        for (size_t i = 0; i < features1.size(); ++i) {
            double diff = features1[i] - features2[i];
            sum += diff * diff;
        }
        
        return std::sqrt(sum);
    }
};

class ManhattanDistance : public DistanceMetrics {
public:
    double Calculate(const DataPoint& dp1, const DataPoint& dp2) override {
        auto features1 = dp1.GetFeature();
        auto features2 = dp2.GetFeature();
        
        if (features1.size() != features2.size()) {
            return -1;
        }
        
        double sum = 0.0;
        for (size_t i = 0; i < features1.size(); ++i) {
            sum += std::abs(features1[i] - features2[i]);
        }
        
        return sum;
    }
};

#endif