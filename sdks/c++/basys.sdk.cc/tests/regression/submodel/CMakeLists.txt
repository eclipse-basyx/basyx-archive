###############################################
###              tests_aas                  ###
###############################################

cmake_minimum_required(VERSION 3.1.0)
project(tests_submodel)


add_executable(${PROJECT_NAME})

target_sources(${PROJECT_NAME}
  PRIVATE
	api/constraint/test_Formula.cpp
	api/constraint/test_Qualifier.cpp
	api/qualifier/test_HasDataSpecification.cpp
	api/qualifier/test_Identifiable.cpp
	api/qualifier/test_Qualifiable.cpp
	api/qualifier/test_Referable.cpp
	api/reference/test_Reference.cpp
	map/common/test_ElementContainer.cpp
	map/submodelelement/test_LangStringSet.cpp
)

target_include_directories(${PROJECT_NAME} PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(${PROJECT_NAME} basyx::util basyx::shared lib::gtest basyx::vab tests::support basyx::submodel tests::main)
gtest_discover_tests(${PROJECT_NAME})

diagnostics_print(${PROJECT_NAME})