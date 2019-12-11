###############################################
###              BaSyx::Util                ###
###############################################

set (BASYX_UTIL_LIB_SUFFIX "Util")

set (BASYX_UTIL_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_UTIL_LIB_SUFFIX}")

add_library(${BASYX_UTIL_LIBRARY_NAME})

set_target_properties(${BASYX_UTIL_LIBRARY_NAME} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_UTIL_LIBRARY_NAME} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})

set(UTIL_PUBLIC_HEADER
		util/function_traits.h
		util/invoke.h
		util/macros.h
		util/make_function.h
		util/meta.h
		util/printer.h
		util/tools/CoderTools.h
		util/tools/StringTools.h
		util/util.h
)

target_sources(${BASYX_UTIL_LIBRARY_NAME}
		PRIVATE
		util/util.cpp
		${UTIL_PUBLIC_HEADER}
)

target_include_directories(${BASYX_UTIL_LIBRARY_NAME} PUBLIC ${PROJECT_SOURCE_DIR}) 
target_link_libraries(${BASYX_UTIL_LIBRARY_NAME} lib::json)
add_library(${PROJECT_SHORTNAME}::${BASYX_UTIL_LIB_SUFFIX} ALIAS ${BASYX_UTIL_LIBRARY_NAME})
add_library(basyx::util ALIAS ${BASYX_UTIL_LIBRARY_NAME})

###############################################
###           Install section               ###
###############################################

configure_file("${BASYX_UTIL_LIBRARY_NAME}Config.cmake.in" "${BASYX_UTIL_LIBRARY_NAME}Config.cmake" @ONLY)

install(TARGETS ${BASYX_UTIL_LIBRARY_NAME}
        ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR})

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/util"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h")

install(FILES ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_UTIL_LIBRARY_NAME}Config.cmake DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${PROJECT_SHORTNAME})

diagnostics_print(${BASYX_UTIL_LIBRARY_NAME})
