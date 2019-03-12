###############################################
###          enable_clang_tidy              ###
###############################################
###
### Enables a clang-tidy target, if
### clang-tidy is installed, to run static
### code analysis on all sources
###

function( enable_clang_tidy )
	set(SOURCE_DIR "${CMAKE_SOURCE_DIR}/src")
	file(GLOB_RECURSE SOURCE_FILES *.c *.cpp *.cxx *.cc )
	file(GLOB_RECURSE HEADER_FILES *.h *.hpp *.hxx *.hh )

	list(FILTER SOURCE_FILES EXCLUDE REGEX ".*/lib/.*")
	list(FILTER HEADER_FILES EXCLUDE REGEX ".*/lib/.*")
		
	find_program(UTIL_TIDY_PATH clang-tidy PATHS /usr/local/Cellar/llvm/*/bin)
	if(UTIL_TIDY_PATH)
		message(STATUS "Using clang-tidy static-analysis: yes")
		add_custom_target(clang-tidy
			COMMAND ${UTIL_TIDY_PATH} ${SOURCE_FILES} ${HEADER_FILES} -p=./ )
	else()
		message(STATUS "Using clang-tidy static-analysis: no")
	endif()
endfunction()