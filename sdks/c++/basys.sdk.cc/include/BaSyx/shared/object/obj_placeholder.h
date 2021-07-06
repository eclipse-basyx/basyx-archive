/*
 * placeholder.h
 *
 *  Created on: 21.02.2019
 *      Author: psota
 */

#ifndef BASYX_SHARED_OBJECT_OBJ_PLACEHOLDER_H
#define BASYX_SHARED_OBJECT_OBJ_PLACEHOLDER_H

#include <BaSyx/shared/object/object_type.h>

#include <BaSyx/util/printer.h>
#include <BaSyx/util/util.h>

#include <nlohmann/json.hpp>

namespace basyx {
namespace detail {
    // PlaceHolder:
    // Interface class for the actual value to be stored by the object object
    // Allows introspection of type
    class objPlaceHolder {
	public:
		enum class HolderType{
			Owning,
			Observing,
			Error
		};
	public:
		virtual void * get_address() const noexcept = 0;
        virtual const std::type_info& type() const noexcept = 0;
        virtual ~objPlaceHolder() = default;
    //    virtual void print(std::ostream& os) const = 0;
		virtual objPlaceHolder::HolderType get_holder_type() const = 0;
       	virtual bool compare(objPlaceHolder * rhs) const = 0;

		virtual void to_json(nlohmann::json& json) const = 0;
		
		virtual basyx::type::objectType object_type() const = 0;
		virtual basyx::type::valueType value_type() const = 0;
    };
}
};

#endif /* BASYX_SHARED_OBJECT_OBJ_PLACEHOLDER_H */
