###############################################
###              tests_vab                  ###
###############################################

cmake_minimum_required(VERSION 3.1.0)
project(tests_vab)

add_executable(${PROJECT_NAME})

target_sources(${PROJECT_NAME}
    PRIVATE
        test_vabPath.cpp
)

target_include_directories(${PROJECT_NAME} PRIVATE ${PROJECT_SOURCE_DIR} ${BASYX_SOURCE_DIR})
target_link_libraries(${PROJECT_NAME} backends basyx gtest testmain)

gtest_discover_tests(${PROJECT_NAME})

diagnostics_print(${PROJECT_NAME})