###############################################
###              BaSyx::AAS                 ###
###############################################

project(basyx)

add_library(basyx_aas)

if(${BASYX_DEBUG_PRINT_FRAMES})
    target_compile_definitions(basyx_aas PUBLIC PRINT_FRAME)
endif()

target_sources(basyx_aas
    PRIVATE
		aas/aas/AssetAdministrationShell.h
		aas/aas/BlobType.h
		aas/aas/dataspecification/IDataSpecification.h
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
		aas/backend/connected/aas/ConnectedAssetAdministrationShell.cpp
		aas/backend/connected/aas/ConnectedAssetAdministrationShell.h
		aas/backend/connected/aas/ConnectedAssetAdministrationShellManager.cpp
		aas/backend/connected/aas/ConnectedAssetAdministrationShellManager.h
		aas/backend/connected/aas/dataspecification/IDataSpecification.h
		impl/map/ElementMap.cpp
		impl/map/ElementMap.h
		impl/metamodel/AssetAdministrationShell.cpp
		impl/metamodel/AssetAdministrationShell.h
		impl/metamodel/hashmap/descriptor/ModelDescriptor.cpp
		impl/metamodel/hashmap/descriptor/ModelDescriptor.h
		impl/metamodel/hashmap/descriptor/SubModelDescriptor.cpp
		impl/metamodel/hashmap/descriptor/SubModelDescriptor.h
		impl/metamodel/hashmap/parts/Asset.cpp
		impl/metamodel/hashmap/parts/Asset.h
		impl/metamodel/hashmap/parts/ConceptDictionary.cpp
		impl/metamodel/hashmap/parts/ConceptDictionary.h
		impl/metamodel/hashmap/parts/View.cpp
		impl/metamodel/hashmap/parts/View.h
		impl/metamodel/hashmap/security/Security.cpp
		impl/metamodel/hashmap/security/Security.h
)

target_include_directories(basyx_aas PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(basyx_aas basyx::shared basyx::abstraction basyx::vab basyx::submodel)
add_library(basyx::aas ALIAS basyx_aas)

diagnostics_print(basyx_aas)