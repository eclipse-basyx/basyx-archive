###############################################
###              nlohmann_json              ###
###############################################

cmake_minimum_required(VERSION 2.8.4)

# Allow versioning the subproject
if(POLICY CMP0048)
    cmake_policy(SET CMP0048 NEW)
endif(POLICY CMP0048)

# Version from ./nlohmann/json.hpp
set(NLOHMANN_VERSION_MAJOR 3)
set(NLOHMANN_VERSION_MINOR 1)
set(NLOHMANN_VERSION_PATCH 0)

project(nlohmann_json
        VERSION ${NLOHMANN_VERSION_MAJOR}.${NLOHMANN_VERSION_MINOR}.${NLOHMANN_VERSION_PATCH}
)

add_library(nlohmann_json INTERFACE)
target_include_directories(nlohmann_json INTERFACE ${CMAKE_CURRENT_SOURCE_DIR})
add_library(nlohmann_json::nlohmann_json ALIAS nlohmann_json)
