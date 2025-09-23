#ifndef KNN_H
#define KNN_H
#include <vector>
#include <string>
#include <algorithm>
#include <map>

#include "DataPoint.hpp"
#include "DistanceMetrics.hpp"

class KNN {
private:
    int k;
    std::vector<DataPoint> trainingSet;
    DistanceMetrics* metrics_;

public:
    KNN(int k = 5) : k(k) {
        metrics_ = new EuclideanDistance();
    }
    
    ~KNN() {
        delete metrics_;
    }

    void Fit(const std::vector<DataPoint>& points) {
        trainingSet = points;
    }

    std::string Predict(const DataPoint& point) {
        // 计算与所有训练样本的距离
        std::vector<std::pair<double, std::string>> distances;
        
        for (const auto& trainPoint : trainingSet) {
            double distance = metrics_->Calculate(point, trainPoint);
            distances.push_back({distance, trainPoint.GetLabel()});
        }
        
        // 按距离排序
        std::sort(distances.begin(), distances.end());
        
        // 选择k个最近邻
        std::map<std::string, int> classCount;
        for (int i = 0; i < k && i < static_cast<int>(distances.size()); ++i) {
            classCount[distances[i].second]++;
        }
        
        // 找到出现次数最多的类别
        std::string predictedClass;
        int maxCount = 0;
        
        for (const auto& pair : classCount) {
            if (pair.second > maxCount) {
                maxCount = pair.second;
                predictedClass = pair.first;
            }
        }
        
        return predictedClass;
    }

    std::vector<std::string> PredictBatch(const std::vector<DataPoint>& points) {
        std::vector<std::string> predictions;
        for (const auto& point : points) {
            predictions.push_back(Predict(point));
        }
        return predictions;
    }
    
    double PredictRegression(const DataPoint& point) {
        // 计算与所有训练样本的距离
        std::vector<std::pair<double, double>> distances;
        
        for (const auto& trainPoint : trainingSet) {
            double distance = metrics_->Calculate(point, trainPoint);
            double labelValue = std::stod(trainPoint.GetLabel());
            distances.push_back({distance, labelValue});
        }
        
        // 按距离排序
        std::sort(distances.begin(), distances.end());
        
        // 选择k个最近邻并计算加权平均
        double sum = 0.0;
        double weightSum = 0.0;
        
        for (int i = 0; i < k && i < static_cast<int>(distances.size()); ++i) {
            // 使用距离的倒数作为权重
            double weight = 1.0 / (distances[i].first + 1e-10); // 避免除零
            sum += distances[i].second * weight;
            weightSum += weight;
        }
        
        return sum / weightSum;
    }
    
    std::vector<double> PredictRegressionBatch(const std::vector<DataPoint>& points) {
        std::vector<double> predictions;
        for (const auto& point : points) {
            predictions.push_back(PredictRegression(point));
        }
        return predictions;
    }
};

#endif