#ifndef CSVREADER_H
#define CSVREADER_H

#include <vector>
#include <string>
#include "DataPoint.hpp"

class CSVReader {
public:
    static std::vector<DataPoint> ReadCSV(const std::string& filename);
    static std::vector<std::vector<double>> ParseCSV(const std::string& filename);
    static std::vector<std::string> SplitLine(const std::string& line, char delimiter = ',');
};

#endif //CSVREADER_H