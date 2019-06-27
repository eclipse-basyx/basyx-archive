/*
 * holder.h
 *
 *  Created on: 21.02.2019
 *      Author: psota
 */


#ifndef UTIL_ANY_HOLDER_H
#define UTIL_ANY_HOLDER_H

#include <memory>
#include <typeinfo>
#include <string>

#include <json/json.hpp>

#include "util/util.h"
#include "util/printer.h"

#include "util/any/placeholder.h"

namespace basyx {
	// Forward declare serialization function
	namespace json {
		template<typename T>
		extern nlohmann::json serialize(const T & t);
	}
	namespace detail {


		// Holder:
		// The actual class holding the object, is derived from PlaceHolder and parametrized through template parameter T
		template<typename T>
		class Holder : public PlaceHolder
		{
		public:
			using json_t = nlohmann::json;
		public: // structors
			Holder(const T & t)
				: stored(t)
			{};

			Holder(T && t)
				: stored(std::move(t))
			{};

		public:
			virtual const std::type_info & type() const noexcept override {
				return typeid(T);
			};

			virtual void print(std::ostream & os) const override {
				// TODO: Find out for which cases this doesn't work
				//os << stored;
			};

			//virtual bool compare(PlaceHolder * rhs) const override {
			//	if (rhs->type() != this->type())
			//		return false;

			//	return this->stored == static_cast<Holder<T>*>(rhs)->stored;
			//}

			virtual void to_json(json_t & json) const override
			{
				json = basyx::json::serialize(stored);
			};

			// Actual stored value
			T stored;
		private:
			Holder & operator=(const Holder &) = delete;
		};
	}
}


#endif
