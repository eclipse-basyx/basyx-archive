###############################################
###              Backends                   ###
###############################################

project(backends)

add_library(backends)

if(${BASYX_DEBUG_PRINT_FRAMES})
    target_compile_definitions(backends PUBLIC PRINT_FRAME)
endif()

target_sources(backends
    PRIVATE
        protocols/basyx/BaSyx.h
        protocols/connector/basyx/BaSyxNativeConnector.cpp
        protocols/connector/basyx/BaSyxNativeConnector.h
        protocols/connector/basyx/frame/BaSyxNativeFrameBuilder.cpp
        protocols/connector/basyx/frame/BaSyxNativeFrameBuilder.h
        protocols/connector/IBaSysConnector.h
        protocols/provider/basyx/BaSyxNativeProvider.h
        protocols/provider/basyx/BaSyxTCPServer.h
        protocols/provider/basyx/CoderTools.h
        protocols/provider/basyx/frame/BaSyxNativeFrameHelper.h
        protocols/provider/basyx/frame/BaSyxNativeFrameProcessor.cpp
        protocols/provider/basyx/frame/BaSyxNativeFrameProcessor.h
        protocols/provider/basyx/StringTools.h
        provider/json/JSONProvider.h
        provider/vab/VABPath.cpp
        provider/vab/VABPath.h
        provider/vab/VABHashmapProvider.cpp
        provider/vab/VABHashmapProvider.h
		    provider/vab/VABElementProxy.cpp
        provider/vab/VABElementProxy.h
)

target_include_directories(backends PUBLIC "${BASYX_SOURCE_DIR}" )
target_link_libraries(backends basyx abstraction)

diagnostics_print(backends)
