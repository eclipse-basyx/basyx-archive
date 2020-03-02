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
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/api/constant_definitions.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/api/dataspecification/datatypes/DataTypeIEC61360.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/api/dataspecification/datatypes/LevelType.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/ConnectedElement.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/ConnectedSubmodel.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/ConnectedVABModelMap.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/ConnectedDataElement.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/ConnectedRelationshipElement.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/ConnectedSubmodelElementCollection.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/ConnectedSubmodelElement.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/operation/ConnectedOperation.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/property/blob/ConnectedBlob.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/property/ConnectedCollectionProperty.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/property/ConnectedMapProperty.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/property/ConnectedProperty.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/property/ConnectedReferenceElement.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/property/ConnectedSingleProperty.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/property/file/ConnectedFile.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/dataspecification/DataSpecificationContent.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/dataspecification/DataSpecification.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/dataspecification/DataSpecificationIEC61360.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/identifier/Identifier.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/modeltype/ModelType.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/parts/ConceptDescription.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/qualifier/AdministrativeInformation.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/qualifier/Description.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/qualifier/HasDataSpecification.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/qualifier/HasKind.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/qualifier/HasSemantics.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/qualifier/Identifiable.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/qualifier/qualifiable/Constraint.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/qualifier/qualifiable/Formula.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/qualifier/qualifiable/Qualifiable.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/qualifier/qualifiable/Qualifier.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/qualifier/qualifiable/QualifierType.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/qualifier/Referable.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/reference/Key.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/reference/Reference.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/SubModel.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/DataElement.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/entity/Entity.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/langstring/LangStringSet.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/operation/Operation.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/operation/OperationVariable.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/property/Blob.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/property/File.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/property/Property.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/ReferenceElement.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/RelationshipElement.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/SubmodelElementCollection.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/SubmodelElement.cpp
    )


target_sources(${BASYX_SUBMODEL_LIB_SUFFIX}
    PRIVATE
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/dataspecification/datatypes/DataTypeIEC61360.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/dataspecification/datatypes/LevelType.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/dataspecification/IDataSpecificationContent.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/dataspecification/IDataSpecification.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/dataspecification/IDataSpecificationIEC61360.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/identifier/IIdentifier.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/IElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/ISubModel.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/parts/IConceptDescription.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/qualifier/IAdministrativeInformation.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/qualifier/IHasDataSpecification.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/qualifier/IHasKind.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/qualifier/IHasSemantics.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/qualifier/IIdentifiable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/qualifier/IReferable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/qualifier/qualifiable/IConstraint.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/qualifier/qualifiable/IFormula.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/qualifier/qualifiable/IQualifiable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/qualifier/qualifiable/IQualifier.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/reference/IKey.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/reference/IReference.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/entity/IEntity.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/IDataElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/IReferenceElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/IRelationshipElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/ISubmodelElementCollection.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/ISubmodelElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/operation/IOperation.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/operation/IOperationVariable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/property/blob/IBlob.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/property/file/IFile.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/property/ICollectionProperty.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/property/IContainerProperty.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/property/IMapProperty.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/property/IProperty.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/langstring/ILangStringSet.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/api/submodelelement/property/ISingleProperty.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/ConnectedElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/ConnectedSubmodel.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/ConnectedVABModelMap.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/submodelelement/ConnectedDataElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/submodelelement/ConnectedRelationshipElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/submodelelement/ConnectedSubmodelElementCollection.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/submodelelement/ConnectedSubmodelElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/submodelelement/operation/ConnectedOperation.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/submodelelement/property/blob/ConnectedBlob.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/submodelelement/property/ConnectedCollectionProperty.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/submodelelement/property/ConnectedContainerProperty.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/submodelelement/property/ConnectedMapProperty.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/submodelelement/property/ConnectedPropertyFactory.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/submodelelement/property/ConnectedProperty.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/submodelelement/property/ConnectedReferenceElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/submodelelement/property/ConnectedSingleProperty.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/connected/submodelelement/property/file/ConnectedFile.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/dataspecification/DataSpecificationContent.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/dataspecification/DataSpecification.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/dataspecification/DataSpecificationIEC61360.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/identifier/Identifier.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/identifier/IdentifierType.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/IVABElementContainer.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/modeltype/ModelType.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/parts/ConceptDescription.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/qualifier/AdministrativeInformation.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/qualifier/Description.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/qualifier/HasDataSpecification.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/qualifier/HasKind.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/qualifier/HasSemantics.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/qualifier/Identifiable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/qualifier/qualifiable/Constraint.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/qualifier/qualifiable/Formula.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/qualifier/qualifiable/Qualifiable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/qualifier/qualifiable/Qualifier.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/qualifier/qualifiable/QualifierType.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/qualifier/Referable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/reference/Key.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/reference/Reference.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/submodelelement/DataElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/submodelelement/entity/Entity.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/submodelelement/operation/Operation.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/submodelelement/operation/OperationVariable.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/submodelelement/langstring/LangStringSet.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/submodelelement/property/Blob.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/submodelelement/property/File.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/submodelelement/property/Property.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/submodelelement/property/valuetypedef/PropertyValueTypeDef.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/submodelelement/property/valuetypedef/PropertyValueTypeDefHelper.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/submodelelement/ReferenceElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/submodelelement/RelationshipElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/submodelelement/SubmodelElementCollection.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/submodelelement/SubmodelElement.h
        ${BASYX_SUBMODEL_INCLUDE_DIR}/map/SubModel.h
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
