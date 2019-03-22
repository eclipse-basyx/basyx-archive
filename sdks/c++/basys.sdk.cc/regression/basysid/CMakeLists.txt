###############################################
###              tests_basysid              ###
###############################################

cmake_minimum_required(VERSION 3.1.0)
project(tests_basysid)

###''' SOURCE AND HEADER FILES '''###

file(GLOB_RECURSE SOURCE_FILES *.cpp *.cc)
file(GLOB_RECURSE HEADER_FILES *.h)

### tests_basysid ###

add_executable(tests_basysid ${SOURCE_FILES} ${HEADER_FILES})
target_include_directories(tests_basysid PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(tests_basysid basyx gtest testmain)

gtest_discover_tests(tests_basysid)

diagnostics_print(tests_basysid)