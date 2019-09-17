#include "SimpleVABElement.h"

#include <basyx/any.h>
#include <basyx/types.h>

#include <basyx/function.h>

#include <set>

namespace basyx {
namespace tests {
namespace support {

	class testFuncs {
	public:
		static bool True() {
			return true;
		};

		static int add(int a, int b) {
			return a + b;
		}
	};


	objectMap_t make_simple_vab_element()
	{
		objectMap_t simpleVABElement;

		objectMap_t primitives;
		primitives.emplace("integer", 123);
		primitives.emplace("double", 3.14);
		primitives.emplace("string", std::string("TestValue"));
		simpleVABElement.emplace("primitives", primitives);

		// Add function types
		objectMap_t functions;
		functions.emplace("supplier", basyx::make_function(testFuncs::True));

		functions.emplace("complex", basyx::make_function(testFuncs::add));
		functions.emplace("serializable", basyx::make_function(testFuncs::add));

		functions.emplace("invalid", true);
		functions.emplace("invokable", basyx::make_function(testFuncs::True));
		simpleVABElement.emplace("operations", functions);

		// Add structure types
		objectMap_t structure;
		objectMap_t emptyMap;
		structure.emplace("map", emptyMap);
		std::set<any> emptySet;
		structure.emplace("set", emptySet);
		std::vector<any> emptyVector;
		structure.emplace("list", emptyVector);
		simpleVABElement.emplace("structure", structure);

		// Add corner cases
		objectMap_t special;
		special.emplace("casesensitivity", true);
		special.emplace("caseSensitivity", false);
		objectMap_t nestedA;
		objectMap_t nestedB;
		nestedB.emplace("value", 100);
		nestedA.emplace("nested", nestedB);
		special.emplace("nested", nestedA);
		special.emplace("null", nullptr);
		simpleVABElement.emplace("special", special);

		return simpleVABElement;
	}

}
}
}