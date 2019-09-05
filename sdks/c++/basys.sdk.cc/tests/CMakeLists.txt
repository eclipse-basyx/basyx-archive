###############################################
###              Tests                      ###
###############################################

cmake_minimum_required(VERSION 2.8.4)
project(tests)

### tests::main ###

add_library(testmain support/main.cpp)
target_link_libraries(testmain gtest)
add_library(tests::main ALIAS testmain)
diagnostics_print(testmain)

### UnitTests ###

add_subdirectory(regression/aas)
add_subdirectory(regression/util)
add_subdirectory(regression/vab)

### Support ###

add_subdirectory(support)

