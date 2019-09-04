###############################################
###              BaSyx::Shared              ###
###############################################

project(basyx)
add_library(shared)

target_sources(shared
    PRIVATE
        basyx/any/any.h
        basyx/any/bad_any_cast.h
        basyx/any/holder.h
        basyx/any/placeholder.h
        basyx/any.h
        basyx/basysid/BaSysID.cpp
        basyx/basysid/BaSysID.h
        basyx/serialization/json/json.h
        basyx/serialization/json/json_deserializer.h
        basyx/serialization/json/json_serializer.h
        basyx/serialization/json/typeid.cpp
        basyx/serialization/json/typeid.h
        basyx/serialization/json.h
        basyx/types.h
)

target_include_directories(shared PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(shared basyx::util lib::json)
add_library(basyx::shared ALIAS shared)

diagnostics_print(shared)
