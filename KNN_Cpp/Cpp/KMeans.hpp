#ifndef KMEANS_H
#define KMEANS_H

#include <vector>
#include <random>
#include "DataPoint.hpp"
#include "DistanceMetrics.hpp"

class KMeans {
private:
    int k;                                  // 聚类数
    int maxIterations;                      // 最大迭代次数
    std::vector<DataPoint> centroids;       // 聚类中心
    std::vector<int> labels;                // 每个数据点的标签
    std::mt19937 rng;                       // 随机数生成器

public:
    KMeans(int k, int maxIterations = 100) : k(k), maxIterations(maxIterations), rng(std::random_device{}()) {}

    void Fit(std::vector<DataPoint>& dataPoints);

    std::vector<int> Predict(const std::vector<DataPoint>& dataPoints);

    std::vector<DataPoint> GetCentroids() const { return centroids; }

private:
    double CalculateDistance(const DataPoint& dp1, const DataPoint& dp2);

    void InitializeCentroids(const std::vector<DataPoint>& dataPoints);

    void AssignClusters(const std::vector<DataPoint>& dataPoints);

    void UpdateCentroids(const std::vector<DataPoint>& dataPoints);

    bool HasConverged(const std::vector<DataPoint>& oldCentroids, double threshold = 1e-6);
};

#endif