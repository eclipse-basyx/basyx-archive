###############################################
###              BaSyx::Submodel            ###
###############################################

include(CMakePackageConfigHelpers)

set (BASYX_SUBMODEL_LIB_SUFFIX "Submodel")

set (BASYX_SUBMODEL_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_SUBMODEL_LIB_SUFFIX}")

set (BASYX_SUBMODEL_INCLUDE_DIR "${BASYX_INCLUDE_DIR}/BaSyx/submodel")
set (PROJECT_INCLUDE_DIR BASYX_SUBMODEL_INCLUDE_DIR)

add_library(${BASYX_SUBMODEL_LIB_SUFFIX})

set_target_properties(${BASYX_SUBMODEL_LIB_SUFFIX} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_SUBMODEL_LIB_SUFFIX} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})
set_target_properties(${BASYX_SUBMODEL_LIB_SUFFIX} PROPERTIES PREFIX "${CMAKE_STATIC_LIBRARY_PREFIX}${PROJECT_SHORTNAME}")
target_include_directories(${BASYX_SUBMODEL_LIB_SUFFIX}
INTERFACE ${CMAKE_INSTALL_PREFIX}/include
       ${CMAKE_INSTALL_PREFIX}/include/${PROJECT_SHORTNAME})


target_sources(${BASYX_SUBMODEL_LIB_SUFFIX}
    PRIVATE
	    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/enumerations/AssetKind.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/enumerations/DataTypeIEC61360.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/enumerations/LevelType.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/enumerations/ModelTypes.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/enumerations/ModelingKind.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/enumerations/EntityType.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/enumerations/ReferableElements.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/enumerations/LocalKeyType.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/enumerations/IdentifierType.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/enumerations/KeyType.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/enumerations/KeyElements.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/enumerations/IdentifiableElements.cpp
	
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/aas/AssetAdministrationShell.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/aas/Asset.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/common/LangStringSet.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/common/ModelType.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/constraint/Formula.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/constraint/Qualifier.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/parts/ConceptDictionary.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/parts/ConceptDescription.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/parts/View.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/qualifier/HasDataSpecification.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/qualifier/Identifiable.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/qualifier/Qualifiable.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/qualifier/Referable.cpp        
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/reference/Reference.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/SubModel.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/submodelelement/operation/Operation.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/submodelelement/operation/OperationVariable.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/submodelelement/property/MultiLanguageProperty.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/submodelelement/property/ReferenceElement.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/submodelelement/SubmodelElement.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/submodelelement/SubmodelElementFactory.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/submodelelement/SubmodelElementCollection.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/dataspecification/DataSpecification.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/dataspecification/DataSpecificationIEC61360.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/dataspecification/ValueList.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/map_v2/dataspecification/DataSpecificationPhysicalUnit.cpp

        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/aas/AssetAdministrationShell.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/aas/Asset.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/common/LangStringSet.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/constraint/Formula.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/constraint/Qualifier.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/dataspecification/DataSpecification.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/dataspecification/DataSpecificationIEC61360.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/dataspecification/DataSpecificationPhysicalUnit.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/dataspecification/ValueList.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/identifier/Identifier.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/parts/ConceptDescription.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/parts/ConceptDictionary.cpp
		${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/parts/View.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/qualifier/AdministrativeInformation.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/qualifier/HasDataSpecification.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/qualifier/Identifiable.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/qualifier/Qualifiable.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/qualifier/Referable.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/reference/Key.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/reference/Reference.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/SubModel.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/submodelelement/DataElement.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/submodelelement/file/Blob.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/submodelelement/file/File.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/submodelelement/operation/Operation.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/submodelelement/operation/OperationVariable.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/submodelelement/property/MultiLanguageProperty.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/submodelelement/property/ReferenceElement.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/submodelelement/SubmodelElementCollection.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/simple/submodelelement/SubmodelElement.cpp
    )


target_sources(${BASYX_SUBMODEL_LIB_SUFFIX}
    PRIVATE
        ${BASYX_SUBMODEL_INCLUDE_DIR}/enumerations/AssetKind.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/enumerations/DataTypeIEC61360.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/enumerations/LevelType.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/enumerations/ModelTypes.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/enumerations/ModelingKind.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/enumerations/EntityType.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/enumerations/ReferableElements.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/enumerations/LocalKeyType.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/enumerations/IdentifierType.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/enumerations/KeyType.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/enumerations/KeyElements.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/enumerations/IdentifiableElements.h
	
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/aas/IAssetAdministrationShell.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/aas/IAsset.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/common/IElementContainer.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/common/ILangStringSet.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/common/IModelType.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/dataspecification/IDataSpecificationContent.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/dataspecification/IDataSpecification.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/dataspecification/IDataSpecificationIEC61360.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/dataspecification/IValueList.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/dataspecification/IEmbeddedDataSpecification.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/dataspecification/IDataSpecificationPhysicalUnit.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/identifier/Identifier.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/constraint/IConstraint.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/constraint/IFormula.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/constraint/IQualifier.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/ISubModel.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/parts/IConceptDescription.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/parts/IConceptDictionary.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/parts/IView.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/qualifier/IHasDataSpecification.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/qualifier/IHasKind.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/qualifier/IHasSemantics.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/qualifier/IIdentifiable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/qualifier/IQualifiable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/qualifier/IReferable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/reference/IKey.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/reference/IReference.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/file/IBlob.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/file/IFile.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/IDataElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/IEntity.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/ISubmodelElementCollection.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/ISubmodelElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/operation/IOperation.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/operation/IOperationVariable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/property/IMultiLanguageProperty.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/property/IProperty.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/property/IReferenceElement.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/IRelationshipElement.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/IAnnotatedRelationshipElement.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/IEvent.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/IBasicEvent.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/ICapability.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/api_v2/submodelelement/IRange.h

        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/aas/AssetAdministrationShell.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/aas/Asset.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/common/ElementContainer.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/common/ElementFactory.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/common/LangStringSet.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/common/ModelType.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/dataspecification/DataSpecificationIEC61360.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/dataspecification/DataSpecificationPhysicalUnit.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/dataspecification/DataSpecification.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/dataspecification/ValueList.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/parts/ConceptDictionary.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/parts/ConceptDescription.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/parts/View.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/constraint/Formula.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/constraint/Qualifier.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/qualifier/HasDataSpecification.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/qualifier/Identifiable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/qualifier/Qualifiable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/qualifier/Referable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/reference/Reference.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/submodelelement/file/Blob.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/submodelelement/file/File.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/submodelelement/operation/Operation.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/submodelelement/operation/OperationVariable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/submodelelement/property/MultiLanguageProperty.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/submodelelement/property/Property.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/submodelelement/property/ReferenceElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/submodelelement/SubmodelElementCollection.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/submodelelement/SubmodelElementFactory.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/submodelelement/SubmodelElement.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/map_v2/SubModel.h

        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/aas/AssetAdministrationShell.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/aas/Asset.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/common/ElementContainer.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/common/LangStringSet.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/simple/constraint/Formula.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/simple/constraint/Qualifier.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/dataspecification/DataSpecification.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/dataspecification/DataSpecificationIEC61360.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/simple/dataspecification/DataSpecificationPhysicalUnit.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/simple/dataspecification/ValueReferencePair.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/simple/dataspecification/ValueList.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/identifier/Identifier.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/parts/ConceptDescription.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/simple/parts/ConceptDictionary.h
		${BASYX_SUBMODEL_INCLUDE_DIR}/simple/parts/View.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/qualifier/AdministrativeInformation.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/qualifier/HasDataSpecification.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/qualifier/Identifiable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/qualifier/Qualifiable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/qualifier/Referable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/reference/Key.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/reference/Reference.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/submodelelement/DataElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/submodelelement/file/Blob.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/submodelelement/file/File.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/submodelelement/operation/Operation.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/submodelelement/operation/OperationVariable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/submodelelement/property/MultiLanguageProperty.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/submodelelement/property/Property.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/submodelelement/property/ReferenceElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/submodelelement/SubmodelElementCollection.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/submodelelement/SubmodelElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/simple/SubModel.h
)

target_include_directories(${BASYX_SUBMODEL_LIB_SUFFIX} PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(${BASYX_SUBMODEL_LIB_SUFFIX}
    PUBLIC
        BaSyx::Shared
        BaSyx::VAB
)

add_library(basyx::submodel ALIAS ${BASYX_SUBMODEL_LIB_SUFFIX})
add_library(${PROJECT_SHORTNAME}::${BASYX_SUBMODEL_LIB_SUFFIX} ALIAS ${BASYX_SUBMODEL_LIB_SUFFIX})

diagnostics_print(${BASYX_SUBMODEL_LIB_SUFFIX})

###############################################
###           Install section               ###
###############################################
if(${BASYX_INSTALL_SDK})

configure_package_config_file(
    ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_SUBMODEL_LIBRARY_NAME}Config.cmake.in
    ${CMAKE_CURRENT_BINARY_DIR}/${BASYX_SUBMODEL_LIBRARY_NAME}Config.cmake
    INSTALL_DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_SUBMODEL_LIBRARY_NAME}
    PATH_VARS
    CMAKE_INSTALL_PREFIX
)

install(FILES ${CMAKE_CURRENT_BINARY_DIR}/${BASYX_SUBMODEL_LIBRARY_NAME}Config.cmake
        DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_SUBMODEL_LIBRARY_NAME}
)

install(EXPORT ${BASYX_SUBMODEL_LIB_SUFFIX}Targets
        NAMESPACE ${PROJECT_SHORTNAME}::
        FILE ${BASYX_SUBMODEL_LIBRARY_NAME}Targets.cmake
        DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_SUBMODEL_LIBRARY_NAME}
)

install(TARGETS ${BASYX_SUBMODEL_LIB_SUFFIX}
        EXPORT ${BASYX_SUBMODEL_LIB_SUFFIX}Targets
        ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
)

install(DIRECTORY "${BASYX_INCLUDE_DIR}/${PROJECT_SHORTNAME}/submodel"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h"
)

endif()