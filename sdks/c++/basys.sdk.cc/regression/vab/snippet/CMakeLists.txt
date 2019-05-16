###############################################
###              tests::vab::snippet        ###
###############################################

add_library(tests_vab_snippet)

target_sources(tests_vab_snippet
    PRIVATE
	CreateDelete.h
	GetPropertyValue.h
)

target_include_directories(tests_vab_snippet PUBLIC ${PROJECT_SOURCE_DIR}) 
target_link_libraries(tests_vab_snippet util json)

diagnostics_print(tests_vab_snippet)
