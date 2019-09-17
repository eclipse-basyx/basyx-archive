/*
 * holder.h
 *
 *  Created on: 21.02.2019
 *      Author: psota
 */

#ifndef BASYX_ANY_HOLDER_H
#define BASYX_ANY_HOLDER_H

#include <memory>
#include <string>
#include <typeinfo>

#include <json/json.hpp>

//#include "basyx/function/invokable.h"

//#include "basyx/function/function_base.h"

#include "util/printer.h"
#include "util/util.h"

#include "basyx/any/placeholder.h"
//#include "basyx/serialization/json.h"

namespace basyx {
// Forward declare serialization function

	namespace serialization {
		namespace json {
			template <typename T>
			nlohmann::json serialize(const T& t);
		}
	}

class function_base;

namespace detail {

    // Holder:
    // The actual class holding the object, is derived from PlaceHolder and parametrized through template parameter T
    template <typename T>
    class Holder : public PlaceHolder {
    public:
        using json_t = nlohmann::json;

    public: // structors
        Holder(const T& t)
            : stored(t) {};

        Holder(T&& t)
            : stored(std::move(t)) {};

    public:
        virtual const std::type_info& type() const noexcept override
        {
            return typeid(T);
        };

        virtual void print(std::ostream& os) const override {
            // TODO: Find out for which cases this doesn't work
            //os << stored;
        };

        //virtual bool compare(PlaceHolder * rhs) const override {
        //	if (rhs->type() != this->type())
        //		return false;

        //	return this->stored == static_cast<Holder<T>*>(rhs)->stored;
        //}

        virtual void to_json(json_t& json) const override
        {
            json = basyx::serialization::json::serialize(stored);
        };

		virtual bool is_invokable() const noexcept override
		{
//			return false;
			return std::is_base_of<basyx::function_base, T>::value;
//			return std::is_convertible<T*, basyx::detail::invokable*>::value;
		};

        // Actual stored value
        T stored;

		virtual void * get_address() const noexcept override
		{
			return (void*)&this->stored;
		};

	private:
        Holder& operator=(const Holder&) = delete;
    };
}
}

#endif /* BASYX_ANY_HOLDER_H */
