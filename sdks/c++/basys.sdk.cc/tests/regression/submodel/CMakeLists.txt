###############################################
###              tests_aas                  ###
###############################################

cmake_minimum_required(VERSION 3.1.0)
project(tests_submodel)


add_executable(${PROJECT_NAME})

target_sources(${PROJECT_NAME}
  PRIVATE
    support/KeyMock.hpp
    support/ReferenceMock.hpp
    support/IdentifiableMock.hpp
    support/IdentifierMock.hpp
    support/ReferableMock.hpp
    support/AdministrativeInformationMock.hpp
    support/AdditionalAssertions.hpp
    support/constant_definitions.cpp

	map/test_Submodel.cpp
    map/qualifier/test_HasKind.cpp
    map/qualifier/test_HasDataSpecification.cpp
    map/qualifier/test_HasSemantics.cpp
    map/qualifier/test_Identifiable.cpp
    map/qualifier/test_Referable.cpp
    map/qualifier/qualifiable/test_Constraint.cpp
    map/qualifier/qualifiable/test_Formula.cpp
    map/qualifier/qualifiable/test_Qualifiable.cpp
    map/qualifier/qualifiable/test_Qualifier.cpp
    map/reference/test_Key.cpp
    map/reference/test_Reference.cpp
    map/submodelelement/operation/test_OperationVariable.cpp
    map/submodelelement/test_LangStringSet.cpp
    map/submodelelement/test_DataElement.cpp
    map/submodelelement/test_ReferenceElement.cpp
)

target_include_directories(${PROJECT_NAME} PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(${PROJECT_NAME} basyx::util basyx::shared lib::gtest basyx::vab tests::support basyx::submodel tests::main)
gtest_discover_tests(${PROJECT_NAME})

diagnostics_print(${PROJECT_NAME})
