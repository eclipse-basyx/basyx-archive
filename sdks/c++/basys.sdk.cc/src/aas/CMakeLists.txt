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
		aas/AssetAdministrationShell.h
		backend/connected/aas/ConnectedElement.cpp
		backend/connected/aas/ConnectedElement.h
		backend/connected/aas/ConnectedSubmodel.cpp
		backend/connected/aas/ConnectedSubmodel.h
		backend/connected/aas/ConnectedVABModelMap.cpp
		backend/connected/aas/ConnectedVABModelMap.h
		backend/connected/aas/ConnectedAssetAdministrationShell.cpp
		backend/connected/aas/ConnectedAssetAdministrationShell.h
		backend/connected/aas/submodelelement/ConnectedDataElement.cpp
		backend/connected/aas/submodelelement/ConnectedDataElement.h
		backend/connected/aas/submodelelement/ConnectedRelationshipElement.h
		backend/connected/aas/submodelelement/ConnectedRelationshipElement.cpp
		backend/connected/aas/submodelelement/ConnectedSubmodelElement.h
		backend/connected/aas/submodelelement/ConnectedSubmodelElement.cpp
		backend/connected/aas/submodelelement/ConnectedSubmodelElementCollection.h
		backend/connected/aas/submodelelement/ConnectedSubmodelElementCollection.cpp
		backend/connected/aas/submodelelement/operation/ConnectedOperation.h
		backend/connected/aas/submodelelement/operation/ConnectedOperation.cpp
		backend/connected/aas/submodelelement/property/ConnectedProperty.h
		backend/connected/aas/submodelelement/property/ConnectedProperty.cpp
		backend/connected/aas/submodelelement/property/ConnectedCollectionProperty.h
		backend/connected/aas/submodelelement/property/ConnectedCollectionProperty.cpp
    backend/connected/aas/submodelelement/property/ConnectedContainerProperty.h
    backend/connected/aas/submodelelement/property/ConnectedMapProperty.cpp
    backend/connected/aas/submodelelement/property/ConnectedMapProperty.h
    backend/connected/aas/submodelelement/property/ConnectedReferenceElement.cpp
    backend/connected/aas/submodelelement/property/ConnectedReferenceElement.h
    backend/connected/aas/submodelelement/property/ConnectedSingleProperty.cpp
    backend/connected/aas/submodelelement/property/ConnectedSingleProperty.h
    backend/connected/aas/submodelelement/property/blob/ConnectedBlob.cpp
    backend/connected/aas/submodelelement/property/blob/ConnectedBlob.h
    backend/connected/aas/submodelelement/property/file/ConnectedFile.cpp
    backend/connected/aas/submodelelement/property/file/ConnectedFile.h
		backend/connected/aas/dataspecification/IDataSpecification.h
		backend/vab/IVABElementContainer.h
		aas/BlobType.h
		aas/identifier/IIdentifier.h
		aas/MimeType.h
		aas/parts/IAsset.h
		aas/parts/IConceptDescription.h
		aas/parts/IConceptDictionary.h
		aas/parts/IView.h
		aas/PathType.h
		aas/policypoints/IAccessControlPolicyPoints.h
		aas/qualifier/haskind/IHasKind.h
		aas/qualifier/IAdministrativeInformation.h
		aas/qualifier/IHasDataSpecification.h
		aas/qualifier/IHasSemantics.h
		aas/qualifier/IIdentifiable.h
		aas/qualifier/IReferable.h
		aas/qualifier/qualifiable/IConstraint.h
		aas/qualifier/qualifiable/IFormula.h
		aas/qualifier/qualifiable/IQualifiable.h
		aas/qualifier/qualifiable/IQualifier.h
		aas/reference/IKey.h
		aas/reference/IReference.h
		aas/security/ISecurity.h
		aas/SubModel.cpp
		aas/SubModel.h
		aas/ISubModel.h
    aas/IAssetAdministrationShell.h
		aas/submodelelement/IRelationshipElement.h
		aas/submodelelement/IElement.h
		aas/submodelelement/ISubmodelElement.h
		aas/submodelelement/ISubmodelElementCollection.h
		aas/submodelelement/operation/IOperationVariable.h
		aas/submodelelement/operation/IOperation.h
		aas/submodelelement/property/blob/IBlob.h
		aas/submodelelement/property/file/IFile.h
		aas/submodelelement/IDataElement.h
		aas/submodelelement/IReferenceElement.h
		aas/submodelelement/property/IProperty.h
		aas/submodelelement/property/ICollectionProperty.h
		aas/submodelelement/property/IContainerProperty.h
		aas/submodelelement/property/ISingleProperty.h
		aas/submodelelement/property/IMapProperty.h
)

target_include_directories(basyx_aas PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(basyx_aas basyx::shared basyx::abstraction basyx::vab)
add_library(basyx::aas ALIAS basyx_aas)

diagnostics_print(basyx_aas)
