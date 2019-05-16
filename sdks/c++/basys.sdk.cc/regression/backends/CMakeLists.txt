###############################################
###              tests_backends             ###
###############################################

cmake_minimum_required(VERSION 3.1.0)
project(tests_backends)

add_executable(tests_backends)

target_sources(tests_backends
    PRIVATE
		protocols/basyx/test_BaSyxNative.cpp
		protocols/connector/basyx/frame/test_BaSyxNativeFrameBuilder.cpp
		protocols/provider/basyx/frame/MockupModelProvider.h
		protocols/provider/basyx/frame/test_BaSyxNativeFrameProcessor.cpp
)

target_include_directories(tests_backends PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(tests_backends basyx backends gtest testmain abstraction)
gtest_discover_tests(tests_backends)

diagnostics_print(tests_backends)