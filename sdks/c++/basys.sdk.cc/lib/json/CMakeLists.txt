###############################################
###              nlohmann_json              ###
###############################################

cmake_minimum_required(VERSION 2.8.4)
project(nlohmann_json)

add_library(nlohmann_json INTERFACE)
target_include_directories(nlohmann_json INTERFACE ${CMAKE_CURRENT_SOURCE_DIR})
add_library(nlohmann_json::nlohmann_json ALIAS nlohmann_json)
