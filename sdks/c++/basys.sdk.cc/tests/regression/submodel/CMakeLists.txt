###############################################
###              tests_aas                  ###
###############################################

cmake_minimum_required(VERSION 3.1.0)
project(tests_submodel)


add_executable(${PROJECT_NAME})

target_sources(${PROJECT_NAME}
  PRIVATE
	api/common/test_ElementContainer.cpp
	api/aas/test_AssetAdministrationShell.cpp
	api/constraint/test_Formula.cpp
	api/constraint/test_Qualifier.cpp
	api/qualifier/test_AdministrativeInformation.cpp
	api/qualifier/test_HasDataSpecification.cpp
	api/qualifier/test_Identifiable.cpp
	api/qualifier/test_Qualifiable.cpp
	api/qualifier/test_Referable.cpp
	api/reference/test_Reference.cpp
	api/dataspecification/test_ValueList.cpp
	api/dataspecification/test_DataSpecificationPhysicalUnit.cpp
	api/parts/test_ConceptDictionary.cpp
	api/parts/test_ConceptDescription.cpp
	api/parts/test_View.cpp
	api/property/test_Property.cpp
	api/common/test_LangStringSet.cpp
	api/property/test_Property.cpp
	map_v2/test_Submodel.cpp
	map_v2/common/test_ElementContainer.cpp
	map_v2/dataspecification/test_DataSpecification.cpp
	map_v2/dataspecification/test_DataSpecificationIEC61360.cpp
	map_v2/identifier/test_Identifier.cpp
)

target_include_directories(${PROJECT_NAME} PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(${PROJECT_NAME} basyx::util basyx::shared lib::gtest basyx::vab tests::support basyx::submodel tests::main)
gtest_discover_tests(${PROJECT_NAME})

diagnostics_print(${PROJECT_NAME})
