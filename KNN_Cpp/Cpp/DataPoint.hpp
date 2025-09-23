#ifndef DATAPOINT_H
#define DATAPOINT_H
#include <vector>
#include <string>

class DataPoint {
private:
    std::vector<double> features;
    std::string label;
public:
    DataPoint() = default;
    DataPoint(const std::vector<double>& features) : features(features) {}
    DataPoint(const std::vector<double>& features, const std::string& label) : features(features), label(label) {}

    std::vector<double> GetFeature() const {
        return features;
    }

    void SetFeature(const std::vector<double>& newFeatures) {
        features = newFeatures;
    }

    std::string GetLabel() const {
        return label;
    }

    void SetLabel(const std::string& newLabel) {
        label = newLabel;
    }
};

#endif