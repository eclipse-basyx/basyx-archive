#ifndef _MAP_UPDATE_H
#define _MAP_UPDATE_H

#include <gtest/gtest.h>

#include <BaSyx/shared/types.h>

#include <BaSyx/vab/core/IModelProvider.h>


namespace basyx {
namespace tests {
namespace regression {
namespace vab {
namespace snippet {


class MapUpdate{
public:
	static void test(basyx::vab::core::IModelProvider * modelProvider)
	{
		testUpdate(modelProvider);
		testWholeMap(modelProvider);
	}

    static void testUpdate(basyx::vab::core::IModelProvider * modelProvider) 
	{
		// Set primitives
		modelProvider->setModelPropertyValue("primitives/integer", 12);
		modelProvider->setModelPropertyValue("primitives/double", 1.2);
		modelProvider->setModelPropertyValue("primitives/string", "updated");

		// Read back
		auto integerValue = modelProvider->getModelPropertyValue("primitives/integer");
		auto doubleValue = modelProvider->getModelPropertyValue("primitives/double");
		auto stringValue = modelProvider->getModelPropertyValue("primitives/string");

		// Test
		ASSERT_TRUE(integerValue.InstanceOf<int>());
		ASSERT_EQ(integerValue.Get<int>(), 12);
		ASSERT_TRUE(doubleValue.InstanceOf<double>());
		ASSERT_EQ(doubleValue.Get<double>(), 1.2);
		ASSERT_TRUE(stringValue.InstanceOf<std::string>());
		ASSERT_EQ(stringValue.Get<std::string&>(), "updated");

		// Revert
		modelProvider->setModelPropertyValue("primitives/integer", 123);
		modelProvider->setModelPropertyValue("primitives/double", 3.14);
		modelProvider->setModelPropertyValue("primitives/string", "TestValue");

		auto createError = modelProvider->createValue("unkown/newElement", 5);
		ASSERT_EQ(createError, basyx::object::error::PropertyNotFound);
	
		auto createError2 = modelProvider->createValue("newElement", 10);
		ASSERT_EQ(createError, basyx::object::error::PropertyNotFound);

		// Test updating an existing null-element
		modelProvider->setModelPropertyValue("special/null", true);
		auto nullUpdate = modelProvider->getModelPropertyValue("special/null");
		ASSERT_TRUE(nullUpdate.InstanceOf<bool>());
		ASSERT_EQ(nullUpdate.Get<bool>(), true);
	}

	static void testWholeMap(basyx::vab::core::IModelProvider * modelProvider)
	{
		// Push whole map via ""-Path
		// - create object
		auto newMap = basyx::object::make_map();
		newMap.insertKey("testKey", "testValue");
		auto error = modelProvider->setModelPropertyValue("", newMap);
		ASSERT_EQ(error, basyx::object::error::PropertyNotFound);
		ASSERT_TRUE(modelProvider->getModelPropertyValue("testKey").IsError());
		ASSERT_TRUE(modelProvider->getModelPropertyValue("primitives/integer").IsError());
	};
};

}
}
}
}
}


#endif /* _MAPREAD_H */
