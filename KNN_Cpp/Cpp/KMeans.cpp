#include "KMeans.hpp"
#include <limits>
#include <cmath>

void KMeans::Fit(std::vector<DataPoint>& dataPoints) {
    if (dataPoints.empty()) {
        return;
    }

    InitializeCentroids(dataPoints);

    for (int iteration = 0; iteration < maxIterations; ++iteration) {
        std::vector<DataPoint> oldCentroids = centroids;
        AssignClusters(dataPoints);
        UpdateCentroids(dataPoints);

        if (HasConverged(oldCentroids)) {
            break;
        }
    }
}

std::vector<int> KMeans::Predict(const std::vector<DataPoint>& dataPoints) {
    std::vector<int> result;
    result.reserve(dataPoints.size());

    for (const auto& point : dataPoints) {
        int closestCluster = 0;
        double minDistance = std::numeric_limits<double>::max();

        for (int i = 0; i < k; ++i) {
            double distance = CalculateDistance(point, centroids[i]);
            if (distance < minDistance) {
                minDistance = distance;
                closestCluster = i;
            }
        }

        result.push_back(closestCluster);
    }

    return result;
}

double KMeans::CalculateDistance(const DataPoint& dp1, const DataPoint& dp2) {
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

void KMeans::InitializeCentroids(const std::vector<DataPoint>& dataPoints) {
    centroids.clear();
    centroids.reserve(k);

    // 随机初始化
    std::uniform_int_distribution<size_t> dist(0, dataPoints.size() - 1);

    for (int i = 0; i < k; ++i) {
        size_t randomIndex = dist(rng);
        centroids.push_back(dataPoints[randomIndex]);
    }
}

void KMeans::AssignClusters(const std::vector<DataPoint>& dataPoints) {
    labels.clear();
    labels.reserve(dataPoints.size());

    for (const auto& point : dataPoints) {
        int closestCluster = 0;
        double minDistance = std::numeric_limits<double>::max();

        for (int i = 0; i < k; ++i) {
            double distance = CalculateDistance(point, centroids[i]);
            if (distance < minDistance) {
                minDistance = distance;
                closestCluster = i;
            }
        }

        labels.push_back(closestCluster);
    }
}

void KMeans::UpdateCentroids(const std::vector<DataPoint>& dataPoints) {
    std::vector<std::vector<double>> newCentroidsFeatures(k);
    std::vector<int> counts(k, 0);

    for (size_t i = 0; i < dataPoints.size(); ++i) {
        int clusterId = labels[i];
        counts[clusterId]++;

        const auto& features = dataPoints[i].GetFeature();
        if (newCentroidsFeatures[clusterId].empty()) {
            newCentroidsFeatures[clusterId].resize(features.size(), 0.0);
        }

        for (size_t j = 0; j < features.size(); ++j) {
            newCentroidsFeatures[clusterId][j] += features[j];
        }
    }

    for (int i = 0; i < k; ++i) {
        if (counts[i] > 0) {
            std::vector<double> avgFeatures(newCentroidsFeatures[i].size());
            for (size_t j = 0; j < newCentroidsFeatures[i].size(); ++j) {
                avgFeatures[j] = newCentroidsFeatures[i][j] / counts[i];
            }
            centroids[i].SetFeature(avgFeatures);
        }
    }
}

bool KMeans::HasConverged(const std::vector<DataPoint>& oldCentroids, double threshold) {
    for (int i = 0; i < k; ++i) {
        double distance = CalculateDistance(centroids[i], oldCentroids[i]);
        if (distance > threshold) {
            return false;
        }
    }
    return true;
}