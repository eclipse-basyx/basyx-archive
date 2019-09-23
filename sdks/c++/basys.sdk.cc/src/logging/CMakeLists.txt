###############################################
###              BaSyx::Logging             ###
###############################################

project(basyx)

add_library(basyx_log)

target_sources(basyx_log
    PRIVATE
		log/log.cpp
		log/log.h
)

target_include_directories(basyx_log PUBLIC ${PROJECT_SOURCE_DIR}) 
target_link_libraries(basyx_log basyx::abstraction basyx::util fmt)
add_library(basyx::log ALIAS basyx_log)


diagnostics_print(basyx_log)
