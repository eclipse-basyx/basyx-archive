/*
 * placeholder.h
 *
 *  Created on: 21.02.2019
 *      Author: psota
 */


#ifndef UTIL_ANY_PLACEHOLDER_H
#define UTIL_ANY_PLACEHOLDER_H

#include "util/util.h"
#include "util/printer.h"

#include <json/json.hpp>

namespace basyx {

	class any_serializer;

	namespace detail {
		// PlaceHolder:
		// Interface class for the actual value to be stored by the any object
		// Allows introspection of type
		class PlaceHolder
		{
		public:
			virtual const std::type_info & type() const noexcept = 0;
			virtual ~PlaceHolder() = default;
			virtual void print(std::ostream & os) const = 0;
		//	virtual bool compare(PlaceHolder * rhs) const = 0;
		//	virtual void serialize(any_serializer * serializer) const = 0;
			virtual void to_json(nlohmann::json & json) const = 0;
		};

	}
};

#endif