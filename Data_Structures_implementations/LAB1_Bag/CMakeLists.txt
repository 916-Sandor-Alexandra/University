cmake_minimum_required(VERSION 3.17)
project(LAB1_Bag)

set(CMAKE_CXX_STANDARD 14)

add_executable(LAB1_Bag
        App.cpp
        Bag.cpp
        Bag.h
        BagIterator.cpp
        BagIterator.h
        ExtendedTest.cpp
        ExtendedTest.h
        ShortTest.cpp
        ShortTest.h)