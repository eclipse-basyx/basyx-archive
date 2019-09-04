###############################################
###              nlohmann_json              ###
###############################################

cmake_minimum_required(VERSION 2.8.4)
project(json)

add_library(json INTERFACE)
target_include_directories(json INTERFACE ${CMAKE_CURRENT_SOURCE_DIR})
add_library(lib::json ALIAS json)
