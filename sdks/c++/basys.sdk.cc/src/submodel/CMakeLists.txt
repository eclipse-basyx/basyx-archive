###############################################
###              BaSyx::Submodel            ###
###############################################

set (BASYX_SUBMODEL_LIB_SUFFIX "Submodel")

set (BASYX_SUBMODEL_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_SUBMODEL_LIB_SUFFIX}")

add_library(${BASYX_SUBMODEL_LIBRARY_NAME})

set_target_properties(${BASYX_SUBMODEL_LIBRARY_NAME} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_SUBMODEL_LIBRARY_NAME} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})

target_sources(${BASYX_SUBMODEL_LIBRARY_NAME}
    PRIVATE
		submodel/api/constant_definitions.cpp
		submodel/api/identifier/IIdentifier.h
		submodel/api/IElement.h
		submodel/api/ISubModel.h
		submodel/api/qualifier/IHasKind.h
		submodel/api/qualifier/IAdministrativeInformation.h
		submodel/api/qualifier/IHasDataSpecification.h
		submodel/api/qualifier/IHasSemantics.h
		submodel/api/qualifier/IIdentifiable.h
		submodel/api/qualifier/IReferable.h
		submodel/api/qualifier/qualifiable/IConstraint.h
		submodel/api/qualifier/qualifiable/IFormula.h
		submodel/api/qualifier/qualifiable/IQualifiable.h
		submodel/api/qualifier/qualifiable/IQualifier.h
		submodel/api/reference/IKey.h
		submodel/api/reference/IReference.h
		submodel/api/submodelelement/IDataElement.h
		submodel/api/submodelelement/IReferenceElement.h
		submodel/api/submodelelement/IRelationshipElement.h
		submodel/api/submodelelement/ISubmodelElement.h
		submodel/api/submodelelement/ISubmodelElementCollection.h
		submodel/api/submodelelement/operation/IOperation.h
		submodel/api/submodelelement/operation/IOperationVariable.h
		submodel/api/submodelelement/property/blob/IBlob.h
		submodel/api/submodelelement/property/file/IFile.h
		submodel/api/submodelelement/property/ICollectionProperty.h
		submodel/api/submodelelement/property/IContainerProperty.h
		submodel/api/submodelelement/property/IMapProperty.h
		submodel/api/submodelelement/property/IProperty.h
		submodel/api/submodelelement/property/ISingleProperty.h
		submodel/connected/ConnectedElement.cpp
		submodel/connected/ConnectedElement.h
		submodel/connected/ConnectedSubmodel.cpp
		submodel/connected/ConnectedSubmodel.h
		submodel/connected/ConnectedVABModelMap.cpp
		submodel/connected/ConnectedVABModelMap.h
		submodel/connected/submodelelement/ConnectedDataElement.cpp
		submodel/connected/submodelelement/ConnectedDataElement.h
		submodel/connected/submodelelement/ConnectedRelationshipElement.cpp
		submodel/connected/submodelelement/ConnectedRelationshipElement.h
		submodel/connected/submodelelement/ConnectedSubmodelElement.cpp
		submodel/connected/submodelelement/ConnectedSubmodelElement.h
		submodel/connected/submodelelement/ConnectedSubmodelElementCollection.cpp
		submodel/connected/submodelelement/ConnectedSubmodelElementCollection.h
		submodel/connected/submodelelement/operation/ConnectedOperation.cpp
		submodel/connected/submodelelement/operation/ConnectedOperation.h
		submodel/connected/submodelelement/property/blob/ConnectedBlob.cpp
		submodel/connected/submodelelement/property/blob/ConnectedBlob.h
		submodel/connected/submodelelement/property/ConnectedCollectionProperty.cpp
		submodel/connected/submodelelement/property/ConnectedCollectionProperty.h
		submodel/connected/submodelelement/property/ConnectedContainerProperty.h
		submodel/connected/submodelelement/property/ConnectedMapProperty.cpp
		submodel/connected/submodelelement/property/ConnectedMapProperty.h
		submodel/connected/submodelelement/property/ConnectedProperty.cpp
		submodel/connected/submodelelement/property/ConnectedProperty.h
		submodel/connected/submodelelement/property/ConnectedPropertyFactory.h
		submodel/connected/submodelelement/property/ConnectedReferenceElement.cpp
		submodel/connected/submodelelement/property/ConnectedReferenceElement.h
		submodel/connected/submodelelement/property/ConnectedSingleProperty.cpp
		submodel/connected/submodelelement/property/ConnectedSingleProperty.h
		submodel/connected/submodelelement/property/file/ConnectedFile.cpp
		submodel/connected/submodelelement/property/file/ConnectedFile.h
		submodel/map/identifier/Identifier.cpp
		submodel/map/identifier/Identifier.h
		submodel/map/modeltype/ModelType.cpp
		submodel/map/modeltype/ModelType.h
		submodel/map/identifier/IdentifierType.h
		submodel/map/IVABElementContainer.h
		submodel/map/qualifier/AdministrativeInformation.cpp
		submodel/map/qualifier/AdministrativeInformation.h
		submodel/map/qualifier/Description.cpp
		submodel/map/qualifier/Description.h
		submodel/map/qualifier/HasDataSpecification.cpp
		submodel/map/qualifier/HasDataSpecification.h
		submodel/map/qualifier/HasKind.cpp
		submodel/map/qualifier/HasKind.h
		submodel/map/qualifier/HasSemantics.cpp
		submodel/map/qualifier/HasSemantics.h
		submodel/map/qualifier/Identifiable.cpp
		submodel/map/qualifier/Identifiable.h
		submodel/map/qualifier/qualifiable/Constraint.cpp
		submodel/map/qualifier/qualifiable/Constraint.h
		submodel/map/qualifier/qualifiable/Formula.cpp
		submodel/map/qualifier/qualifiable/Formula.h
		submodel/map/qualifier/qualifiable/Qualifiable.cpp
		submodel/map/qualifier/qualifiable/Qualifiable.h
		submodel/map/qualifier/qualifiable/Qualifier.cpp
		submodel/map/qualifier/qualifiable/Qualifier.h
		submodel/map/qualifier/qualifiable/QualifierType.cpp
		submodel/map/qualifier/qualifiable/QualifierType.h
		submodel/map/qualifier/Referable.cpp
		submodel/map/qualifier/Referable.h
		submodel/map/reference/Key.cpp
		submodel/map/reference/Key.h
		submodel/map/reference/Reference.cpp
		submodel/map/reference/Reference.h
		submodel/map/SubModel.cpp
		submodel/map/SubModel.h
		submodel/map/submodelelement/DataElement.cpp
		submodel/map/submodelelement/DataElement.h
		submodel/map/submodelelement/operation/Operation.cpp
		submodel/map/submodelelement/operation/Operation.h
		submodel/map/submodelelement/operation/OperationVariable.cpp
		submodel/map/submodelelement/operation/OperationVariable.h
		submodel/map/submodelelement/property/Property.cpp
		submodel/map/submodelelement/property/Property.h
		submodel/map/submodelelement/property/valuetypedef/PropertyValueTypeDef.h
		submodel/map/submodelelement/property/valuetypedef/PropertyValueTypeDefHelper.h
		submodel/map/submodelelement/ReferenceElement.cpp
		submodel/map/submodelelement/ReferenceElement.h
		submodel/map/submodelelement/RelationshipElement.cpp
		submodel/map/submodelelement/RelationshipElement.h
		submodel/map/submodelelement/SubmodelElement.cpp
		submodel/map/submodelelement/SubmodelElement.h
		submodel/map/submodelelement/SubmodelElementCollection.cpp
		submodel/map/submodelelement/SubmodelElementCollection.h
)

target_include_directories(${BASYX_SUBMODEL_LIBRARY_NAME} PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(${BASYX_SUBMODEL_LIBRARY_NAME} BaSyx::Shared BaSyx::VAB)
add_library(${PROJECT_SHORTNAME}::${BASYX_SUBMODEL_LIB_SUFFIX} ALIAS ${BASYX_SUBMODEL_LIBRARY_NAME})
add_library(basyx::submodel ALIAS ${BASYX_SUBMODEL_LIBRARY_NAME})

###############################################
###           Install section               ###
###############################################

configure_file("${BASYX_SUBMODEL_LIBRARY_NAME}Config.cmake.in" "${BASYX_SUBMODEL_LIBRARY_NAME}Config.cmake" @ONLY)

install(TARGETS ${BASYX_SUBMODEL_LIBRARY_NAME}
        ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR})

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/submodel"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h")

install(FILES ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_SUBMODEL_LIBRARY_NAME}Config.cmake DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${BASYX_SUBMODEL_LIBRARY_NAME})

diagnostics_print(${BASYX_SUBMODEL_LIBRARY_NAME})
