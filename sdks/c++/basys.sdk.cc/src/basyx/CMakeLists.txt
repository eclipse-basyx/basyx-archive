###############################################
###              BaSyx                      ###
###############################################

project(basyx)
add_library(basyx)

target_sources(basyx
    PRIVATE
        aas/AssetAdministrationShell.h
        aas/RTTIMacros.h
        aas/SubModel.h
        api/IAASManager.h
        api/IAssetAdministrationShell.h
        api/IElement.h
        api/IModelProvider.h
        api/ISubModel.h
        basysid/BaSysID.cpp
        basysid/BaSysID.h
        json/json.h
	json/json_deserializer.h
        json/json_serializer.h
        json/typeid.cpp
        json/typeid.h
        parameter/BParameter.h
        types/BaSysTypes.h
)

target_include_directories(basyx PUBLIC ${PROJECT_SOURCE_DIR} ${CMAKE_SOURCE_DIR} ) 
target_link_libraries(basyx util json)

diagnostics_print(basyx)
