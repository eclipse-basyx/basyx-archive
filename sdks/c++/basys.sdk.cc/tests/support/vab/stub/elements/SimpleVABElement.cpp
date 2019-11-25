#include "SimpleVABElement.h"

#include <basyx/object.h>
#include <basyx/types.h>

#include <set>

namespace basyx {
namespace tests {
namespace support {

	class testFuncs {
	public:
		static int add(int a, int b) {
			return a + b;
		}
	};


	basyx::object make_simple_vab_element()
	{
		basyx::object vabElement = basyx::object::make_map();

		// Add primivites
		vabElement.insertKey("primitives", basyx::object::make_map());
		vabElement.getProperty("primitives").insertKey("integer", 123);
		vabElement.getProperty("primitives").insertKey("double", 3.14);
		vabElement.getProperty("primitives").insertKey("string", std::string("TestValue"));

		// Add function types
		vabElement.insertKey("operations", basyx::object::make_map());
		vabElement.getProperty("operations").insertKey("supplier", basyx::object::make_function([]() {return true; }));
		vabElement.getProperty("operations").insertKey("complex", basyx::object::make_function(testFuncs::add));
		vabElement.getProperty("operations").insertKey("serializable", basyx::object::make_function(testFuncs::add));

		vabElement.getProperty("operations").insertKey("invalid", true);
		vabElement.getProperty("operations").insertKey("invokable", basyx::object::make_function([]() {return true; }));

		// Add structure types
		vabElement.insertKey("structure", basyx::object::make_map());
		vabElement.getProperty("structure").insertKey("map", basyx::object::make_map());
		vabElement.getProperty("structure").insertKey("set", basyx::object::make_set<bool>());
		vabElement.getProperty("structure").insertKey("list", basyx::object::make_list<int>());

		// Add corner cases
		vabElement.insertKey("special", basyx::object::make_map());
		vabElement.getProperty("special").insertKey("casesensitivity", true);
		vabElement.getProperty("special").insertKey("caseSensitivity", false);
		vabElement.getProperty("special").insertKey("nested", basyx::object::make_map());
		vabElement.getProperty("special").getProperty("nested").insertKey("nested", basyx::object::make_map());
		vabElement.getProperty("special").getProperty("nested").getProperty("nested").insertKey("value", 100);
		vabElement.getProperty("special").insertKey("null", basyx::object::make_null());
		
		return vabElement;
	}

}
}
}