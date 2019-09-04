###############################################
###              BaSyx::Logging             ###
###############################################

project(basyx)

add_library(logging)

target_sources(logging
    PRIVATE
		log/log.cpp
		log/log.h
)

target_include_directories(logging PUBLIC ${PROJECT_SOURCE_DIR}) 
target_link_libraries(logging basyx::abstraction basyx::util)
add_library(basyx::logging ALIAS logging)


diagnostics_print(logging)
