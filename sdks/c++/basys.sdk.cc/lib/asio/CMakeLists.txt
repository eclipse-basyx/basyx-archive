###############################################
###              ASIO                       ###
###############################################

cmake_minimum_required(VERSION 2.8.4)
project(asio)

add_library(asio INTERFACE)
target_include_directories(asio INTERFACE ${CMAKE_CURRENT_SOURCE_DIR})
target_compile_definitions(asio INTERFACE ASIO_STANDALONE)
add_library(lib::asio ALIAS asio)