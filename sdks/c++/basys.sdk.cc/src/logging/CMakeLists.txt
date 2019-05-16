###############################################
###              Logging                    ###
###############################################

project(logging)

add_library(logging)

target_sources(logging
    PRIVATE
		log/log.cpp
		log/log.h
)

target_include_directories(logging PUBLIC ${PROJECT_SOURCE_DIR}) 
target_link_libraries(logging abstraction util)

diagnostics_print(logging)