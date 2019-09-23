###############################################
###              BaSyx C++ SDK              ###
###############################################

cmake_minimum_required(VERSION 3.13.0)
project("BaSyx C++ SDK")

set(BASYX_MAJOR_REV "0")
set(BASYX_MINOR_REV "1")

### CMake settings ###

list(APPEND CMAKE_MODULE_PATH "${CMAKE_CURRENT_LIST_DIR}/cmake")
include(build_source_group)
include(diagnostics_print)
include(enable_clang_format)
include(enable_clang_tidy)
include(enable_cppcheck)
include(enable_playground)

set_property(GLOBAL PROPERTY USE_FOLDERS ON)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)
set(CMAKE_ECLIPSE_MAKE_ARGUMENTS "-C ${CMAKE_BINARY_DIR} -j8" CACHE STRING "Make build arguments" FORCE)

### Project settings ###

message(STATUS "CMAKE_SOURCE_DIR: ${CMAKE_SOURCE_DIR}")
message(STATUS "CMAKE_BINARY_DIR: ${CMAKE_BINARY_DIR}")
message(STATUS "" )

set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/bin)
set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/lib)
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/lib)

### BASYX directories ###

set(BASYX_SOURCE_DIR  "${CMAKE_SOURCE_DIR}/src")
set(BASYX_TEST_DIR    "${CMAKE_SOURCE_DIR}/regression")
set(BASYX_LIBRARY_DIR "${CMAKE_SOURCE_DIR}/lib")

### Build options ###
set(BASYX_BUILD_TESTS ON CACHE BOOL "Build unit tests")
set(BASYX_BUILD_API ON CACHE BOOL "Build BaSyx API")
set(BASYX_UTILITY_PROJECTS ON CACHE BOOL "Create utility targets")
set(BASYX_DEBUG_PRINT_FRAMES OFF CACHE BOOL "Print BaSyx frames")

option(BASYX_BUILD_LIBFMT "Wether to build external, yet included library libfmt" ON)

### Compiler settings ###

set(CMAKE_CXX_STANDARD 11)

### Compile Step Definitions ###

add_compile_definitions(BASYX_MAJOR_REV=${BASYX_MAJOR_REV})
add_compile_definitions(BASYX_MINOR_REV=${BASYX_MINOR_REV})
add_compile_definitions(WIN32_LEAN_AND_MEAN)
##add_compile_definitions(__USE_MINGW_ANSI_STDIO=0)

###############################################
###           Library Settings              ###
###############################################

### Detect OS threading library ###
find_package(Threads)

###############################################
###           Library Directory             ###
###############################################

if(${BASYX_BUILD_LIBFMT})
   add_subdirectory(lib/fmt)
   add_library(lib::fmt ALIAS fmt)
   add_library(lib::fmt-header-only ALIAS fmt-header-only)
else()
   find_package(fmt REQUIRED)
endif()

add_subdirectory(lib/json)

if(${BASYX_BUILD_TESTS})
    add_subdirectory(lib/gtest)
    include(GoogleTest)
endif()

###############################################
###           Source Directory              ###
###############################################

add_subdirectory(src/aas)
add_subdirectory(src/abstraction)
add_subdirectory(src/logging)
add_subdirectory(src/shared)
add_subdirectory(src/utility)
add_subdirectory(src/vab)

###############################################
###           Test Directory                ###
###############################################

if(${BASYX_BUILD_TESTS})
    include( CTest )
    add_subdirectory(tests)
endif()

###############################################
###           Utility Projects              ###
###############################################

if(${BASYX_UTILITY_PROJECTS})
    enable_clang_format()
    enable_clang_tidy()
    enable_cppcheck()
    enable_playground()
endif()
