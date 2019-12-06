###############################################
###              BaSyx::Server              ###
###############################################

set (BASYX_SERVER_LIB_SUFFIX "Server")

set (BASYX_SERVER_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_SERVER_LIB_SUFFIX}")

add_library(${BASYX_SERVER_LIBRARY_NAME})

set_target_properties(${BASYX_SERVER_LIBRARY_NAME} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_SERVER_LIBRARY_NAME} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})

if(${BASYX_DEBUG_PRINT_FRAMES})
    target_compile_definitions(${BASYX_SERVER_LIBRARY_NAME} PUBLIC PRINT_FRAME)
endif()

target_sources(${BASYX_SERVER_LIBRARY_NAME}
    PRIVATE
		./server/BaSyxNativeProvider.h
		./server/BaSyxTCPServer.h
		./server/server.cpp
		./server/server.h
)

target_include_directories(${BASYX_SERVER_LIBRARY_NAME} PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(${BASYX_SERVER_LIBRARY_NAME} BaSyx::Shared BaSyx::VAB lib::asio)
add_library(${PROJECT_SHORTNAME}::${BASYX_SERVER_LIB_SUFFIX} ALIAS ${BASYX_SERVER_LIBRARY_NAME})
add_library(basyx::server ALIAS ${BASYX_SERVER_LIBRARY_NAME})

###############################################
###           Install section               ###
###############################################

configure_file("${BASYX_SERVER_LIBRARY_NAME}Config.cmake.in" "${BASYX_SERVER_LIBRARY_NAME}Config.cmake" @ONLY)

install(TARGETS ${BASYX_SERVER_LIBRARY_NAME}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR})

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/server"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h")

install(FILES ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_SERVER_LIBRARY_NAME}Config.cmake DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${PROJECT_SHORTNAME})

diagnostics_print(${BASYX_SERVER_LIBRARY_NAME})
