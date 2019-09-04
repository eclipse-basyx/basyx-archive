###############################################
###              BaSyx::Util                ###
###############################################

project(basyx)

add_library(util)

target_sources(util
    PRIVATE
        util/array.h
        util/function.h
        util/invoke.h
        util/macros.h
        util/meta.h
        util/printer.h
        util/tools/CoderTools.h
        util/tools/StringTools.h
        util/util.cpp
        util/util.h
)

target_include_directories(util PUBLIC ${PROJECT_SOURCE_DIR}) 
target_link_libraries(util lib::json)
add_library(basyx::util ALIAS util)

diagnostics_print(util)
