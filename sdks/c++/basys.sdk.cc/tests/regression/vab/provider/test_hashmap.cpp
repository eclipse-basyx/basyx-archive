/*
 * test_cxxhandler.cpp
 *
 *  Created on: 12.02.2019
 *      Author: psota
 */

#include <gtest/gtest.h>

#include "vab/provider/hashmap/VABHashmapProvider.h"

#include "vab/stub/elements/SimpleVABElement.h"

#include "snippet/MapRead.h"
#include "snippet/MapCreateDelete.h"
#include "snippet/MapInvoke.h"

#include "basyx/any.h"
#include "basyx/serialization/json.h"

#include "basyx/function.h"

#include <memory>
#include <unordered_map>
#include <vector>

using namespace basyx;

class TestBaSyxHashmapProvider : public ::testing::Test {
public:
    vab::provider::HashmapProvider hashMapProvider;

    virtual void SetUp()
    {
        basyx::objectCollection_t collection;
        collection.emplace_back(1);
        collection.emplace_back(2);

        basyx::objectMap_t outerMap, innerMap, propertyMap;

		propertyMap.emplace("Test", 321);
		propertyMap.emplace("test", 123);

		innerMap.emplace("property1.1", 7);
		innerMap.emplace("property1.2", std::move(collection));
		innerMap.emplace("propertyMap", std::move(propertyMap));
			   		 	  
		outerMap.emplace("property1", std::move(innerMap));

        hashMapProvider = vab::provider::HashmapProvider{ std::move(outerMap) };
    }

    virtual void TearDown()
    {
    }
};

TEST_F(TestBaSyxHashmapProvider, GetPropertyValue)
{
	// Get property value
	basyx::any  property1 = hashMapProvider.getModelPropertyValue("property1");

	basyx::any  value1 = hashMapProvider.getModelPropertyValue("property1/property1.1");
	basyx::any  value2 = hashMapProvider.getModelPropertyValue("/property1/property1.1");
	basyx::any  value3 = hashMapProvider.getModelPropertyValue("property1/property1.1/");
	basyx::any  value4 = hashMapProvider.getModelPropertyValue("/property1/property1.1/");

	basyx::any  mapTest1 = hashMapProvider.getModelPropertyValue("property1/propertyMap/Test");
	basyx::any  mapTest2 = hashMapProvider.getModelPropertyValue("property1/propertyMap/test");

	// Check test case results
	ASSERT_TRUE(property1.InstanceOf<basyx::objectMap_t>());
	ASSERT_EQ(property1.Get<basyx::objectMap_t&>().size(), 3);

	ASSERT_TRUE(value1.InstanceOf<int>());
	ASSERT_TRUE(value2.InstanceOf<int>());
	ASSERT_TRUE(value3.InstanceOf<int>());
	ASSERT_TRUE(value4.InstanceOf<int>());
	ASSERT_TRUE(mapTest1.InstanceOf<int>());
	ASSERT_TRUE(mapTest2.InstanceOf<int>());

	ASSERT_EQ(value1.Get<int>(), 7);
	ASSERT_EQ(value2.Get<int>(), 7);
	ASSERT_EQ(value3.Get<int>(), 7);
	ASSERT_EQ(value4.Get<int>(), 7);
	ASSERT_EQ(mapTest1.Get<int>(), 321);
	ASSERT_EQ(mapTest2.Get<int>(), 123);
}

int testFunc(int a, int b)
{
	return a + b;
}

TEST_F(TestBaSyxHashmapProvider, TestInvoke)
{
	hashMapProvider.createValue("function", basyx::make_function(testFunc));

	basyx::objectCollection_t args{ 1,2 };

	auto val = hashMapProvider.invokeOperationImpl("function", args);

	ASSERT_ANY_EQ(val, 3);
}

TEST_F(TestBaSyxHashmapProvider, SetPropertyValue)
{
	// Set and reread property value
	hashMapProvider.setModelPropertyValue("property1/property1.1", 12);
	basyx::any  property1_1 = hashMapProvider.getModelPropertyValue("property1/property1.1");

	// Check test case results
	ASSERT_TRUE(property1_1.InstanceOf<int>());
	ASSERT_EQ(property1_1.Get<int>(), 12);

	// Change value back
	hashMapProvider.setModelPropertyValue("property1/property1.1", 7);
	basyx::any  property1_1b = hashMapProvider.getModelPropertyValue("property1/property1.1");

	// Check test case results
	ASSERT_TRUE(property1_1b.InstanceOf<int>());
	ASSERT_EQ(property1_1b.Get<int>(), 7);
}

TEST_F(TestBaSyxHashmapProvider, CreateDelete)
{
    basyx::any  property1 = hashMapProvider.getModelPropertyValue("property1");
    basyx::any  property1_1 = hashMapProvider.getModelPropertyValue("property1/property1.1");

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
    basyx::any  property2 = hashMapProvider.getModelPropertyValue("property2");
    // - Check test case results
    ASSERT_TRUE(property2.InstanceOf<int>());
    ASSERT_EQ(property2.Get<int>(), 21);

    // Read values back
    basyx::any  property1_4 = hashMapProvider.getModelPropertyValue("property1/property1.4");
    // - Check test case results
    ASSERT_TRUE(property1_4.InstanceOf<int>());
    ASSERT_EQ(property1_4.Get<int>(), 22);

    // Read values back
    basyx::any  property1_2 = hashMapProvider.getModelPropertyValue("property1/property1.2");
    // - Check test case results
    ASSERT_TRUE(property1_2.InstanceOf<basyx::objectCollection_t>());
    ASSERT_EQ(property1_2.Get<basyx::objectCollection_t&>().size(), 3);

    // Delete properties
    hashMapProvider.deleteValue("property2");
    hashMapProvider.deleteValue("property1/property1.4");
//   	hashMapProvider.deleteValue("property1/property1.2", 23);

    // Read values back
    //basyx::any  property2_del = hashMapProvider.getModelPropertyValue("property2");	// - Check test case results
    //assertEquals(null, value6);

    // Read values back
    //basyx::any  property1_4del = hashMapProvider.getModelPropertyValue("property1/property1.4");	// - Check test case results
    // - Check test case results
    //assertEquals(null, value7);

    // Read values back
    basyx::any  property1_2b = hashMapProvider.getModelPropertyValue("property1/property1.2");
    // - Check test case results
    ASSERT_TRUE(property1_2b.InstanceOf<basyx::objectCollection_t>());
	ASSERT_EQ(property1_2b.Get<basyx::objectCollection_t&>().size(), 3);
}

TEST_F(TestBaSyxHashmapProvider, MapRead)
{
	vab::provider::HashmapProvider hashMapProvider{ tests::support::make_simple_vab_element() };

	tests::regression::vab::snippet::MapRead::test(&hashMapProvider);
}

TEST_F(TestBaSyxHashmapProvider, MapCreateDelete)
{
	vab::provider::HashmapProvider hashMapProvider{ tests::support::make_simple_vab_element() };

	tests::regression::vab::snippet::MapCreateDelete::test(&hashMapProvider);
}

TEST_F(TestBaSyxHashmapProvider, MapInvoke)
{
	vab::provider::HashmapProvider hashMapProvider{ tests::support::make_simple_vab_element() };

	tests::regression::vab::snippet::MapInvoke::test(&hashMapProvider);
}