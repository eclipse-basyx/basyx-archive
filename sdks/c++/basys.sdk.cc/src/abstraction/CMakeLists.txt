###############################################
###              BaSyx::abstraction         ###
###############################################

set (BASYX_ABSTRACTION_LIB_SUFFIX "Abstraction")

set (BASYX_ABSTRACTION_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_ABSTRACTION_LIB_SUFFIX}")

add_library(${BASYX_ABSTRACTION_LIBRARY_NAME})

set_target_properties(${BASYX_ABSTRACTION_LIBRARY_NAME} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_ABSTRACTION_LIBRARY_NAME} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})

## basyx::thread sources ##

target_sources(${BASYX_ABSTRACTION_LIBRARY_NAME} 
    PRIVATE 
        abstraction/Thread.h
        abstraction/thread/Thread.h 
        abstraction/impl/thread_launcher.h
        abstraction/impl/thread_impl.h
)

if(${CMAKE_USE_WIN32_THREADS_INIT})
    message(STATUS "Using win32 threads.")
    set(thread_platform "windows")
elseif(${CMAKE_USE_PTHREADS_INIT})
    message(STATUS "Using pthreads.")
    set(thread_platform "unix")
endif()

target_sources(${BASYX_ABSTRACTION_LIBRARY_NAME}
    PRIVATE
        abstraction/impl/${thread_platform}/thread/thread_impl.cpp
        abstraction/impl/${thread_platform}/thread/thread_impl.h
	)

## basyx::net sources ##

target_sources(${BASYX_ABSTRACTION_LIBRARY_NAME}
    PRIVATE
        abstraction/Net.h
        abstraction/net/Socket.h
        abstraction/net/Acceptor.h
        abstraction/net/Acceptor.cpp
        abstraction/net/Buffer.h
        abstraction/impl/acceptor_impl.h
        abstraction/impl/socket_impl.h
        abstraction/impl/system_net_types.h
)

if(WIN32)
    message(STATUS "Using winsock2.")
    target_link_libraries(${BASYX_ABSTRACTION_LIBRARY_NAME} ws2_32)
    set(socket_platform "windows")
endif()

if(UNIX)
    message(STATUS "Using POSIX sockets.")	
    set(socket_platform "unix")
endif()

target_sources(${BASYX_ABSTRACTION_LIBRARY_NAME}
    PRIVATE
        abstraction/impl/${socket_platform}/socket/socket_impl.cpp
        abstraction/impl/${socket_platform}/socket/acceptor_impl.cpp
)

target_include_directories(${BASYX_ABSTRACTION_LIBRARY_NAME} PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(${BASYX_ABSTRACTION_LIBRARY_NAME} Threads::Threads BaSyx::Util BaSyx::Log)
add_library(${PROJECT_SHORTNAME}::${BASYX_ABSTRACTION_LIB_SUFFIX} ALIAS ${BASYX_ABSTRACTION_LIBRARY_NAME})
add_library(basyx::abstraction ALIAS ${BASYX_ABSTRACTION_LIBRARY_NAME})

###############################################
###           Install section               ###
###############################################

configure_file("${BASYX_ABSTRACTION_LIBRARY_NAME}Config.cmake.in" "${BASYX_ABSTRACTION_LIBRARY_NAME}Config.cmake" @ONLY)

install(TARGETS ${BASYX_ABSTRACTION_LIBRARY_NAME}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR})

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/abstraction"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h")
        
install(FILES ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_ABSTRACTION_LIBRARY_NAME}Config.cmake DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${PROJECT_SHORTNAME})

diagnostics_print(${BASYX_ABSTRACTION_LIBRARY_NAME})
