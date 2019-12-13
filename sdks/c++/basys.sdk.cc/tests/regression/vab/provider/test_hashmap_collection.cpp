/*
 * test_cxxhandler.cpp
 *
 *  Created on: 12.03.2019
 *      Author: psota
 */

#include <gtest/gtest.h>

#include <BaSyx/vab/provider/hashmap/VABHashmapProvider.h>

#include <BaSyx/shared/object.h>

#include <memory>
#include <unordered_map>
#include <vector>

class TestBaSyxHashmapProviderCollection : public ::testing::Test {
public:
    basyx::vab::provider::VABModelProvider hashMapProvider;

    virtual void SetUp()
    {
		auto outerMap = basyx::object::make_map();
		outerMap.insertKey("property1", basyx::object::make_map());
		outerMap.getProperty("property1").insertKey("property1.1", 7);
		outerMap.getProperty("property1").insertKey("property1.2", basyx::object::make_list<int>({ 1,2 }));

        hashMapProvider = basyx::vab::provider::VABModelProvider{ std::move(outerMap) };
    }

    virtual void TearDown()
    {
    }
};
