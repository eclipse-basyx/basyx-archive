###############################################
###              BaSyx::Submodel            ###
###############################################
include(CMakePackageConfigHelpers)

set (BASYX_SUBMODEL_LIB_SUFFIX "Submodel")

set (BASYX_SUBMODEL_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_SUBMODEL_LIB_SUFFIX}")

add_library(${BASYX_SUBMODEL_LIB_SUFFIX})

set_target_properties(${BASYX_SUBMODEL_LIB_SUFFIX} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_SUBMODEL_LIB_SUFFIX} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})
set_target_properties(${BASYX_SUBMODEL_LIB_SUFFIX} PROPERTIES PREFIX "lib${PROJECT_SHORTNAME}")
target_include_directories(${BASYX_SUBMODEL_LIB_SUFFIX}
INTERFACE ${CMAKE_INSTALL_PREFIX}/include
       ${CMAKE_INSTALL_PREFIX}/include/${PROJECT_SHORTNAME})

target_sources(${BASYX_SUBMODEL_LIB_SUFFIX}
    PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/api/constant_definitions.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/ConnectedElement.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/ConnectedSubmodel.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/ConnectedVABModelMap.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/ConnectedDataElement.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/ConnectedRelationshipElement.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/ConnectedSubmodelElement.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/ConnectedSubmodelElementCollection.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/operation/ConnectedOperation.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/property/blob/ConnectedBlob.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/property/ConnectedCollectionProperty.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/property/ConnectedMapProperty.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/property/ConnectedProperty.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/property/ConnectedReferenceElement.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/property/ConnectedSingleProperty.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/connected/submodelelement/property/file/ConnectedFile.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/identifier/Identifier.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/modeltype/ModelType.cpp
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
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/operation/Operation.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/operation/OperationVariable.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/property/Property.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/ReferenceElement.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/RelationshipElement.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/SubmodelElement.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/submodel/map/submodelelement/SubmodelElementCollection.cpp
)

target_include_directories(${BASYX_SUBMODEL_LIB_SUFFIX} PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(${BASYX_SUBMODEL_LIB_SUFFIX}
    PUBLIC
    BaSyx::Shared
    BaSyx::VAB
)

add_library(basyx::submodel ALIAS ${BASYX_SUBMODEL_LIB_SUFFIX})
add_library(${PROJECT_SHORTNAME}::${BASYX_SUBMODEL_LIB_SUFFIX} ALIAS ${BASYX_SUBMODEL_LIB_SUFFIX})

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

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/../include/${PROJECT_SHORTNAME}/submodel"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h"
)

diagnostics_print(${BASYX_SUBMODEL_LIB_SUFFIX})

endif()
