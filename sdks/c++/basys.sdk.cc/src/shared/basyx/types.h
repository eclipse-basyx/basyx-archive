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

namespace basyx {

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
