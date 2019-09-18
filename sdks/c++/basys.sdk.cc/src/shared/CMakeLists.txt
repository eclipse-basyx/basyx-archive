###############################################
###              BaSyx::Shared              ###
###############################################

project(basyx)
add_library(basyx_shared)

target_sources(basyx_shared
    PRIVATE
        basyx/any/any.h
        basyx/any/bad_any_cast.h
        basyx/any/holder.h
        basyx/any/placeholder.h
        basyx/any.h
        basyx/basysid/BaSysID.cpp
        basyx/basysid/BaSysID.h
        basyx/function/function.h
        basyx/function/function_base.h
        basyx/function/invokable.h
        basyx/function.h
        basyx/serialization/json/json.h
        basyx/serialization/json/json_deserializer.h
        basyx/serialization/json/json_serializer.h
        basyx/serialization/json/typeid.cpp
        basyx/serialization/json/typeid.h
        basyx/serialization/json.h
        basyx/types.h
		basyx/anyTypeChecker.h
)

target_include_directories(basyx_shared PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(basyx_shared basyx::util basyx::log lib::json)
add_library(basyx::shared ALIAS basyx_shared)

diagnostics_print(basyx_shared)
