###############################################
###              tests_typertti                 ###
###############################################

cmake_minimum_required(VERSION 3.1.0)
project(tests_typertti)

###''' SOURCE AND HEADER FILES '''###

file(GLOB_RECURSE SOURCE_FILES *.cpp *.cc)
file(GLOB_RECURSE HEADER_FILES *.h)

### tests_typertti ###

add_executable(tests_typertti ${SOURCE_FILES} ${HEADER_FILES})
target_include_directories(tests_typertti PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(tests_typertti basyx gtest testmain)

diagnostics_print(tests_typertti)