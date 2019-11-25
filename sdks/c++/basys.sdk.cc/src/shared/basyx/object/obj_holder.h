/*
 * holder.h
 *
 *  Created on: 21.02.2019
 *      Author: psota
 */

#ifndef BASYX_object_HOLDER_H
#define BASYX_object_HOLDER_H

#include <memory>
#include <string>
#include <typeinfo>

#include <json/json.hpp>

#include "util/printer.h"
#include "util/util.h"

#include "basyx/object/obj_placeholder.h"
#include "basyx/serialization/json.h"

namespace basyx {
namespace detail {

    // Holder:
    // The actual class holding the object, is derived from PlaceHolder and parametrized through template parameter T
    template <typename T>
    class objHolder : public objPlaceHolder {
    public:
        using json_t = nlohmann::json;
	public:
		// Actual stored value
		T stored;
    public: // structors
		objHolder(const T& t)
            : stored(t) {};

		objHolder(T&& t)
            : stored(std::move(t)) {};

	public:
        virtual const std::type_info& type() const noexcept override
        {
            return typeid(T);
        };

		virtual objPlaceHolder::HolderType get_holder_type() const noexcept override
		{
			return objPlaceHolder::HolderType::Owning;
		};

        virtual bool compare(objPlaceHolder * rhs) const override {
        	if (rhs->type() != this->type())
        		return false;

        	return this->stored == static_cast<objHolder<T>*>(rhs)->stored;
        }

        virtual void to_json(json_t& json) const override
        {
            json = basyx::serialization::json::serialize(stored);
        };

		virtual void * get_address() const noexcept override
		{
			return (void*)&this->stored;
		};

		virtual basyx::type::objectType object_type() const override
		{
			return basyx::type::basyx_type<T>::object_type;
		};
				
		virtual basyx::type::valueType value_type() const override
		{
			return basyx::type::basyx_type<T>::value_type;
		};

	private:
        objHolder& operator=(const objHolder&) = delete;
    };
}
}

#endif /* BASYX_object_HOLDER_H */