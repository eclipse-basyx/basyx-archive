###############################################
###              BaSyx::abstraction         ###
###############################################
include(CMakePackageConfigHelpers)

set (BASYX_ABSTRACTION_LIB_SUFFIX "Abstraction")

set (BASYX_ABSTRACTION_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_ABSTRACTION_LIB_SUFFIX}")

add_library(${BASYX_ABSTRACTION_LIB_SUFFIX})

set_target_properties(${BASYX_ABSTRACTION_LIB_SUFFIX} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_ABSTRACTION_LIB_SUFFIX} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})
set_target_properties(${BASYX_ABSTRACTION_LIB_SUFFIX} PROPERTIES PREFIX "lib${PROJECT_SHORTNAME}")
target_include_directories(${BASYX_ABSTRACTION_LIB_SUFFIX}
INTERFACE ${CMAKE_INSTALL_PREFIX}/include
       ${CMAKE_INSTALL_PREFIX}/include/${PROJECT_SHORTNAME})

## basyx::thread sources ##

if(${CMAKE_USE_WIN32_THREADS_INIT})
    message(STATUS "Using win32 threads.")
    set(thread_platform "windows")
elseif(${CMAKE_USE_PTHREADS_INIT})
    message(STATUS "Using pthreads.")
    set(thread_platform "unix")
endif()

target_sources(${BASYX_ABSTRACTION_LIB_SUFFIX}
    PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/abstraction/impl/${thread_platform}/thread/thread_impl.cpp
)

## basyx::net sources ##

target_sources(${BASYX_ABSTRACTION_LIB_SUFFIX}
    PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/abstraction/net/Acceptor.cpp
)

if(WIN32)
    message(STATUS "Using winsock2.")
    target_link_libraries(${BASYX_ABSTRACTION_LIB_SUFFIX} ws2_32)
    set(socket_platform "windows")
endif()

if(UNIX)
    message(STATUS "Using POSIX sockets.")	
    set(socket_platform "unix")
endif()

target_sources(${BASYX_ABSTRACTION_LIB_SUFFIX}
    PRIVATE
        abstraction/impl/${socket_platform}/socket/socket_impl.cpp
        abstraction/impl/${socket_platform}/socket/acceptor_impl.cpp
)

target_include_directories(${BASYX_ABSTRACTION_LIB_SUFFIX} PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(${BASYX_ABSTRACTION_LIB_SUFFIX}
    PUBLIC
    Threads::Threads
    BaSyx::Util
    BaSyx::Log
)

add_library(basyx::abstraction ALIAS ${BASYX_ABSTRACTION_LIB_SUFFIX})
add_library(${PROJECT_SHORTNAME}::${BASYX_ABSTRACTION_LIB_SUFFIX} ALIAS ${BASYX_ABSTRACTION_LIB_SUFFIX})


###############################################
###           Install section               ###
###############################################
configure_package_config_file(
    ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_ABSTRACTION_LIBRARY_NAME}Config.cmake.in
    ${CMAKE_CURRENT_BINARY_DIR}/${BASYX_ABSTRACTION_LIBRARY_NAME}Config.cmake
    INSTALL_DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_ABSTRACTION_LIBRARY_NAME}
    PATH_VARS
    CMAKE_INSTALL_PREFIX
)

install(FILES ${CMAKE_CURRENT_BINARY_DIR}/${BASYX_ABSTRACTION_LIBRARY_NAME}Config.cmake
        DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_ABSTRACTION_LIBRARY_NAME}
)

install(EXPORT ${BASYX_ABSTRACTION_LIB_SUFFIX}Targets
        NAMESPACE ${PROJECT_SHORTNAME}::
        FILE ${BASYX_ABSTRACTION_LIB_SUFFIX}Targets.cmake
        DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_ABSTRACTION_LIB_SUFFIX}
)

install(TARGETS ${BASYX_ABSTRACTION_LIB_SUFFIX}
        EXPORT ${BASYX_ABSTRACTION_LIB_SUFFIX}Targets
        ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
)

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/../include/${PROJECT_SHORTNAME}/abstraction"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h"
)

diagnostics_print(${BASYX_ABSTRACTION_LIB_SUFFIX})
