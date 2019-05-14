###############################################
###              tests_provider             ###
###############################################

cmake_minimum_required(VERSION 3.1.0)
project(tests_provider)

add_executable(tests_provider)

target_sources(tests_provider 
    PRIVATE 
        hashmap/test_hashmap.cpp
		hashmap/test_hashmap_collection.cpp
		hashmap/test_hashmap_map.cpp
    )

target_include_directories(tests_provider PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(tests_provider util basyx abstraction backends gtest testmain)

gtest_discover_tests(tests_provider)

diagnostics_print(tests_provider)
