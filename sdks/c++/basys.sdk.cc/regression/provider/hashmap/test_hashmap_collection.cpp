/*
 * test_cxxhandler.cpp
 *
 *  Created on: 12.03.2019
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

class TestBaSyxHashmapProviderCollection : public ::testing::Test {
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
