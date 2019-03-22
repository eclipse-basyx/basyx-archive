###############################################
###              tests_bref                 ###
###############################################

cmake_minimum_required(VERSION 3.1.0)
project(tests_bref)

###''' SOURCE AND HEADER FILES '''###

file(GLOB_RECURSE SOURCE_FILES *.cpp *.cc)
file(GLOB_RECURSE HEADER_FILES *.h)

### tests_bref ###

add_executable(tests_bref ${SOURCE_FILES} ${HEADER_FILES} )
target_include_directories(tests_bref PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(tests_bref basyx gtest testmain)

gtest_discover_tests(tests_bref)

diagnostics_print(tests_bref)
