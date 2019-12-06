###############################################
###              BaSyx::VAB                 ###
###############################################

set (BASYX_VAB_LIB_SUFFIX "VAB")

set (BASYX_VAB_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_VAB_LIB_SUFFIX}")

add_library(${BASYX_VAB_LIBRARY_NAME})

set_target_properties(${BASYX_VAB_LIBRARY_NAME} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_VAB_LIBRARY_NAME} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})

if(${BASYX_DEBUG_PRINT_FRAMES})
    target_compile_definitions(${BASYX_VAB_LIBRARY_NAME} PUBLIC PRINT_FRAME)
endif()

target_sources(${BASYX_VAB_LIBRARY_NAME}
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

target_include_directories(${BASYX_VAB_LIBRARY_NAME} PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(${BASYX_VAB_LIBRARY_NAME} BaSyx::Shared BaSyx::Abstraction)
add_library(${PROJECT_SHORTNAME}::${BASYX_VAB_LIB_SUFFIX} ALIAS ${BASYX_VAB_LIBRARY_NAME})
add_library(basyx::vab ALIAS ${BASYX_VAB_LIBRARY_NAME})

###############################################
###           Install section               ###
###############################################

configure_file("${BASYX_VAB_LIBRARY_NAME}Config.cmake.in" "${BASYX_VAB_LIBRARY_NAME}Config.cmake" @ONLY)

install(TARGETS ${BASYX_VAB_LIBRARY_NAME}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR})

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/vab"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h")

install(FILES ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_VAB_LIBRARY_NAME}Config.cmake DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${PROJECT_SHORTNAME})

diagnostics_print(${BASYX_VAB_LIBRARY_NAME})

