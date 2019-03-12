###############################################
###              tests_parameter                 ###
###############################################

cmake_minimum_required(VERSION 3.1.0)
project(tests_parameter)

###''' SOURCE AND HEADER FILES '''###

file(GLOB_RECURSE SOURCE_FILES *.cpp *.cc)
file(GLOB_RECURSE HEADER_FILES *.h)

### tests_parameter ###

add_executable(tests_parameter ${SOURCE_FILES} ${HEADER_FILES})
target_include_directories(tests_parameter PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(tests_parameter basyx gtest testmain)

diagnostics_print(tests_parameter)