###############################################
###              BaSyx C++ SDK              ###
###############################################
cmake_minimum_required(VERSION 3.13.0)

### CMake settings ###
list(APPEND CMAKE_MODULE_PATH "${CMAKE_CURRENT_LIST_DIR}/cmake")
include(build_source_group)
include(diagnostics_print)
include(enable_clang_format)
include(enable_clang_tidy)
include(enable_cppcheck)
include(enable_testbed)
include(GNUInstallDirs)

set (BASYX_PACKAGE_VERSION_MAJOR "0")
set (BASYX_PACKAGE_VERSION_MINOR "1")
set (BASYX_PACKAGE_VERSION_PATCH "0")

set (PROJECT_NAME "BaSyx C++ SDK")
set (PROJECT_SHORTNAME "BaSyx")

project (${PROJECT_NAME} 
        VERSION ${BASYX_PACKAGE_VERSION_MAJOR}.${BASYX_PACKAGE_VERSION_MINOR}.${BASYX_PACKAGE_VERSION_PATCH}
        )

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

### BaSyx directories ###
set(BASYX_SOURCE_DIR  "${CMAKE_SOURCE_DIR}/src")
set(BASYX_INCLUDE_DIR "${CMAKE_SOURCE_DIR}/include")
set(BASYX_TEST_DIR    "${CMAKE_SOURCE_DIR}/regression")
set(BASYX_LIBRARY_DIR "${CMAKE_SOURCE_DIR}/lib")

### Build options ###
set(BASYX_BUILD_TESTS ON CACHE BOOL "Build unit tests")
set(BASYX_BUILD_API ON CACHE BOOL "Build BaSyx API")
set(BASYX_UTILITY_PROJECTS ON CACHE BOOL "Create utility targets")
set(BASYX_DEBUG_PRINT_FRAMES OFF CACHE BOOL "Print BaSyx frames")
set(BASYX_INSTALL_SDK ON CACHE BOOL "Create install instructions for BaSyx SDK")

set(BUILD_SHARED_LIBS OFF CACHE BOOL "Build Shared (.so / .dll) instead of static libraries")

option(BASYX_USE_SYSTEM_LIBFMT "Wether to build external, yet included library libfmt" OFF)
option(BASYX_USE_SYSTEM_ASIO "Use system supplied ASIO library." OFF)
option(BASYX_USE_SYSTEM_JSON "Use system supplied nlohmann json library" OFF)

### Compiler settings ###
set(CMAKE_CXX_STANDARD 11)

### Compile Step Definitions ###
add_compile_definitions(BASYX_MAJOR_REV=${BASYX_PACKAGE_VERSION_MAJOR})
add_compile_definitions(BASYX_MINOR_REV=${BASYX_PACKAGE_VERSION_MINOR})
add_compile_definitions(BASYX_PATCH_REV=${BASYX_PACKAGE_VERSION_PATCH})
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

if(${BASYX_USE_SYSTEM_LIBFMT})
   find_package(fmt REQUIRED)
else()
   add_subdirectory(lib/fmt)
   add_library(lib::fmt ALIAS fmt)
   add_library(lib::fmt-header-only ALIAS fmt-header-only)
endif()

if(${BASYX_USE_SYSTEM_ASIO})
    find_package(asio REQUIRED)
else()
    add_subdirectory(lib/asio)
endif()

if(${BASYX_USE_SYSTEM_JSON})
    find_package(nlohmann_json REQUIRED)
else()
    add_subdirectory(lib/json)
endif()

if(${BASYX_BUILD_TESTS})
    add_subdirectory(lib/gtest)
    include(GoogleTest)
endif()

###############################################
###           Source Directory              ###
###############################################
include_directories("${BASYX_INCLUDE_DIR}")

add_subdirectory(src/abstraction)
add_subdirectory(src/logging)
add_subdirectory(src/shared)
add_subdirectory(src/utility)
add_subdirectory(src/vab)
add_subdirectory(src/server)
add_subdirectory(src/submodel)
add_subdirectory(src/controlcomponent)

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
    enable_testbed()
endif()


###############################################
###           Install section               ###
###############################################

if(${BASYX_INSTALL_SDK})

# Let the projects using our server infrastucure find asio (interally /
# externally provided)

install(FILES ${CMAKE_SOURCE_DIR}/cmake/Findasio.cmake
		DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/BaSyxAsio
)

###############################################
###           Install overview              ###
###############################################

message("")
message("--------------------")
message("Configuration:")
message("   Library name and version:   " "${PROJECT_NAME}, ${PROJECT_VERSION}" )
message("   Library destination folder: " ${CMAKE_INSTALL_PREFIX}/${CMAKE_INSTALL_LIBDIR} )
message("   Includes folder:            " ${CMAKE_INSTALL_PREFIX}/${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME} )
#message("   Pkgconfig folder:           " ${CMAKE_INSTALL_PREFIX}/${CMAKE_INSTALL_LIBDIR}/pkgconfig )
message("   CMake folder:               " ${CMAKE_INSTALL_PREFIX}/${CMAKE_INSTALL_LIBDIR}/cmake/${PROJECT_SHORTNAME} )
message("")
if(${BASYX_USE_SYSTEM_LIBFMT})
message("   Using system supplied fmt")
else()
message("   Using contained fmt - Please check if this is necessary!")
endif()

if(${BASYX_USE_SYSTEM_ASIO})
message("   Using system supplied asio")
else()
message("   Using contained asio - Please check if this is necessary!")
endif()

if(${BASYX_USE_SYSTEM_JSON})
message("   Using system supplied nlohmann_json")
else()
message("   Using contained nlohmann_json - Please check if this is necessary!")
endif()

if ((NOT ${BASYX_USE_SYSTEM_LIBFMT}) OR (NOT ${BASYX_USE_SYSTEM_ASIO}) OR (NOT ${BASYX_USE_SYSTEM_JSON}))
message("")
message("   A contained external library will be installed while installing \
BaSyx, please use distribution provided libraries if possible!")
endif()
	
endif() # ${BASYX_INSTALL_SDK}
