/* ************************************************************************************************
 * BaSys Type System
 *
 * Define type IDs
 * ************************************************************************************************/

#ifndef BASYX_TYPES_H
#define BASYX_TYPES_H

#include <set>
#include <unordered_map>
#include <unordered_set>
#include <vector>
#include <memory>

namespace basyx {

  template<typename T>
    using specificCollection_t = std::vector<std::shared_ptr<T>>;

  template<typename T>
   using specificMap_t = std::unordered_map<std::string, std::shared_ptr<T >> ;

	enum class BaseType
	{
		Primitive,
		HashMap,
		Set,
		List
	};

	enum class ValueType
	{
		Bool,
		Int,
		Float,
		String
	};
};

enum BaSyxCommand {
	GET = 1,
	SET = 2,
	CREATE = 3,
	DEL = 4, // DELETE is a reserved keyword
	INVOKE = 5
};

#define BASYX_FRAMESIZE_SIZE 4
#define BASYX_STRINGSIZE_SIZE 4

#endif /* BASYX_TYPES_H */
