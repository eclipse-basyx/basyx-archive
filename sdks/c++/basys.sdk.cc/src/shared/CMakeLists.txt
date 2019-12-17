###############################################
###              BaSyx::Shared              ###
###############################################

set (BASYX_SHARED_LIB_SUFFIX "Shared")

set (BASYX_SHARED_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_SHARED_LIB_SUFFIX}")

add_library(${BASYX_SHARED_LIBRARY_NAME})

set_target_properties(${BASYX_SHARED_LIBRARY_NAME} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_SHARED_LIBRARY_NAME} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})

target_sources(${BASYX_SHARED_LIBRARY_NAME}
    PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/shared/basysid/BaSysID.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/shared/object/impl/object_impl.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/shared/object/object_type.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/shared/serialization/json/typeid.cpp
)

target_include_directories(${BASYX_SHARED_LIBRARY_NAME} PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(${BASYX_SHARED_LIBRARY_NAME} BaSyx::Util BaSyx::Log lib::json)
add_library(${PROJECT_SHORTNAME}::${BASYX_SHARED_LIB_SUFFIX} ALIAS ${BASYX_SHARED_LIBRARY_NAME})
add_library(basyx::shared ALIAS ${BASYX_SHARED_LIBRARY_NAME})

###############################################
###           Install section               ###
###############################################

configure_file("${BASYX_SHARED_LIBRARY_NAME}Config.cmake.in" "${BASYX_SHARED_LIBRARY_NAME}Config.cmake" @ONLY)

install(TARGETS ${BASYX_SHARED_LIBRARY_NAME}
        ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR})

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/../include/BaSyx/shared"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h")

install(FILES ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_SHARED_LIBRARY_NAME}Config.cmake DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_SHARED_LIBRARY_NAME})

diagnostics_print(${BASYX_SHARED_LIBRARY_NAME})
