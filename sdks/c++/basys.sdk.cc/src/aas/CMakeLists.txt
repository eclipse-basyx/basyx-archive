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
		aas/backend/connected/ConnectedElement.cpp
		aas/backend/connected/ConnectedElement.h
		aas/backend/connected/submodelelement/ConnectedDataElement.cpp
		aas/backend/connected/submodelelement/ConnectedDataElement.h
		aas/backend/connected/submodelelement/ConnectedRelationshipElement.h
		aas/backend/connected/submodelelement/ConnectedRelationshipElement.cpp
		aas/backend/connected/submodelelement/ConnectedSubmodelElement.h
		aas/backend/connected/submodelelement/ConnectedSubmodelElement.cpp
		aas/backend/connected/submodelelement/ConnectedSubmodelElementCollection.h
		aas/backend/connected/submodelelement/ConnectedSubmodelElementCollection.cpp
		aas/backend/connected/submodelelement/operation/ConnectedOperation.h
		aas/backend/connected/submodelelement/operation/ConnectedOperation.cpp
		aas/backend/connected/submodelelement/property/ConnectedProperty.h
		aas/backend/connected/submodelelement/property/ConnectedProperty.cpp
		aas/backend/connected/submodelelement/property/ConnectedCollectionProperty.h
		aas/backend/connected/submodelelement/property/ConnectedCollectionProperty.cpp
    aas/backend/connected/submodelelement/property/ConnectedContainerProperty.cpp
    aas/backend/connected/submodelelement/property/ConnectedContainerProperty.h
    aas/backend/connected/submodelelement/property/ConnectedMapProperty.cpp
    aas/backend/connected/submodelelement/property/ConnectedMapProperty.h
    aas/backend/connected/submodelelement/property/ConnectedReferenceElement.cpp
    aas/backend/connected/submodelelement/property/ConnectedReferenceElement.h
    aas/backend/connected/submodelelement/property/ConnectedSingleProperty.cpp
    aas/backend/connected/submodelelement/property/ConnectedSingleProperty.h
    aas/backend/connected/submodelelement/property/blob/ConnectedBlob.cpp
    aas/backend/connected/submodelelement/property/blob/ConnectedBlob.h
    aas/backend/connected/submodelelement/property/file/ConnectedFile.cpp
    aas/backend/connected/submodelelement/property/file/ConnectedFile.h
		aas/backend/connected/dataspecification/IDataSpecification.h
		aas/backend/vab/IVABElementContainer.h
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
