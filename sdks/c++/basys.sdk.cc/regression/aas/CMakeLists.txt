###############################################
###              tests_aas                  ###
###############################################

cmake_minimum_required(VERSION 2.8.4)
project(tests_aas)

###''' SOURCE AND HEADER FILES '''###

file(GLOB_RECURSE SOURCE_FILES *.cpp *.cc)
file(GLOB_RECURSE HEADER_FILES *.h)

### tests_aas ###

add_executable(tests_aas ${SOURCE_FILES} ${HEADER_FILES})
target_include_directories(tests_aas PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(tests_aas basyx gtest testmain submodels)

diagnostics_print(tests_aas)