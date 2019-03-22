###############################################
###              submodels                  ###
###############################################

project(submodels)

### submodels ###

add_library(submodels)

target_sources(submodels
    PRIVATE
        DeviceStatusSubModel.cc
)

target_link_libraries(submodels basyx)

### tests_submodels ###

add_executable(tests_submodels )
target_sources(tests_submodels
    PRIVATE
        test_devicestatussm.cc
)

target_link_libraries(tests_submodels submodels basyx gtest testmain)

diagnostics_print(submodels)
diagnostics_print(tests_submodels)