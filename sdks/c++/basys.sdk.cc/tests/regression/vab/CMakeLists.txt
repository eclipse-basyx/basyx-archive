###############################################
###              tests_vab                  ###
###############################################

cmake_minimum_required(VERSION 3.1.0)
project(tests_vab)

add_executable(tests_vab)

target_sources(tests_vab
    PRIVATE
        connector/basyx/frame/test_BaSyxNativeFrameBuilder.cpp
        connector/Test_VABBasyxTCP.cpp
        core/proxy/test_vab_elementproxy.cpp
        provider/basyx/frame/test_BaSyxNativeFrameProcessor.cpp
        provider/test_hashmap.cpp
        provider/test_hashmap_collection.cpp
        provider/test_hashmap_map.cpp
        snippet/MapCreateDelete.h
        snippet/MapInvoke.h
        snippet/MapRead.h
		path/test_VABPath.cpp
        support/MockupModelProvider.h
)

target_include_directories(tests_vab PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(tests_vab basyx::util basyx::vab basyx::shared lib::gtest tests::support tests::main)
gtest_discover_tests(tests_vab)

diagnostics_print(tests_vab)
