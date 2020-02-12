###############################################
###              BaSyx::Util                ###
###############################################
include(CMakePackageConfigHelpers)

set (BASYX_UTIL_LIB_SUFFIX "Util")

set (BASYX_UTIL_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_UTIL_LIB_SUFFIX}")

add_library(${BASYX_UTIL_LIB_SUFFIX})

set_target_properties(${BASYX_UTIL_LIB_SUFFIX} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_UTIL_LIB_SUFFIX} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})
set_target_properties(${BASYX_UTIL_LIB_SUFFIX} PROPERTIES PREFIX "lib${PROJECT_SHORTNAME}")
target_include_directories(${BASYX_UTIL_LIB_SUFFIX}
INTERFACE ${CMAKE_INSTALL_PREFIX}/include
       ${CMAKE_INSTALL_PREFIX}/include/${PROJECT_SHORTNAME})

target_sources(${BASYX_UTIL_LIB_SUFFIX}
    PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/util/util.cpp
)

target_include_directories(${BASYX_UTIL_LIB_SUFFIX} PRIVATE ${PROJECT_SOURCE_DIR})

add_library(basyx::util ALIAS ${BASYX_UTIL_LIB_SUFFIX})
add_library(${PROJECT_SHORTNAME}::${BASYX_UTIL_LIB_SUFFIX} ALIAS ${BASYX_UTIL_LIB_SUFFIX})

###############################################
###           Install section               ###
###############################################
if(${BASYX_INSTALL_SDK})

configure_package_config_file(
    ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_UTIL_LIBRARY_NAME}Config.cmake.in
    ${CMAKE_CURRENT_BINARY_DIR}/${BASYX_UTIL_LIBRARY_NAME}Config.cmake
    INSTALL_DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_UTIL_LIBRARY_NAME}
    PATH_VARS
    CMAKE_INSTALL_PREFIX
)

install(FILES ${CMAKE_CURRENT_BINARY_DIR}/${BASYX_UTIL_LIBRARY_NAME}Config.cmake
        DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_UTIL_LIBRARY_NAME}
)

install(EXPORT ${BASYX_UTIL_LIB_SUFFIX}Targets
        NAMESPACE ${PROJECT_SHORTNAME}::
        FILE ${BASYX_UTIL_LIBRARY_NAME}Targets.cmake
        DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_UTIL_LIBRARY_NAME}
)

install(TARGETS ${BASYX_UTIL_LIB_SUFFIX}
        EXPORT ${BASYX_UTIL_LIB_SUFFIX}Targets
        ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
)

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/../include/${PROJECT_SHORTNAME}/util"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h"
)

diagnostics_print(${BASYX_UTIL_LIB_SUFFIX})

endif()
