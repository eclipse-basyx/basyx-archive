###############################################
###              tests_fundamentals         ###
###############################################

cmake_minimum_required(VERSION 3.1.0)
project(tests_fundamentals)

add_executable(tests_fundamentals)

target_sources(tests_fundamentals
    PRIVATE
        test_basyx_any.cc
        test_basyx_array.cc
)

target_link_libraries(tests_fundamentals basyx util gtest testmain)
gtest_discover_tests(tests_fundamentals)

diagnostics_print(tests_fundamentals)
