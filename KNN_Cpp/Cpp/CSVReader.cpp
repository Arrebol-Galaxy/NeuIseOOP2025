#include "CSVReader.hpp"
#include <fstream>
#include <sstream>
#include <iostream>
#include <stdexcept>

std::vector<DataPoint> CSVReader::ReadCSV(const std::string& filename) {
    std::vector<DataPoint> dataPoints;
    std::ifstream file(filename);
    
    if (!file.is_open()) {
        throw std::runtime_error("打不开文件: " + filename);
    }
    
    std::string line;
    // 跳过标题行
    std::getline(file, line);
    
    while (std::getline(file, line)) {
        if (line.empty()) continue;
        
        std::vector<std::string> tokens = SplitLine(line);
        if (tokens.empty()) continue;
        
        std::vector<double> features;

        for (size_t i = 0; i < tokens.size() - 1; ++i) {
            try {
                features.push_back(std::stod(tokens[i]));
            } catch (const std::invalid_argument& e) {
                std::cerr << tokens[i] << "转换不了数字，置零" << std::endl;
                features.push_back(0.0);
            }
        }
        
        // 最后一列作为标签
        std::string label = tokens.back();
        dataPoints.emplace_back(features, label);
    }
    
    file.close();
    return dataPoints;
}

std::vector<std::vector<double>> CSVReader::ParseCSV(const std::string& filename) {
    std::vector<std::vector<double>> data;
    std::ifstream file(filename);
    
    if (!file.is_open()) {
        throw std::runtime_error("打不开文件: " + filename);
    }
    
    std::string line;
    // 跳过标题行（如果有的话）
    std::getline(file, line);
    
    while (std::getline(file, line)) {
        if (line.empty()) continue;
        
        std::vector<std::string> tokens = SplitLine(line);
        if (tokens.empty()) continue;
        
        std::vector<double> row;
        for (const auto& token : tokens) {
            try {
                row.push_back(std::stod(token));
            } catch (const std::invalid_argument& e) {
                std::cerr << token << "转换不了数字，置零" << std::endl;
                row.push_back(0.0);
            }
        }
        
        data.push_back(row);
    }
    
    file.close();
    return data;
}

std::vector<std::string> CSVReader::SplitLine(const std::string& line, char delimiter) {
    std::vector<std::string> tokens;
    std::stringstream ss(line);
    std::string token;
    
    while (std::getline(ss, token, delimiter)) {
        // 去除可能存在的引号
        if (!token.empty() && token.front() == '"' && token.back() == '"') {
            token = token.substr(1, token.length() - 2);
        }
        tokens.push_back(token);
    }
    
    return tokens;
}