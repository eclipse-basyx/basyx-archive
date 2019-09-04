###############################################
###              GTest                      ###
###############################################

cmake_minimum_required(VERSION 2.8.4)
project(gtest)

###''' SOURCE AND HEADER FILES '''###

file(GLOB_RECURSE SOURCE_FILES *.cc)
file(GLOB_RECURSE HEADER_FILES *.h)

### GTest ###

add_library(gtest ${SOURCE_FILES} ${HEADER_FILES})
target_include_directories(gtest PUBLIC ${PROJECT_SOURCE_DIR}) 
target_link_libraries(gtest Threads::Threads)
add_library(lib::gtest ALIAS gtest)

diagnostics_print(gtest)
