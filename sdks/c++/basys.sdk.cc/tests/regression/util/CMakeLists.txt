###############################################
###              tests_util         ###
###############################################

cmake_minimum_required(VERSION 3.1.0)
project(tests_util)

add_executable(tests_util)

target_sources(tests_util
    PRIVATE
        fundamentals/test_basyx_any.cc
        fundamentals/test_basyx_array.cc
		    test_AnyTypeChecker.cpp
)

target_link_libraries(tests_util basyx::util basyx::shared lib::gtest tests::main)
gtest_discover_tests(tests_util)

diagnostics_print(tests_util)
