#include "SimpleVABElement.h"

#include <basyx/any.h>
#include <basyx/types.h>

#include <set>

namespace basyx {
namespace tests {
namespace support {

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
/*		functions.emplace("supplier", []() { return true;});

		functions.emplace("serverException", [](){
			throw "ServerException";
		});

		functions.emplace("nullException", [](){
			throw "NullPointerException";
		});

		functions.emplace("nullException", [](){
			throw "NullPointerException";
		});

		functions.emplace("serializable", [](any a, any b) {
			return a.Get<int>() + b.Get<int>();
		});

		functions.emplace("invalid", true);
		functions.emplace("invokable", [](){
			return true;
		});*/
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