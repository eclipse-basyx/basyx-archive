###############################################
###              BaSyx::Util                ###
###############################################

set (BASYX_UTIL_LIB_SUFFIX "Util")

set (BASYX_UTIL_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_UTIL_LIB_SUFFIX}")

add_library(${BASYX_UTIL_LIBRARY_NAME})

set_target_properties(${BASYX_UTIL_LIBRARY_NAME} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_UTIL_LIBRARY_NAME} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})

target_sources(${BASYX_UTIL_LIBRARY_NAME}
    PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/util/util.cpp
)

target_include_directories(${BASYX_UTIL_LIBRARY_NAME} PUBLIC ${PROJECT_SOURCE_DIR}) 
target_link_libraries(${BASYX_UTIL_LIBRARY_NAME} nlohmann_json::nlohmann_json)
add_library(${PROJECT_SHORTNAME}::${BASYX_UTIL_LIB_SUFFIX} ALIAS ${BASYX_UTIL_LIBRARY_NAME})
add_library(basyx::util ALIAS ${BASYX_UTIL_LIBRARY_NAME})

###############################################
###           Install section               ###
###############################################

configure_file("${BASYX_UTIL_LIBRARY_NAME}Config.cmake.in" "${BASYX_UTIL_LIBRARY_NAME}Config.cmake" @ONLY)

install(TARGETS ${BASYX_UTIL_LIBRARY_NAME}
        ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR})

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/../include/BaSyx/util"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h")

install(FILES ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_UTIL_LIBRARY_NAME}Config.cmake DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_UTIL_LIBRARY_NAME})

diagnostics_print(${BASYX_UTIL_LIBRARY_NAME})
