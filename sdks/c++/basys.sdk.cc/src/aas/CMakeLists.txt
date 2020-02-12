###############################################
###              BaSyx::AAS                 ###
###############################################
include(CMakePackageConfigHelpers)

set (BASYX_AAS_LIB_SUFFIX "AAS")

set (BASYX_AAS_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_AAS_LIB_SUFFIX}")

set (BASYX_AAS_INCLUDE_DIR "${BASYX_INCLUDE_DIR}/BaSyx/aas")

add_library(${BASYX_AAS_LIB_SUFFIX})

set_target_properties(${BASYX_AAS_LIB_SUFFIX} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_AAS_LIB_SUFFIX} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})
set_target_properties(${BASYX_AAS_LIB_SUFFIX} PROPERTIES PREFIX "lib${PROJECT_SHORTNAME}")
target_include_directories(${BASYX_AAS_LIB_SUFFIX}
INTERFACE ${CMAKE_INSTALL_PREFIX}/include
       ${CMAKE_INSTALL_PREFIX}/include/${PROJECT_SHORTNAME})


target_sources(${BASYX_AAS_LIB_SUFFIX}
    PRIVATE
        ${CMAKE_CURRENT_SOURCE_DIR}/aas/api/modelurn/ModelUrn.cpp
        #${CMAKE_CURRENT_SOURCE_DIR}/aas/backend/connected/aas/ConnectedAssetAdministrationShell.cpp
        #${CMAKE_CURRENT_SOURCE_DIR}/aas/backend/connected/aas/ConnectedAssetAdministrationShellManager.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/aas/impl/metamodel/AssetAdministrationShell.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/aas/impl/metamodel/hashmap/descriptor/ModelDescriptor.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/aas/impl/metamodel/hashmap/descriptor/SubModelDescriptor.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/aas/impl/metamodel/hashmap/parts/Asset.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/aas/impl/metamodel/hashmap/parts/ConceptDescription.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/aas/impl/metamodel/hashmap/parts/ConceptDictionary.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/aas/impl/metamodel/hashmap/parts/View.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/aas/impl/metamodel/hashmap/security/Security.cpp
)

target_sources(${BASYX_AAS_LIB_SUFFIX}
    PRIVATE
        ${BASYX_AAS_INCLUDE_DIR}/aas/AssetAdministrationShell.h
        ${BASYX_AAS_INCLUDE_DIR}/aas/BlobType.h
        ${BASYX_AAS_INCLUDE_DIR}/aas/dataspecification/IDataSpecification.h
        ${BASYX_AAS_INCLUDE_DIR}/aas/dataspecification/IDataSpecificationContent.h
        ${BASYX_AAS_INCLUDE_DIR}/aas/dataspecification/IDataSpecificationIEC61630.h
        ${BASYX_AAS_INCLUDE_DIR}/aas/MimeType.h
        ${BASYX_AAS_INCLUDE_DIR}/aas/parts/IAsset.h
        ${BASYX_AAS_INCLUDE_DIR}/aas/parts/IConceptDescription.h
        ${BASYX_AAS_INCLUDE_DIR}/aas/parts/IConceptDictionary.h
        ${BASYX_AAS_INCLUDE_DIR}/aas/parts/IView.h
        ${BASYX_AAS_INCLUDE_DIR}/aas/PathType.h
        ${BASYX_AAS_INCLUDE_DIR}/aas/policypoints/IAccessControlPolicyPoints.h
        ${BASYX_AAS_INCLUDE_DIR}/aas/security/ISecurity.h
        ${BASYX_AAS_INCLUDE_DIR}/api/constant_definitions.h
        ${BASYX_AAS_INCLUDE_DIR}/api/manager/IAssetAdministrationShellManager.h
        ${BASYX_AAS_INCLUDE_DIR}/api/metamodel/aas/IAssetAdministrationShell.h
        ${BASYX_AAS_INCLUDE_DIR}/api/modelurn/ModelUrn.h
        ${BASYX_AAS_INCLUDE_DIR}/backend/connected/aas/ConnectedAssetAdministrationShell.h
        ${BASYX_AAS_INCLUDE_DIR}/backend/connected/aas/ConnectedAssetAdministrationShellManager.h
        ${BASYX_AAS_INCLUDE_DIR}/backend/connected/aas/dataspecification/IDataSpecification.h
        ${BASYX_AAS_INCLUDE_DIR}/impl/metamodel/AssetAdministrationShell.h
        ${BASYX_AAS_INCLUDE_DIR}/impl/metamodel/hashmap/descriptor/ModelDescriptor.h
        ${BASYX_AAS_INCLUDE_DIR}/impl/metamodel/hashmap/descriptor/SubModelDescriptor.h
        ${BASYX_AAS_INCLUDE_DIR}/impl/metamodel/hashmap/parts/Asset.h
        ${BASYX_AAS_INCLUDE_DIR}/impl/metamodel/hashmap/parts/ConceptDescription.h
        ${BASYX_AAS_INCLUDE_DIR}/impl/metamodel/hashmap/parts/ConceptDictionary.h
        ${BASYX_AAS_INCLUDE_DIR}/impl/metamodel/hashmap/parts/View.h
        ${BASYX_AAS_INCLUDE_DIR}/impl/metamodel/hashmap/security/Security.h
)

target_include_directories(${BASYX_AAS_LIB_SUFFIX} PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(${BASYX_AAS_LIB_SUFFIX}
    PUBLIC
        BaSyx::Shared
        BaSyx::Abstraction
        BaSyx::VAB
        BaSyx::Submodel
)

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

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/.		${BASYX_AAS_INCLUDE_DIR}include/${PROJECT_SHORTNAME}/aas"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h"
)

endif()
