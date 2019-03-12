###############################################
###              Util                       ###
###############################################

project(util)

add_library(util)

target_sources(util
    PRIVATE
        util/array.h
        util/any.h
        util/invoke.h
        util/macros.h
        util/meta.h
        util/printer.h
        util/util.cpp
        util/util.h
)

target_include_directories(util PUBLIC ${PROJECT_SOURCE_DIR}) 

diagnostics_print(util)
