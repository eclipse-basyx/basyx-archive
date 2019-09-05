#ifndef _MAPREAD_H
#define _MAPREAD_H

#include <gtest/gtest.h>

#include "basyx/types.h"

#include "vab/core/IModelProvider.h"

#define ASSERT_ANY_EQ(a,t) 	ASSERT_TRUE(a.template InstanceOf<decltype(t)>());		ASSERT_EQ(t, a.template Get<decltype(t)&>());



namespace basyx {
namespace tests {
namespace regression {
namespace vab {
namespace snippet {


class MapRead {
public:

	//template<typename T>
	//static void ASSERT_ANY_EQ(basyx::any & a, const T & val)
	//{
	//	ASSERT_TRUE(a.InstanceOf<T>());
	//	ASSERT_EQ(val, a.Get<T&>());
	//}


    static void test(basyx::vab::core::IModelProvider * modelProvider) {
		// Test path access
		auto slashA = modelProvider->getModelPropertyValue("/primitives/integer");
		auto slashB = modelProvider->getModelPropertyValue("primitives/integer/");
		auto slashC = modelProvider->getModelPropertyValue("/primitives/integer/");
		auto slashD = modelProvider->getModelPropertyValue("/primitives/integer/");

		ASSERT_ANY_EQ(slashA, 123);
		ASSERT_ANY_EQ(slashB, 123);
		ASSERT_ANY_EQ(slashC, 123);
		ASSERT_ANY_EQ(slashD, 123);

		// Test reading different data types
		auto map = modelProvider->getModelPropertyValue("primitives");
		auto doubleValue = modelProvider->getModelPropertyValue("primitives/double");
		auto string = modelProvider->getModelPropertyValue("primitives/string");

		ASSERT_TRUE(map.InstanceOf<objectMap_t>());
		ASSERT_TRUE(doubleValue.InstanceOf<double>());
		ASSERT_TRUE(string.InstanceOf<std::string>());


		ASSERT_EQ(3, map.Get<objectMap_t>().size());

		ASSERT_EQ(3.14, doubleValue.Get<double>());
//		ASSERT_EQ("TestValue", string);

		// Test case sensitivity
		auto caseSensitiveA = modelProvider->getModelPropertyValue("special/casesensitivity");
		auto caseSensitiveB = modelProvider->getModelPropertyValue("special/caseSensitivity");

		ASSERT_TRUE(caseSensitiveA.InstanceOf<bool>());
		ASSERT_TRUE(caseSensitiveB.InstanceOf<bool>());
		ASSERT_EQ(true, caseSensitiveA.Get<bool>());
		ASSERT_EQ(false, caseSensitiveB.Get<bool>());

		// Test reading null value
		auto nullValue = modelProvider->getModelPropertyValue("special/null");
		ASSERT_TRUE(nullValue.IsNull());

		//// Test reading serializable functions
		//auto serializableFunction = modelProvider->getModelPropertyValue("operations/serializable");
		//Function<auto[], auto> testFunction = (Function<auto[], auto>) serializableFunction;
		//ASSERT_EQ(3, testFunction.apply(new auto[] { 1, 2 }));

		// Non-existing parent element
		ASSERT_TRUE(modelProvider->getModelPropertyValue("unknown/x").IsNull());

		// Non-existing target element
		ASSERT_TRUE(modelProvider->getModelPropertyValue("primitives/unkown").IsNull());
		ASSERT_TRUE(modelProvider->getModelPropertyValue("unkown").IsNull());

		// Nested access
		ASSERT_ANY_EQ(modelProvider->getModelPropertyValue("special/nested/nested/value"), 100);

		// Empty path
		auto rootValueA = modelProvider->getModelPropertyValue("");
		auto rootValueB = modelProvider->getModelPropertyValue("/");

		ASSERT_TRUE(rootValueA.InstanceOf<objectMap_t>());
		ASSERT_TRUE(rootValueB.InstanceOf<objectMap_t>());

		ASSERT_EQ(4, rootValueA.Get<objectMap_t>().size());
		ASSERT_EQ(4, rootValueB.Get<objectMap_t>().size());
	}
};

}
}
}
}
}


#endif /* _MAPREAD_H */
