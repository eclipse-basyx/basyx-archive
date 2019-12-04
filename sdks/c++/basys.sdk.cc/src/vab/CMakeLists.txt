###############################################
###              BaSyx::VAB                 ###
###############################################

project(basyx)

add_library(basyx_vab)

if(${BASYX_DEBUG_PRINT_FRAMES})
    target_compile_definitions(basyx_vab PUBLIC PRINT_FRAME)
endif()

target_sources(basyx_vab
    PRIVATE
		vab/backend/connector/IBaSyxConnector.h
		vab/backend/connector/JSONProvider.h
		vab/backend/connector/native/BaSyxConnector.cpp
		vab/backend/connector/native/BaSyxConnector.h
		vab/backend/connector/native/frame/BaSyxNativeFrameBuilder.cpp
		vab/backend/connector/native/frame/BaSyxNativeFrameBuilder.h
		vab/core/IModelProvider.h
		vab/core/proxy/IVABElementProxy.h
		vab/core/proxy/VABElementProxy.cpp
		vab/core/proxy/VABElementProxy.h
		vab/core/util/VABPath.cpp
		vab/core/util/VABPath.h
		vab/core/VABConnectionManager.cpp
		vab/core/VABConnectionManager.h
		vab/ElementMap.cpp
		vab/ElementMap.h
		vab/provider/hashmap/VABHashmapProvider.h
		vab/provider/IVABElementHandler.h
		vab/provider/native/frame/BaSyxNativeFrameHelper.h
		vab/provider/native/frame/BaSyxNativeFrameProcessor.cpp
		vab/provider/native/frame/BaSyxNativeFrameProcessor.h
		vab/provider/VABModelProvider.h
		vab/provider/VABMultiElementHandler.h
)

target_include_directories(basyx_vab PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(basyx_vab basyx::shared basyx::abstraction)
add_library(basyx::vab ALIAS basyx_vab)

diagnostics_print(basyx_vab)

