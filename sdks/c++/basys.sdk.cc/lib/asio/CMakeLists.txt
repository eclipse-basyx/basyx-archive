###############################################
###              ASIO                       ###
###############################################

cmake_minimum_required(VERSION 2.8.4)

# Allow versioning the subproject
if(POLICY CMP0048)
    cmake_policy(SET CMP0048 NEW)
endif(POLICY CMP0048)

# Version from ./asio/version.hpp
set(ASIO_VERSION_MAJOR 1)
set(ASIO_VERSION_MINOR 12)
set(ASIO_VERSION_PATCH 2)

project(asio
        VERSION ${ASIO_VERSION_MAJOR}.${ASIO_VERSION_MINOR}.${ASIO_VERSION_PATCH}
)

add_library(asio INTERFACE)
target_include_directories(asio INTERFACE ${CMAKE_CURRENT_SOURCE_DIR})
target_compile_definitions(asio INTERFACE ASIO_STANDALONE)
add_library(lib::asio ALIAS asio)
