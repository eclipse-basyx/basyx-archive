###############################################
###              BaSyx::Logging             ###
###############################################
include(CMakePackageConfigHelpers)

set (BASYX_LOG_LIB_SUFFIX "Log")

set (BASYX_LOG_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_LOG_LIB_SUFFIX}")

add_library(${BASYX_LOG_LIB_SUFFIX})

set_target_properties(${BASYX_LOG_LIB_SUFFIX} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_LOG_LIB_SUFFIX} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})
set_target_properties(${BASYX_LOG_LIB_SUFFIX} PROPERTIES PREFIX "lib${PROJECT_SHORTNAME}")
target_include_directories(${BASYX_LOG_LIB_SUFFIX}
INTERFACE ${CMAKE_INSTALL_PREFIX}/include
       ${CMAKE_INSTALL_PREFIX}/include/${PROJECT_SHORTNAME})

target_sources(${BASYX_LOG_LIB_SUFFIX}
    PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/log/log.cpp
)

target_include_directories(${BASYX_LOG_LIB_SUFFIX} PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(${BASYX_LOG_LIB_SUFFIX}
    PUBLIC
    BaSyx::Util
    fmt::fmt
)

add_library(basyx::log ALIAS ${BASYX_LOG_LIB_SUFFIX})
add_library(${PROJECT_SHORTNAME}::${BASYX_LOG_LIB_SUFFIX} ALIAS ${BASYX_LOG_LIB_SUFFIX})

###############################################
###           Install section               ###
###############################################
configure_package_config_file(
    ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_LOG_LIBRARY_NAME}Config.cmake.in
    ${CMAKE_CURRENT_BINARY_DIR}/${BASYX_LOG_LIBRARY_NAME}Config.cmake
    INSTALL_DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_LOG_LIBRARY_NAME}
    PATH_VARS
    CMAKE_INSTALL_PREFIX
)

install(FILES ${CMAKE_CURRENT_BINARY_DIR}/${BASYX_LOG_LIBRARY_NAME}Config.cmake
        DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_LOG_LIBRARY_NAME}
)

install(EXPORT ${BASYX_LOG_LIB_SUFFIX}Targets
        NAMESPACE ${PROJECT_SHORTNAME}::
        FILE ${BASYX_LOG_LIBRARY_NAME}Targets.cmake
        DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_LOG_LIBRARY_NAME}
)

install(TARGETS ${BASYX_LOG_LIB_SUFFIX}
        EXPORT ${BASYX_LOG_LIB_SUFFIX}Targets
        ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
)

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/../include/${PROJECT_SHORTNAME}/log"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h"
)

diagnostics_print(${BASYX_LOG_LIB_SUFFIX})
