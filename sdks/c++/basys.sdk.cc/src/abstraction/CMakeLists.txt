###############################################
###              abstraction                ###
###############################################

project(abstraction)

message(STATUS "${PROJECT_NAME} settings:")
message(STATUS "==============")

add_library(abstraction)

## basyx::thread sources ##

target_sources(abstraction 
    PRIVATE 
        Thread.h
        thread/Thread.h 
        impl/thread_launcher.h
        impl/thread_impl.h
)

if(${CMAKE_USE_WIN32_THREADS_INIT})
    message(STATUS "Using win32 threads.")
    set(thread_platform "windows")
endif()

if(${CMAKE_USE_PTHREADS_INIT})
    message(STATUS "Using pthreads.")
    set(thread_platform "unix")
endif()

target_sources(abstraction
    PRIVATE
        impl/${thread_platform}/thread/thread_impl.cpp
        impl/${thread_platform}/thread/thread_impl.h
	)

## basyx::net sources ##

target_sources(abstraction
    PRIVATE
        Net.h
        net/Socket.h
        net/Acceptor.h
        net/Acceptor.cpp
        net/Buffer.h
        impl/acceptor_impl.h
        impl/socket_impl.h
        impl/system_net_types.h
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
        impl/${socket_platform}/socket/socket_impl.cpp
        impl/${socket_platform}/socket/acceptor_impl.cpp
)

target_include_directories(abstraction PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(abstraction Threads::Threads util)

diagnostics_print(abstraction)
