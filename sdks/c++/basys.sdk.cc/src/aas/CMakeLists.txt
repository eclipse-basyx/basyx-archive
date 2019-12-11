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
		aas/aas/AssetAdministrationShell.h
		aas/aas/BlobType.h
		aas/aas/dataspecification/IDataSpecification.h
		aas/aas/dataspecification/IDataSpecificationContent.h
		aas/aas/dataspecification/IDataSpecificationIEC61630.h
		aas/aas/MimeType.h
		aas/aas/parts/IAsset.h
		aas/aas/parts/IConceptDescription.h
		aas/aas/parts/IConceptDictionary.h
		aas/aas/parts/IView.h
		aas/aas/PathType.h
		aas/aas/policypoints/IAccessControlPolicyPoints.h
		aas/aas/security/ISecurity.h
		aas/api/manager/IAssetAdministrationShellManager.h
		aas/api/metamodel/aas/IAssetAdministrationShell.h
		aas/api/modelurn/ModelUrn.cpp
		aas/api/modelurn/ModelUrn.h
		#aas/backend/connected/aas/ConnectedAssetAdministrationShell.cpp
		#aas/backend/connected/aas/ConnectedAssetAdministrationShell.h
		#aas/backend/connected/aas/ConnectedAssetAdministrationShellManager.cpp
		#aas/backend/connected/aas/ConnectedAssetAdministrationShellManager.h
		#aas/backend/connected/aas/dataspecification/IDataSpecification.h
		impl/metamodel/AssetAdministrationShell.cpp
		impl/metamodel/AssetAdministrationShell.h
		impl/metamodel/hashmap/descriptor/ModelDescriptor.cpp
		impl/metamodel/hashmap/descriptor/ModelDescriptor.h
		impl/metamodel/hashmap/descriptor/SubModelDescriptor.cpp
		impl/metamodel/hashmap/descriptor/SubModelDescriptor.h
		impl/metamodel/hashmap/parts/Asset.cpp
		impl/metamodel/hashmap/parts/Asset.h
		impl/metamodel/hashmap/parts/ConceptDescription.cpp
		impl/metamodel/hashmap/parts/ConceptDescription.h
		impl/metamodel/hashmap/parts/ConceptDictionary.cpp
		impl/metamodel/hashmap/parts/ConceptDictionary.h
		impl/metamodel/hashmap/parts/View.cpp
		impl/metamodel/hashmap/parts/View.h
		impl/metamodel/hashmap/security/Security.cpp
		impl/metamodel/hashmap/security/Security.h
    aas/api/constant_definitions.h
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

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/aas"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h")
        
install(FILES ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_AAS_LIBRARY_NAME}Config.cmake DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${PROJECT_SHORTNAME})

diagnostics_print(${BASYX_AAS_LIBRARY_NAME})
