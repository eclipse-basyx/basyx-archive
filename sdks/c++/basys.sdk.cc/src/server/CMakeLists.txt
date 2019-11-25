###############################################
###              BaSyx::Server              ###
###############################################

project(basyx)

add_library(basyx_server)

if(${BASYX_DEBUG_PRINT_FRAMES})
    target_compile_definitions(basyx_server PUBLIC PRINT_FRAME)
endif()

target_sources(basyx_server
    PRIVATE
		./server/BaSyxNativeProvider.h
		./server/BaSyxTCPServer.h
		./server/server.cpp
		./server/server.h
)

target_include_directories(basyx_server PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(basyx_server basyx::shared basyx::vab lib::asio)
add_library(basyx::server ALIAS basyx_server)

diagnostics_print(basyx_server)
