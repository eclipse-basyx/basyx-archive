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
  aas/api/constant_definitions.h
##  aas/api/dataspecification/IDataSpecification.h
##  aas/api/dataspecification/IDataSpecificationContent.h
##  aas/api/dataspecification/IDataSpecificationIEC61360.h
  aas/api/manager/IAssetAdministrationShellManager.h
  aas/api/metamodel/IAssetAdministrationShell.h
  aas/api/parts/IAsset.h
  aas/api/parts/IConceptDictionary.h
  aas/api/parts/IView.h
  aas/api/policypoints/IAccessControlPolicyPoints.h
  aas/api/security/ISecurity.h
#  aas/connected/aas/ConnectedAssetAdministrationShell.cpp
#  aas/connected/aas/ConnectedAssetAdministrationShell.h
#  aas/connected/aas/ConnectedAssetAdministrationShellManager.cpp
#  aas/connected/aas/ConnectedAssetAdministrationShellManager.h
#  aas/connected/aas/dataspecification/IDataSpecification.h
##  aas/map/dataspecification/DataSpecification.cpp
##  aas/map/dataspecification/DataSpecification.h
##  aas/map/dataspecification/DataSpecificationContent.cpp
##  aas/map/dataspecification/DataSpecificationContent.h
##  aas/map/dataspecification/DataSpecificationIEC61360.cpp
##  aas/map/dataspecification/DataSpecificationIEC61360.h
  aas/map/dataspecification/datatypes/DataTypeIEC61360.hpp
  aas/map/dataspecification/datatypes/LevelType.hpp
  aas/map/BlobType.h
  aas/map/descriptor/ModelDescriptor.cpp
  aas/map/descriptor/ModelDescriptor.h
  aas/map/descriptor/SubModelDescriptor.cpp
  aas/map/descriptor/SubModelDescriptor.h
  aas/map/metamodel/AssetAdministrationShell.cpp
  aas/map/metamodel/AssetAdministrationShell.h
  aas/map/manager/AssetAdministrationShellManager.cpp
  aas/map/manager/AssetAdministrationShellManager.h
  aas/map/MimeType.h
  aas/map/modelurn/ModelUrn.cpp
  aas/map/modelurn/ModelUrn.h
  aas/map/parts/Asset.cpp
  aas/map/parts/Asset.h
##  aas/map/parts/ConceptDescription.cpp
##  aas/map/parts/ConceptDescription.h
  aas/map/parts/ConceptDictionary.cpp
  aas/map/parts/ConceptDictionary.h
  aas/map/parts/View.cpp
  aas/map/parts/View.h
  aas/map/PathType.h
  aas/map/security/Security.cpp
  aas/map/security/Security.h
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
