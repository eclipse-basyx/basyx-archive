###############################################
###          enable_playground              ###
###############################################
###
### Load instructions from a testing.cmake file
### This can be used, to create build targets
### for testing purposes
###

function( enable_playground )
	if(EXISTS "${CMAKE_CURRENT_SOURCE_DIR}/testing.cmake")
		include("${CMAKE_CURRENT_SOURCE_DIR}/testing.cmake")
	endif()
endfunction()