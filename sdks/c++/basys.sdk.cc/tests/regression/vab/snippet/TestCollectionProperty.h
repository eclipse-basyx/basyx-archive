#ifndef _TESTCOLLECTIONPROPERTY_H
#define _TESTCOLLECTIONPROPERTY_H

#include <gtest/gtest.h>

#include <BaSyx/shared/types.h>

#include <BaSyx/vab/core/IModelProvider.h>

#include "macros.h"


namespace basyx {
namespace tests {
namespace regression {
namespace vab {
namespace snippet {

class TestCollectionProperty {
public:
	static void test(basyx::vab::core::IModelProvider * modelProvider)
	{
		testRead(modelProvider);
		testCreateDelete(modelProvider);
	}
private:
	static void testRead(basyx::vab::core::IModelProvider * modelProvider)
	{
		// Adding elements to tested list
		modelProvider->createValue("/structure/list/", 5);
		modelProvider->createValue("/structure/list/", 12);

		// Test reading whole lists
    	auto collection = modelProvider->getModelPropertyValue("/structure/list/");
    	ASSERT_TRUE(collection.InstanceOf<basyx::object::list_t<int>>());
    	ASSERT_EQ(collection.Get<basyx::object::list_t<int>&>().size(), 2);

		// Test invalid list access
		auto invalid = modelProvider->getModelPropertyValue("/structure/list/invalid");
		ASSERT_TRUE(invalid.IsNull());

		// Delete remaining entries
		modelProvider->deleteValue("/structure/list",  5);
		modelProvider->deleteValue("/structure/list", 12);

		auto collection2 = modelProvider->getModelPropertyValue("/structure/list/");
		ASSERT_TRUE(collection2.empty());
	}


	static void testCreateDelete(basyx::vab::core::IModelProvider * modelProvider)
	{
		// Create elements in List (no key provided)
		auto insertResult = modelProvider->createValue("/structure/list/", 56);
		ASSERT_EQ(insertResult, basyx::object::error::None);
		auto toTest = modelProvider->getModelPropertyValue("/structure/list/");
		ASSERT_TRUE(toTest.InstanceOf<basyx::object::list_t<int>>());
		auto & objectCollection = toTest.Get<basyx::object::list_t<int>&>();
		ASSERT_EQ(objectCollection.back(), 56);
		
		// Delete at List
		// by object
		modelProvider->deleteValue("/structure/list/", 56);
		toTest = modelProvider->getModelPropertyValue("/structure/list/");
		ASSERT_EQ(toTest.GetObjectType(), basyx::type::objectType::List);
		ASSERT_TRUE(toTest.empty());

		// Create a list element
		modelProvider->createValue("listInRoot", basyx::object::list_t<int>{ 1, 1, 2, 3, 5 });

		// Test whole list
		auto listInRoot = modelProvider->getModelPropertyValue("listInRoot");
		ASSERT_TRUE(listInRoot.InstanceOf<basyx::object::list_t<int>>());
		ASSERT_EQ(listInRoot.Get<basyx::object::list_t<int>&>().size(), 5);

		ASSERT_EQ(listInRoot.Get<basyx::object::list_t<int>&>()[0], 1);
		ASSERT_EQ(listInRoot.Get<basyx::object::list_t<int>&>()[1], 1);
		ASSERT_EQ(listInRoot.Get<basyx::object::list_t<int>&>()[2], 2);
		ASSERT_EQ(listInRoot.Get<basyx::object::list_t<int>&>()[3], 3);
		ASSERT_EQ(listInRoot.Get<basyx::object::list_t<int>&>()[4], 5);

		// Delete whole list
		modelProvider->deleteValue("listInRoot");
		toTest = modelProvider->getModelPropertyValue("listInRoot");
		ASSERT_TRUE(toTest.IsNull());

		return;
	}

};

}
}
}
}
}

#endif /* _TESTCOLLECTIONPROPERTY_H */
