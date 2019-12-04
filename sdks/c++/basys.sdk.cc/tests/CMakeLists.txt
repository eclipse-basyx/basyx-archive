###############################################
###              Tests                      ###
###############################################

cmake_minimum_required(VERSION 2.8.4)
project(tests)

### tests::main ###

add_library(tests_main support/main.cpp)
target_link_libraries(tests_main gtest)
add_library(tests::main ALIAS tests_main)
diagnostics_print(tests_main)

### UnitTests ###

#add_subdirectory(regression/aas)
add_subdirectory(regression/util)
add_subdirectory(regression/vab)

### Support ###

add_subdirectory(support)

