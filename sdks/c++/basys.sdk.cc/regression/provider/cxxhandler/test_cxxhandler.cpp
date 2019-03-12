/*
 * test_cxxhandler.cpp
 *
 *  Created on: 17.08.2018
 *      Author: schnicke
 */

/////////////////////////////////////////////////////////////////
// Includes
// GTest
#include "gtest/gtest.h"
#include "gtest/gtest-all.cc"

#include "ref/BRef.h"
#include "types/BValue.h"
#include "parameter/BParameter.h"
#include "types/BObjectCollection.h"
#include "types/BObjectMap.h"
#include "types/BArray.h"

#include <unordered_map>
#include <vector>
#include <memory>

#include "backends/provider/cxxhandler/CXXHandler.h"

class TestClass {
public:

	int intProp;
	std::unordered_map<std::string, int> map;
	std::vector<int> collection;

	int increment(int param) {
		return param + 1;
	}
};

/**
 * Helper function to create a BObjectCollection from a std::vector<int>
 */
BRef<BObjectCollection> createCollection(std::vector<int> collection) {
	BRef<BObjectCollection> ret = BRef<BObjectCollection>(
			new BObjectCollection());

	std::for_each(collection.begin(), collection.end(),
			[&](int i) {ret->add(BRef<BValue>(new BValue(i)));});
	return ret;
}

/**
 * Helper function to create a BObjectMap from a std::unordered_map<std::string, int>
 */

BRef<BObjectMap> createMap(std::unordered_map<std::string, int> map) {
	BRef<BObjectMap> ret = BRef<BObjectMap>(new BObjectMap());
	for (auto val : map) {
		ret->elements()->insert(
				std::pair<std::string, BRef<BValue>>(val.first,
						BRef<BValue>(new BValue(val.second))));
	}
	return ret;
}

/**
 * Tests setting and getting a property containing a primitive value
 */
TEST(CXXHandlerTest, simplePropTest) {
	std::unique_ptr<CXXHandler> handler(new CXXHandler());

	TestClass cls = TestClass();
	std::string path = "intProp";
	handler->addProperty(path,
			[&]() {return BRef<BValue>(new BValue(cls.intProp));},
			[&](BRef<BType> val) {cls.intProp = static_cast<BRef<BValue>>(val)->getInt();},
			0, 0);

	// Check getting a property
	cls.intProp = 6;

	BRef<BType> ret = handler->getModelPropertyValue(path);
	ASSERT_EQ(ret->getType(), BASYS_INT);
	ASSERT_EQ(static_cast<BRef<BValue>>(ret)->getInt(), cls.intProp);

	// Check setting a property
	handler->setModelPropertyValue(path, BRef<BValue>(new BValue(10)));
	ASSERT_EQ(cls.intProp, 10);
}

TEST(CXXHandlerTest, collectionPropTest) {
	std::unique_ptr<CXXHandler> handler(new CXXHandler());

	TestClass cls = TestClass();
	std::string path = "collectionPath";
	int size = 5;
	for (int i = 0; i < size; i++) {
		cls.collection.push_back(i);
	}
	handler->addProperty(path, [&]() {return createCollection(cls.collection);},
			nullptr,
			[&](BRef<BValue> val) {
				if (val->getType() == BASYS_INT) {
					cls.collection.push_back(static_cast<BRef<BValue>>(val)->getInt());
				}
			},
			[&](BRef<BValue> val) {
				if (val->getType() == BASYS_INT) {
					std::vector<int>::iterator position = std::find(cls.collection.begin(), cls.collection.end(), static_cast<BRef<BValue>>(val)->getInt());
					if (position != cls.collection.end()) {
						cls.collection.erase(position);
					}
				}
			});

	// Assert that the collection has the expected values as initialized
	BRef<BType> ret = handler->getModelPropertyValue(path);
	ASSERT_EQ(ret->getType(), BASYS_COLLECTION);
	BRef<BObjectCollection> col = static_cast<BRef<BValue>>(ret);
	ASSERT_EQ(col->size(), size);

	auto it = col->elements()->begin();
	int i = 0;
	while (it != col->elements()->end()) {
		ASSERT_EQ((*it)->getType(), BASYS_INT);
		ASSERT_EQ(static_cast<BRef<BValue>>(*it)->getInt(), i);
		i++;
		it++;
	}

	// Assert that the element is removed from the collection
	handler->deleteValue(path, 4);

	ret = handler->getModelPropertyValue(path);
	ASSERT_EQ(ret->getType(), BASYS_COLLECTION);
	col = static_cast<BRef<BValue>>(ret);
	ASSERT_EQ(col->size(), size - 1);

	it = col->elements()->begin();
	i = 0;
	while (it != col->elements()->end()) {
		ASSERT_EQ((*it)->getType(), BASYS_INT);
		ASSERT_EQ(static_cast<BRef<BValue>>(*it)->getInt(), i);
		i++;
		it++;
	}

	// Assert that the element is added to the collection
	handler->createValue(path, 4);

	ret = handler->getModelPropertyValue(path);
	ASSERT_EQ(ret->getType(), BASYS_COLLECTION);
	col = static_cast<BRef<BValue>>(ret);
	ASSERT_EQ(col->size(), size);

	it = col->elements()->begin();
	i = 0;
	while (it != col->elements()->end()) {
		ASSERT_EQ((*it)->getType(), BASYS_INT);
		ASSERT_EQ(static_cast<BRef<BValue>>(*it)->getInt(), i);
		i++;
		it++;
	}
}

TEST(CXXHandlerTest, mapPropTest) {
	std::unique_ptr<CXXHandler> handler(new CXXHandler());

	TestClass cls = TestClass();
	std::string path = "mapPath";
	cls.map["a"] = 0;
	cls.map["b"] = 1;
	cls.map["c"] = 2;

	handler->addProperty(path, [&]() {return createMap(cls.map);}, nullptr,
			[&](BRef<BType> val) {
				if (val->getType() == BASYS_OBJECTARRAY) {
					BRef<BArray> arr = static_cast<BRef<BArray>>(val);
					std::string key = static_cast<BRef<BString>>(arr->getMembersObject()[0])->getString();
					int val = static_cast<BRef<BValue>>(arr->getMembersObject()[1])->getInt();
					cls.map[key] = val;
				}
			}, [&](BRef<BType> val) {
				if(val->getType() == BASYS_STRING) {
					cls.map.erase(static_cast<BRef<BString>>(val)->getString());
				}
			});

	// Assert that the map has the expected values as initialized
	BRef<BType> map = handler->getModelPropertyValue(path);
	ASSERT_EQ(map->getType(), BASYS_MAP);
	auto mapElements = static_cast<BRef<BObjectMap>>(map)->elements();
	ASSERT_EQ(static_cast<BRef<BValue>>((*mapElements)["a"])->getInt(), 0);
	ASSERT_EQ(static_cast<BRef<BValue>>((*mapElements)["b"])->getInt(), 1);
	ASSERT_EQ(static_cast<BRef<BValue>>((*mapElements)["c"])->getInt(), 2);

	// Assert that the element is removed from the map
	handler->deleteValue(path, BRef<BString>(new BString("a")));
	map = handler->getModelPropertyValue(path);
	ASSERT_EQ(map->getType(), BASYS_MAP);
	mapElements = static_cast<BRef<BObjectMap>>(map)->elements();
	ASSERT_EQ(mapElements->find("a"), mapElements->end());

	// Assert that the element is added to the map
	BRef<BType> values[] = {BRef<BString>("x"), BRef<BValue>(100)};
	BRef<BArray> arr = BRef<BArray>(new BArray(values, 2));
	handler->createValue(path, arr);

	map = handler->getModelPropertyValue(path);
	ASSERT_EQ(map->getType(), BASYS_MAP);
	mapElements = static_cast<BRef<BObjectMap>>(map)->elements();

	ASSERT_NE(mapElements->find("x"), mapElements->end());
	ASSERT_EQ(static_cast<BRef<BValue>>((*mapElements)["x"])->getInt(), 100);
}

TEST(CXXHandlerTest, invokeTest) {
	std::unique_ptr<CXXHandler> handler(new CXXHandler());

	TestClass cls = TestClass();
	std::string path = "funcPath";

	handler->addFunction(path, [&](BRef<BType> param) -> BRef<BType> {
		if (param->getType() != BASYS_INT) {
			return BRef<BNullObject>(new BNullObject);
		}

		int val = static_cast<BRef<BValue>>(param)->getInt();
		return BRef<BValue>(new BValue(cls.increment(val)));
	});

	// Check if the function return value is as expected
	BRef<BType> ret = handler->invokeOperation(path,
			BRef<BValue>(new BValue(10)));
	ASSERT_EQ(ret->getType(), BASYS_INT);
	ASSERT_EQ(static_cast<BRef<BValue>>(ret)->getInt(), 11);
}

/* ************************************************
 * Run test suite
 * ************************************************/
int main(int argc, char **argv) {
	// Init gtest framework
	::testing::InitGoogleTest(&argc, argv);

	// Run all tests
	return RUN_ALL_TESTS();
}
