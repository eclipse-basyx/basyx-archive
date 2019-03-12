###############################################
###              tests_json                 ###
###############################################

cmake_minimum_required(VERSION 3.1.0)
project(tests_json)

###''' SOURCE AND HEADER FILES '''###

file(GLOB_RECURSE SOURCE_FILES *.cpp *.cc)
file(GLOB_RECURSE HEADER_FILES *.h)

### tests_json ###

add_executable(tests_json ${SOURCE_FILES} ${HEADER_FILES})
target_include_directories(tests_json PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(tests_json basyx gtest testmain)

diagnostics_print(tests_json)