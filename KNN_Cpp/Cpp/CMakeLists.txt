cmake_minimum_required(VERSION 3.29)
project(CppMachineLearning)

set(CMAKE_CXX_STANDARD 20)

add_executable(CppMachineLearning main.cpp DataPoint.hpp DistanceMetrics.hpp KNN.hpp KMeans.cpp KMeans.hpp CSVReader.cpp CSVReader.hpp)