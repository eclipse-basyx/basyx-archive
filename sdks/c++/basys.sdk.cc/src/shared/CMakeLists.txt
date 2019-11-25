###############################################
###              BaSyx::Shared              ###
###############################################

project(basyx)
add_library(basyx_shared)

target_sources(basyx_shared
    PRIVATE
		basyx/anyTypeChecker.h
		basyx/basysid/BaSysID.cpp
		basyx/basysid/BaSysID.h
		basyx/object/bad_object_cast.h
		basyx/object/impl/object_access_impl.h
		basyx/object/impl/object_cast_impl.h
		basyx/object/impl/object_factories_impl.h
		basyx/object/impl/object_impl.cpp
		basyx/object/impl/object_meta_impl.h
		basyx/object/object_header.h
		basyx/object/object_map_facade.h
		basyx/object/object_type.cpp
		basyx/object/object_type.h
		basyx/object/obj_function.h
		basyx/object/obj_holder.h
		basyx/object/obj_placeholder.h
		basyx/object/obj_ref_holder.h
		basyx/object.h
		basyx/serialization/json/json.h
		basyx/serialization/json/json_deserializer.h
		basyx/serialization/json/json_serializer.h
		basyx/serialization/json/typeid.cpp
		basyx/serialization/json/typeid.h
		basyx/serialization/json.h
		basyx/types.h
)

target_include_directories(basyx_shared PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(basyx_shared basyx::util basyx::log lib::json)
add_library(basyx::shared ALIAS basyx_shared)

diagnostics_print(basyx_shared)
