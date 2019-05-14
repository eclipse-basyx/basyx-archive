###############################################
###              Tests                      ###
###############################################

cmake_minimum_required(VERSION 2.8.4)
project(tests)

### testmain ###

add_library(testmain main.cpp)
target_link_libraries(testmain gtest)

### tests ###

#add_subdirectory(submodels)
#add_subdirectory(aas)
add_subdirectory(backends)
add_subdirectory(basysid)
add_subdirectory(fundamentals)
#add_subdirectory(json)
#add_subdirectory(parameter)
add_subdirectory(provider)
add_subdirectory(vab/snippet)

diagnostics_print(testmain)
