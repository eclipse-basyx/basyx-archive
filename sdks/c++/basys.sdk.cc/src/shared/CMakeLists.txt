###############################################
###              BaSyx::Shared              ###
###############################################

set (BASYX_SHARED_LIB_SUFFIX "Shared")

set (BASYX_SHARED_LIBRARY_NAME "${PROJECT_SHORTNAME}${BASYX_SHARED_LIB_SUFFIX}")

add_library(${BASYX_SHARED_LIBRARY_NAME})

set_target_properties(${BASYX_SHARED_LIBRARY_NAME} PROPERTIES VERSION ${PROJECT_VERSION})
set_target_properties(${BASYX_SHARED_LIBRARY_NAME} PROPERTIES SOVERSION ${BASYX_PACKAGE_VERSION_MAJOR})

target_sources(${BASYX_SHARED_LIBRARY_NAME}
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

target_include_directories(${BASYX_SHARED_LIBRARY_NAME} PUBLIC ${PROJECT_SOURCE_DIR})
target_link_libraries(${BASYX_SHARED_LIBRARY_NAME} BaSyx::Util BaSyx::Log lib::json)
add_library(${PROJECT_SHORTNAME}::${BASYX_SHARED_LIB_SUFFIX} ALIAS ${BASYX_SHARED_LIBRARY_NAME})
add_library(basyx::shared ALIAS ${BASYX_SHARED_LIBRARY_NAME})

###############################################
###           Install section               ###
###############################################

configure_file("${BASYX_SHARED_LIBRARY_NAME}Config.cmake.in" "${BASYX_SHARED_LIBRARY_NAME}Config.cmake" @ONLY)

install(TARGETS ${BASYX_SHARED_LIBRARY_NAME}
        ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR})

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/basyx"
        DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/${PROJECT_SHORTNAME}"
        FILES_MATCHING PATTERN "*.h")

install(FILES ${CMAKE_CURRENT_SOURCE_DIR}/${BASYX_SHARED_LIBRARY_NAME}Config.cmake DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/${PROJECT_SHORTNAME})

diagnostics_print(${BASYX_SHARED_LIBRARY_NAME})
