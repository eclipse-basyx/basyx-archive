###############################################
###              BaSyx                      ###
###############################################

project(basyx)

### BaSyx ###

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
        json/JSONTools.cpp
        json/JSONTools.h
        parameter/BParameter.h
        ref/BRef.h
        types/BArray.h
        types/BAny.h
        types/BaSysTypes.h
        types/BNullObject.h
        types/BObject.cc
        types/BObject.h
        types/BObjectCollection.h
        types/BObjectMap.h
        types/BString.h
        types/BType.h
        types/BValue.h
)

target_include_directories(basyx PUBLIC ${PROJECT_SOURCE_DIR} ${CMAKE_SOURCE_DIR} ) 
target_link_libraries(basyx util json)

diagnostics_print(basyx)