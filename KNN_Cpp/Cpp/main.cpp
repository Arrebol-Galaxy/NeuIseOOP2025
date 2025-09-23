#include <iostream>
#include <vector>
#include <algorithm>
#include <random>
#include "DataPoint.hpp"
#include "KNN.hpp"
#include "CSVReader.hpp"

// 标准化数据
void StandardizeData(std::vector<DataPoint>& data) {
    if (data.empty()) return;

    size_t featureCount = data[0].GetFeature().size();
    std::vector<double> means(featureCount, 0.0);
    std::vector<double> stdDevs(featureCount, 0.0);

    // 计算均值
    for (const auto& point : data) {
        auto features = point.GetFeature();
        for (size_t i = 0; i < featureCount; ++i) {
            means[i] += features[i];
        }
    }

    for (double& mean : means) {
        mean /= data.size();
    }

    // 计算标准差
    for (const auto& point : data) {
        auto features = point.GetFeature();
        for (size_t i = 0; i < featureCount; ++i) {
            double diff = features[i] - means[i];
            stdDevs[i] += diff * diff;
        }
    }

    for (double& stdDev : stdDevs) {
        stdDev = std::sqrt(stdDev / (data.size() - 1));
    }

    // 标准化数据
    for (auto& point : data) {
        auto features = point.GetFeature();
        for (size_t i = 0; i < featureCount; ++i) {
            if (stdDevs[i] > 0) {
                features[i] = (features[i] - means[i]) / stdDevs[i];
            }
        }
        point.SetFeature(features);
    }
}

// 随机分割数据集
void SplitData(std::vector<DataPoint>& data,
               std::vector<DataPoint>& trainData,
               std::vector<DataPoint>& testData,
               double trainRatio = 0.8) {
    std::random_device rd;
    std::mt19937 g(rd());
    std::shuffle(data.begin(), data.end(), g);

    size_t trainSize = static_cast<size_t>(data.size() * trainRatio);
    trainData.assign(data.begin(), data.begin() + trainSize);
    testData.assign(data.begin() + trainSize, data.end());
}

int main() {
    try {
        auto housingData = CSVReader::ReadCSV("/Users/arrebol-galaxy/Documents/想法/CppMachineLearning/data/Boston_house_data.csv");
        StandardizeData(housingData);

        // 分割
        std::vector<DataPoint> trainData, testData;
        SplitData(housingData, trainData, testData, 0.8);

        std::cout << "训练集大小: " << trainData.size() << std::endl;
        std::cout << "测试集大小: " << testData.size() << std::endl;

        KNN knnRegressor(5);
        knnRegressor.Fit(trainData);

        // 测试集预测
        std::vector<double> predictions = knnRegressor.PredictRegressionBatch(testData);

        std::cout << "\nKNN回归结果:" << std::endl;
        std::cout << "实际值\t\t预测值\t\t误差" << std::endl;

        double mse = 0.0;
        double mae = 0.0;
        for (size_t i = 0; i < testData.size(); ++i) {
            double actual = std::stod(testData[i].GetLabel());
            double error = actual - predictions[i];
            mse += error * error;
            mae += std::abs(error);
            std::cout << actual << "\t\t" << predictions[i] << "\t\t" << error << std::endl;
        }

        mse /= testData.size();
        mae /= testData.size();
        double rmse = std::sqrt(mse);

        std::cout << "均方根误差 (RMSE): " << rmse << std::endl;
        std::cout << "平均绝对误差 (MAE): " << mae << std::endl;

    } catch (const std::exception& e) {
        std::cerr << "有毛病: " << e.what() << std::endl;
        return 1;
    }

    return 0;
}
