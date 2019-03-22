###############################################
###              tests_backends             ###
###############################################

cmake_minimum_required(VERSION 2.8.4)
project(tests_backends)

###''' SOURCE AND HEADER FILES '''###

file(GLOB_RECURSE SOURCE_FILES *.cpp *.cc)
file(GLOB_RECURSE HEADER_FILES *.h)

### tests_backends ###

add_executable(tests_backends ${SOURCE_FILES} ${HEADER_FILES})
target_include_directories(tests_backends PRIVATE ${PROJECT_SOURCE_DIR})
target_link_libraries(tests_backends basyx backends gtest testmain abstraction)

diagnostics_print(tests_backends)