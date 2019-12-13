###############################################
###              BaSyx::AAS                 ###
###############################################

set (BASYX_AAS_LIB_SUFFIX "AAS")

set (BASYX_AAS_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_AAS_LIB_SUFFIX}")

add_library(${BASYX_AAS_LIBRARY_NAME})

set_target_properties(${BASYX_AAS_LIBRARY_NAME} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_AAS_LIBRARY_NAME} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})

if(${BASYX_DEBUG_PRINT_FRAMES})
    target_compile_definitions(${BASYX_AAS_LIBRARY_NAME} PUBLIC PRINT_FRAME)
endif()

target_sources(${BASYX_AAS_LIBRARY_NAME}
    PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/aas/api/modelurn/ModelUrn.cpp
    #${CMAKE_CURRENT_SOURCE_DIR}/aas/backend/connected/aas/ConnectedAssetAdministrationShell.cpp
    #${CMAKE_CURRENT_SOURCE_DIR}/aas/backend/connected/aas/ConnectedAssetAdministrationShellManager.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/impl/metamodel/AssetAdministrationShell.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/impl/metamodel/hashmap/descriptor/ModelDescriptor.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/impl/metamodel/hashmap/descriptor/SubModelDescriptor.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/impl/metamodel/hashmap/parts/Asset.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/impl/metamodel/hashmap/parts/ConceptDescription.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/impl/metamodel/hashmap/parts/ConceptDictionary.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/impl/metamodel/hashmap/parts/View.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/impl/metamodel/hashmap/security/Security.cpp
)

target_include_directories(${BASYX_AAS_LIBRARY_NAME} PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(${BASYX_AAS_LIBRARY_NAME} BaSyx::Shared BaSyx::Abstraction BaSyx::VAB BaSyx::Submodel)
add_library(${PROJECT_SHORTNAME}::${BASYX_AAS_LIB_SUFFIX} ALIAS ${BASYX_AAS_LIBRARY_NAME})
add_library(basyx::aas ALIAS ${BASYX_AAS_LIBRARY_NAME})


###############################################
###           Install section               ###
###############################################

configure_file("${BASYX_AAS_LIBRARY_NAME}Config.cmake.in" "${BASYX_AAS_LIBRARY_NAME}Config.cmake" @ONLY)

install(TARGETS ${BASYX_AAS_LIBRARY_NAME}
        ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR})

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/../include/BaSyx/aas"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h")
        
install(FILES ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_AAS_LIBRARY_NAME}Config.cmake DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_AAS_LIBRARY_NAME})

diagnostics_print(${BASYX_AAS_LIBRARY_NAME})
