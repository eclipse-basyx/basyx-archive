/*
 * test_cxxhandler.cpp
 *
 *  Created on: 12.03.2019
 *      Author: psota
 */

#include <gtest/gtest.h>

#include "vab/provider/hashmap/VABHashmapProvider.h"

#include "basyx/any.h"

#include <memory>
#include <unordered_map>
#include <vector>

class TestBaSyxHashmapProviderCollection : public ::testing::Test {
public:
    basyx::vab::provider::HashmapProvider hashMapProvider;

    virtual void SetUp()
    {
        basyx::objectCollection_t collection;
        collection.emplace_back(1);
        collection.emplace_back(2);

        basyx::objectMap_t outerMap, propertyMap;
        propertyMap.emplace("property1.1", 7);
        propertyMap.emplace("property1.2", std::move(collection));
        outerMap.emplace("property1", std::move(propertyMap));

        hashMapProvider = basyx::vab::provider::HashmapProvider{ std::move(outerMap) };
    }

    virtual void TearDown()
    {
    }
};
