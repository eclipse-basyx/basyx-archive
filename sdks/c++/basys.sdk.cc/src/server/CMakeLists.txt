###############################################
###              BaSyx::Server              ###
###############################################
include(CMakePackageConfigHelpers)

set (BASYX_SERVER_LIB_SUFFIX "Server")

set (BASYX_SERVER_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_SERVER_LIB_SUFFIX}")

add_library(${BASYX_SERVER_LIB_SUFFIX})

set_target_properties(${BASYX_SERVER_LIB_SUFFIX} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_SERVER_LIB_SUFFIX} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})
set_target_properties(${BASYX_SERVER_LIB_SUFFIX} PROPERTIES PREFIX "lib${PROJECT_SHORTNAME}")
target_include_directories(${BASYX_SERVER_LIB_SUFFIX}
INTERFACE ${CMAKE_INSTALL_PREFIX}/include
       ${CMAKE_INSTALL_PREFIX}/include/${PROJECT_SHORTNAME})

if(${BASYX_DEBUG_PRINT_FRAMES})
    target_compile_definitions(${BASYX_SERVER_LIB_SUFFIX} PUBLIC PRINT_FRAME)
endif()

target_sources(${BASYX_SERVER_LIB_SUFFIX}
    PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/server/server.cpp
)

target_include_directories(${BASYX_SERVER_LIB_SUFFIX} PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(${BASYX_SERVER_LIB_SUFFIX}
    PUBLIC
    BaSyx::Shared
    BaSyx::VAB
    lib::asio
)

add_library(basyx::server ALIAS ${BASYX_SERVER_LIB_SUFFIX})
add_library(${PROJECT_SHORTNAME}::${BASYX_SERVER_LIB_SUFFIX} ALIAS ${BASYX_SERVER_LIB_SUFFIX})

###############################################
###           Install section               ###
###############################################
if(${BASYX_INSTALL_SDK})

configure_package_config_file(
    ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_SERVER_LIBRARY_NAME}Config.cmake.in
    ${CMAKE_CURRENT_BINARY_DIR}/${BASYX_SERVER_LIBRARY_NAME}Config.cmake
    INSTALL_DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_SERVER_LIBRARY_NAME}
    PATH_VARS
    CMAKE_INSTALL_PREFIX
)

install(FILES ${CMAKE_CURRENT_BINARY_DIR}/${BASYX_SERVER_LIBRARY_NAME}Config.cmake
        DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_SERVER_LIBRARY_NAME}
)

install(EXPORT ${BASYX_SERVER_LIB_SUFFIX}Targets
        NAMESPACE ${PROJECT_SHORTNAME}::
        FILE ${BASYX_SERVER_LIBRARY_NAME}Targets.cmake
        DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_SERVER_LIBRARY_NAME}
)

install(TARGETS ${BASYX_SERVER_LIB_SUFFIX}
        EXPORT ${BASYX_SERVER_LIB_SUFFIX}Targets
        ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
)

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/../include/${PROJECT_SHORTNAME}/aas"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h"
)

diagnostics_print(${BASYX_SERVER_LIB_SUFFIX})

endif()
