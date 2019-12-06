###############################################
###              BaSyx::Logging             ###
###############################################

set (BASYX_LOG_LIB_SUFFIX "Log")

set (BASYX_LOG_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_LOG_LIB_SUFFIX}")

add_library(${BASYX_LOG_LIBRARY_NAME})

set_target_properties(${BASYX_LOG_LIBRARY_NAME} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_LOG_LIBRARY_NAME} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})

set(LOG_PUBLIC_HEADER
		log/log.h
)

target_sources(${BASYX_LOG_LIBRARY_NAME}
		PRIVATE
		log/log.cpp
		${LOG_PUBLIC_HEADER}
)

target_include_directories(${BASYX_LOG_LIBRARY_NAME} PUBLIC ${PROJECT_SOURCE_DIR}) 
target_link_libraries(${BASYX_LOG_LIBRARY_NAME} BaSyx::Util fmt)
add_library(${PROJECT_SHORTNAME}::${BASYX_LOG_LIB_SUFFIX} ALIAS ${BASYX_LOG_LIBRARY_NAME})
add_library(basyx::log ALIAS ${BASYX_LOG_LIBRARY_NAME})

###############################################
###           Install section               ###
###############################################

set_target_properties(${BASYX_LOG_LIBRARY_NAME} PROPERTIES PUBLIC_HEADER "${LOG_PUBLIC_HEADER}")

configure_file("${BASYX_LOG_LIBRARY_NAME}Config.cmake.in" "${BASYX_LOG_LIBRARY_NAME}Config.cmake" @ONLY)

install(TARGETS ${BASYX_LOG_LIBRARY_NAME}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
        PUBLIC_HEADER DESTINATION ${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}/log)

install(FILES ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_LOG_LIBRARY_NAME}Config.cmake DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${PROJECT_SHORTNAME})

diagnostics_print(${BASYX_LOG_LIBRARY_NAME})
