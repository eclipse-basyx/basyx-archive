###############################################
###              BaSyx::VAB                 ###
###############################################

project(basyx)

add_library(vab)

if(${BASYX_DEBUG_PRINT_FRAMES})
    target_compile_definitions(vab PUBLIC PRINT_FRAME)
endif()

target_sources(vab
    PRIVATE
        vab/backend/connector/IBaSyxConnector.h
        vab/backend/connector/JSONProvider.h
        vab/backend/connector/native/BaSyxConnector.cpp
        vab/backend/connector/native/BaSyxConnector.h
        vab/backend/connector/native/frame/BaSyx.h
        vab/backend/connector/native/frame/BaSyxNativeFrameBuilder.cpp
        vab/backend/connector/native/frame/BaSyxNativeFrameBuilder.h
        vab/core/IModelProvider.h
        vab/core/proxy/VABElementProxy.cpp
        vab/core/proxy/VABElementProxy.h
        vab/core/util/VABPath.cpp
        vab/core/util/VABPath.h
        vab/core/VABConnectionManager.cpp
        vab/core/VABConnectionManager.h
        vab/provider/hashmap/VABHashmapProvider.cpp
        vab/provider/hashmap/VABHashmapProvider.h
        vab/provider/native/BaSyxNativeProvider.h
        vab/provider/native/BaSyxTCPServer.h
        vab/provider/native/frame/BaSyxNativeFrameHelper.h
        vab/provider/native/frame/BaSyxNativeFrameProcessor.cpp
        vab/provider/native/frame/BaSyxNativeFrameProcessor.h
)

target_include_directories(vab PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(vab basyx::shared basyx::abstraction)
add_library(basyx::vab ALIAS vab)

diagnostics_print(vab)
