###############################################
###              BaSyx::Util                ###
###############################################

project(basyx)

add_library(basyx_util)

target_sources(basyx_util
    PRIVATE
        util/array.h
        util/invoke.h
        util/macros.h
        util/meta.h
        util/printer.h
        util/tools/CoderTools.h
        util/tools/StringTools.h
        util/util.cpp
        util/util.h
)

target_include_directories(basyx_util PUBLIC ${PROJECT_SOURCE_DIR}) 
target_link_libraries(basyx_util lib::json)
add_library(basyx::util ALIAS basyx_util)

diagnostics_print(basyx_util)
