/*
 * test_cxxhandler.cpp
 *
 *  Created on: 12.09.2019
 *      Author: psota
 */

/////////////////////////////////////////////////////////////////
// Includes
// GTest
#include "gtest/gtest.h"

#include <backends/provider/vab/VABHashmapProvider.h>

#include <util/any.h>

#include <memory>
#include <unordered_map>
#include <vector>

class TestBaSyxHashmapProvider : public ::testing::Test {
public:
    basyx::provider::HashmapProvider hashMapProvider;

    virtual void SetUp()
    {
        basyx::objectCollection_t collection;
        collection.emplace_back(1);
        collection.emplace_back(2);

        basyx::objectMap_t outerMap, propertyMap;
        propertyMap.emplace("property1.1", 7);
        propertyMap.emplace("property1.2", std::move(collection));
        outerMap.emplace("property1", std::move(propertyMap));

        hashMapProvider = basyx::provider::HashmapProvider { std::move(outerMap) };
    }

    virtual void TearDown()
    {
    }
};

TEST_F(TestBaSyxHashmapProvider, GetPropertyValue)
{
	// Get property value
	basyx::any & property1 = hashMapProvider.getModelPropertyValue2("property1");
	basyx::any & property1_1 = hashMapProvider.getModelPropertyValue2("property1/property1.1");

	// Check test case results
	ASSERT_TRUE(property1.InstanceOf<basyx::objectMap_t>());
	ASSERT_EQ(property1.Get<basyx::objectMap_t&>().size(), 2);

	ASSERT_TRUE(property1_1.InstanceOf<int>());
	ASSERT_EQ(property1_1.Get<int>(), 7);
}

TEST_F(TestBaSyxHashmapProvider, SetPropertyValue)
{
	// Set and reread property value
	hashMapProvider.setModelPropertyValue("property1/property1.1.", 12);
	basyx::any & property1_1 = hashMapProvider.getModelPropertyValue2("property1/property1.1");

	// Check test case results
	ASSERT_TRUE(property1_1.InstanceOf<int>());
	ASSERT_EQ(property1_1.Get<int>(), 12);

	// Change value back
	hashMapProvider.setModelPropertyValue("property1/property1.1.", 7);
	basyx::any & property1_1b = hashMapProvider.getModelPropertyValue2("property1/property1.1");

	// Check test case results
	ASSERT_TRUE(property1_1b.InstanceOf<int>());
	ASSERT_EQ(property1_1b.Get<int>(), 7);
}

TEST_F(TestBaSyxHashmapProvider, CreateDelete)
{
    basyx::any & property1 = hashMapProvider.getModelPropertyValue2("property1");
    basyx::any & property1_1 = hashMapProvider.getModelPropertyValue2("property1/property1.1");

    ASSERT_TRUE(property1.InstanceOf<basyx::objectMap_t>());
    ASSERT_TRUE(property1_1.InstanceOf<int>());
    ASSERT_EQ(property1_1.Get<int>(), 7);

    // - Create property directly in VAB element
    hashMapProvider.createValue("property2", 21);
    // - Create property in contained hashmap
    hashMapProvider.createValue("property1/property1.4", 22);
    // - Create property in collection in contained hashmap
    hashMapProvider.createValue("property1/property1.2", 23);

    // Read values back
    basyx::any & property2 = hashMapProvider.getModelPropertyValue2("property2");
    // - Check test case results
    ASSERT_TRUE(property2.InstanceOf<int>());
    ASSERT_EQ(property2.Get<int>(), 21);

    // Read values back
    basyx::any & property1_4 = hashMapProvider.getModelPropertyValue2("property1/property1.4");
    // - Check test case results
    ASSERT_TRUE(property1_4.InstanceOf<int>());
    ASSERT_EQ(property1_4.Get<int>(), 22);

    // Read values back
    basyx::any & property1_2 = hashMapProvider.getModelPropertyValue2("property1/property1.2");
    // - Check test case results
    ASSERT_TRUE(property1_2.InstanceOf<basyx::objectCollection_t>());
    ASSERT_EQ(property1_2.Get<basyx::objectCollection_t&>().size(), 3);

    // Delete properties
    hashMapProvider.deleteValue("property2");
    hashMapProvider.deleteValue("property1/property1.4");
    hashMapProvider.deleteValue("property1/property1.2", 23);

    // Read values back
    //basyx::any & property2_del = hashMapProvider.getModelPropertyValue2("property2");	// - Check test case results
    //assertEquals(null, value6);

    // Read values back
    //basyx::any & property1_4del = hashMapProvider.getModelPropertyValue2("property1/property1.4");	// - Check test case results
    // - Check test case results
    //assertEquals(null, value7);

    // Read values back
    basyx::any & property1_2b = hashMapProvider.getModelPropertyValue2("property1/property1.2");
    // - Check test case results
    ASSERT_TRUE(property1_2b.InstanceOf<basyx::objectCollection_t>());
    ASSERT_EQ(property1_2b.Get<basyx::objectCollection_t&>().size(), 2);

    ASSERT_EQ(2, 2);
}
