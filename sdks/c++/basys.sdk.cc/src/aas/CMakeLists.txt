###############################################
###              BaSyx::AAS                 ###
###############################################
include(CMakePackageConfigHelpers)

set (BASYX_AAS_LIB_SUFFIX "AAS")

set (BASYX_AAS_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_AAS_LIB_SUFFIX}")

set (BASYX_AAS_INCLUDE_DIR "${BASYX_INCLUDE_DIR}/BaSyx/aas")
set (PROJECT_INCLUDE_DIR BASYX_AAS_INCLUDE_DIR)

add_library(${BASYX_AAS_LIB_SUFFIX})

set_target_properties(${BASYX_AAS_LIB_SUFFIX} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_AAS_LIB_SUFFIX} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})
set_target_properties(${BASYX_AAS_LIB_SUFFIX} PROPERTIES PREFIX "${CMAKE_STATIC_LIBRARY_PREFIX}${PROJECT_SHORTNAME}")

target_include_directories(${BASYX_AAS_LIB_SUFFIX}
    INTERFACE ${CMAKE_INSTALL_PREFIX}/include
    ${CMAKE_INSTALL_PREFIX}/include/${PROJECT_SHORTNAME})

target_sources(${BASYX_AAS_LIB_SUFFIX}
  PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/aas/map/constant_definitions.cpp
    ${BASYX_AAS_INCLUDE_DIR}/api/manager/IAssetAdministrationShellManager.h
    ${BASYX_AAS_INCLUDE_DIR}/api/metamodel/IAssetAdministrationShell.h
    ${BASYX_AAS_INCLUDE_DIR}/api/parts/IAsset.h
    ${BASYX_AAS_INCLUDE_DIR}/api/parts/IConceptDictionary.h
    ${BASYX_AAS_INCLUDE_DIR}/api/parts/IView.h
    ${BASYX_AAS_INCLUDE_DIR}/api/policypoints/IAccessControlPolicyPoints.h
    ${BASYX_AAS_INCLUDE_DIR}/api/security/ISecurity.h
#  ${CMAKE_CURRENT_SOURCE_DIR}/aas/connected/aas/ConnectedAssetAdministrationShell.cpp
#    ${BASYX_AAS_INCLUDE_DIR}/connected/aas/ConnectedAssetAdministrationShell.h
#  ${CMAKE_CURRENT_SOURCE_DIR}/aas/connected/aas/ConnectedAssetAdministrationShellManager.cpp
#    ${BASYX_AAS_INCLUDE_DIR}/connected/aas/ConnectedAssetAdministrationShellManager.h
    ${CMAKE_CURRENT_SOURCE_DIR}/aas/map/descriptor/ModelDescriptor.cpp
    ${BASYX_AAS_INCLUDE_DIR}/map/descriptor/ModelDescriptor.h
    ${CMAKE_CURRENT_SOURCE_DIR}/aas/map/descriptor/SubModelDescriptor.cpp
    ${BASYX_AAS_INCLUDE_DIR}/map/descriptor/SubModelDescriptor.h
    ${CMAKE_CURRENT_SOURCE_DIR}/aas/map/metamodel/AssetAdministrationShell.cpp
    ${BASYX_AAS_INCLUDE_DIR}/map/metamodel/AssetAdministrationShell.h
    ${CMAKE_CURRENT_SOURCE_DIR}/aas/map/manager/AssetAdministrationShellManager.cpp
    ${BASYX_AAS_INCLUDE_DIR}/map/manager/AssetAdministrationShellManager.h
    ${CMAKE_CURRENT_SOURCE_DIR}/aas/map/modelurn/ModelUrn.cpp
    ${BASYX_AAS_INCLUDE_DIR}/map/modelurn/ModelUrn.h
    ${CMAKE_CURRENT_SOURCE_DIR}/aas/map/parts/Asset.cpp
    ${BASYX_AAS_INCLUDE_DIR}/map/parts/Asset.h
    ${CMAKE_CURRENT_SOURCE_DIR}/aas/map/parts/ConceptDictionary.cpp
    ${BASYX_AAS_INCLUDE_DIR}/map/parts/ConceptDictionary.h
    ${CMAKE_CURRENT_SOURCE_DIR}/aas/map/parts/View.cpp
    ${BASYX_AAS_INCLUDE_DIR}/map/parts/View.h
    ${CMAKE_CURRENT_SOURCE_DIR}/aas/map/security/Security.cpp
    ${BASYX_AAS_INCLUDE_DIR}/map/security/Security.h
)


target_include_directories(${BASYX_AAS_LIB_SUFFIX} PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(${BASYX_AAS_LIB_SUFFIX} PUBLIC BaSyx::Shared BaSyx::Abstraction BaSyx::VAB BaSyx::Submodel)

add_library(basyx::aas ALIAS ${BASYX_AAS_LIB_SUFFIX})
add_library(${PROJECT_SHORTNAME}::${BASYX_AAS_LIB_SUFFIX} ALIAS ${BASYX_AAS_LIB_SUFFIX})

diagnostics_print(${BASYX_AAS_LIB_SUFFIX})

###############################################
###           Install section               ###
###############################################
if(${BASYX_INSTALL_SDK})
configure_package_config_file(
    ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_AAS_LIBRARY_NAME}Config.cmake.in
    ${CMAKE_CURRENT_BINARY_DIR}/${BASYX_AAS_LIBRARY_NAME}Config.cmake
    INSTALL_DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_AAS_LIBRARY_NAME}
    PATH_VARS
    CMAKE_INSTALL_PREFIX
)

install(FILES ${CMAKE_CURRENT_BINARY_DIR}/${BASYX_AAS_LIBRARY_NAME}Config.cmake
        DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_AAS_LIBRARY_NAME}
        )

install(EXPORT ${BASYX_AAS_LIB_SUFFIX}Targets
        NAMESPACE ${PROJECT_SHORTNAME}::
        FILE ${BASYX_AAS_LIBRARY_NAME}Targets.cmake
        DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_AAS_LIBRARY_NAME}
        )

install(TARGETS ${BASYX_AAS_LIB_SUFFIX}
        EXPORT ${BASYX_AAS_LIB_SUFFIX}Targets
        ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
        )

install(DIRECTORY "${BASYX_INCLUDE_DIR}/${PROJECT_SHORTNAME}/aas"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h"
        )
endif()