###############################################
###              BaSyx::abstraction         ###
###############################################

project(basyx)

message(STATUS "${PROJECT_NAME} settings:")
message(STATUS "==============")

add_library(abstraction)

## basyx::thread sources ##

target_sources(abstraction 
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

target_sources(abstraction
    PRIVATE
        abstraction/impl/${thread_platform}/thread/thread_impl.cpp
        abstraction/impl/${thread_platform}/thread/thread_impl.h
	)

## basyx::net sources ##

target_sources(abstraction
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
    target_link_libraries(abstraction ws2_32)
    set(socket_platform "windows")
endif()

if(UNIX)
    message(STATUS "Using POSIX sockets.")	
    set(socket_platform "unix")
endif()

target_sources(abstraction
    PRIVATE
        abstraction/impl/${socket_platform}/socket/socket_impl.cpp
        abstraction/impl/${socket_platform}/socket/acceptor_impl.cpp
)

target_include_directories(abstraction PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(abstraction Threads::Threads basyx::util basyx::logging)
add_library(basyx::abstraction ALIAS abstraction)

diagnostics_print(abstraction)
