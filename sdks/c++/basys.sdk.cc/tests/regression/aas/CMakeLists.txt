###############################################
###              tests_aas                  ###
###############################################

cmake_minimum_required(VERSION 3.1.0)
project(tests_aas)


add_executable(tests_aas)

target_sources(tests_aas
  PRIVATE
	  support/VABProxyMock.cpp
    backend/connected/test_ConnectedDataElement.cpp
    backend/connected/test_ConnectedElement.cpp
    backend/connected/submodelelement/property/test_ConnectedMapProperty.cpp
    backend/connected/submodelelement/property/test_ConnectedProperty.cpp
    backend/connected/submodelelement/test_ConnectedRelationshipElement.cpp
)

target_include_directories(tests_aas PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(tests_aas lib::gtest basyx::aas tests::main)
gtest_discover_tests(tests_aas)

diagnostics_print(tests_aas)
