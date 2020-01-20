###############################################
###              BaSyx::Shared              ###
###############################################

set (BASYX_SHARED_LIB_SUFFIX "Shared")

set (BASYX_SHARED_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_SHARED_LIB_SUFFIX}")

add_library(${BASYX_SHARED_LIB_SUFFIX})

set_target_properties(${BASYX_SHARED_LIB_SUFFIX} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_SHARED_LIB_SUFFIX} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})
set_target_properties(${BASYX_SHARED_LIB_SUFFIX} PROPERTIES PREFIX "lib${PROJECT_SHORTNAME}")
target_include_directories(${BASYX_SHARED_LIB_SUFFIX}
INTERFACE ${CMAKE_INSTALL_PREFIX}/include
       ${CMAKE_INSTALL_PREFIX}/include/${PROJECT_SHORTNAME})

target_sources(${BASYX_SHARED_LIB_SUFFIX}
    PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/shared/basysid/BaSysID.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/shared/object/impl/object_impl.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/shared/object/object_type.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/shared/serialization/json/typeid.cpp
)

target_include_directories(${BASYX_SHARED_LIB_SUFFIX} PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(${BASYX_SHARED_LIB_SUFFIX}
    PUBLIC
    BaSyx::Util
    BaSyx::Log
    nlohmann_json::nlohmann_json
)
add_library(basyx::shared ALIAS ${BASYX_SHARED_LIB_SUFFIX})
add_library(${PROJECT_SHORTNAME}::${BASYX_SHARED_LIB_SUFFIX} ALIAS ${BASYX_SHARED_LIB_SUFFIX})


###############################################
###           Install section               ###
###############################################
configure_package_config_file(
    ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_SHARED_LIBRARY_NAME}Config.cmake.in
    ${CMAKE_CURRENT_BINARY_DIR}/${BASYX_SHARED_LIBRARY_NAME}Config.cmake
    INSTALL_DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_SHARED_LIBRARY_NAME}
    PATH_VARS
    CMAKE_INSTALL_PREFIX
)

install(FILES ${CMAKE_CURRENT_BINARY_DIR}/${BASYX_SHARED_LIBRARY_NAME}Config.cmake
        DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_SHARED_LIBRARY_NAME}
)

install(EXPORT ${BASYX_SHARED_LIB_SUFFIX}Targets
        NAMESPACE ${PROJECT_SHORTNAME}::
        FILE ${BASYX_SHARED_LIBRARY_NAME}Targets.cmake
        DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_SHARED_LIBRARY_NAME}
)

install(TARGETS ${BASYX_SHARED_LIB_SUFFIX}
        EXPORT ${BASYX_SHARED_LIB_SUFFIX}Targets
        ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
)

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/../include/${PROJECT_SHORTNAME}/shared"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h"
)

diagnostics_print(${BASYX_SHARED_LIB_SUFFIX})
