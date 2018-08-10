/*
 * test_jsonprovider.cpp
 *
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#include "regression/support/gtest/gtest.h"

#include "backends/provider/json/JSONProvider.h"
#include "api/IModelProvider.h"

/**
 * IModelProvider stub exposing the function call arguments
 */
class ModelProviderStub: public IModelProvider {
public:
	virtual ~ModelProviderStub() {
	}

	std::string path;
	BRef<BType> value;
	virtual std::string getElementScope(std::string elementPath) override {
		return elementPath;
	}

	/**
	 * Get a sub model property value
	 */
	virtual BRef<BType> getModelPropertyValue(std::string path) override {
		this->path = path;
		return BRef<BType>(new BValue(2));
	}

	/**
	 * Set a sub model property value
	 */
	virtual void setModelPropertyValue(std::string path, BRef<BType> newValue)
			override {
		this->path = path;
		this->value = newValue;
	}

	/**
	 * Create/insert a value in a collection
	 */
	virtual void createValue(std::string path, BRef<BType> addedValue)
			override {
		this->path = path;
		this->value = addedValue;
	}

	/**
	 * Delete a value from a collection
	 */
	virtual void deleteValue(std::string path, BRef<BType> deletedValue)
			override {
		this->path = path;
		this->value = deletedValue;
	}

	/**
	 * Invoke an operation
	 */
	virtual BRef<BType> invokeOperation(std::string path,
			BRef<BObjectCollection> parameter) override {
		this->path = path;
		this->value = parameter;
		return BRef<BType>(new BValue(3));
	}
};

/////////////////////////////////////////////////////////////////
// Tests processBaSysGet method
TEST(TestJSONProvider, testGet) {
	std::unique_ptr<JSONTools> jsonTools(new JSONTools());
	std::unique_ptr<ModelProviderStub> stub(new ModelProviderStub());
	JSONProvider<ModelProviderStub> provider(stub.get(), jsonTools.get());
	std::unique_ptr<char[]> output(new char[1000]);
	size_t size = 0;

	// Perform call and deserialize return value
	std::string path = "TestPath1";
	provider.processBaSysGet(path, output.get(), &size);
	std::string serialized(output.get(), size);
	BRef<BValue> val = jsonTools->deserialize(json::parse(serialized), 0, "");

	// Check for correctness
	ASSERT_EQ(stub->path, path);
	ASSERT_EQ(val->getType(), BASYS_INT);
	ASSERT_EQ(val->getInt(), 2);
}

/////////////////////////////////////////////////////////////////
// Tests processBaSysSet method
TEST(TestJSONProvider, testSet) {
	std::unique_ptr<JSONTools> jsonTools(new JSONTools());
	std::unique_ptr<ModelProviderStub> stub(new ModelProviderStub());
	JSONProvider<ModelProviderStub> provider(stub.get(), jsonTools.get());


	// Perform call
	std::string path = "TestPath2";
	json serialized = jsonTools->serialize(BRef<BValue>(new BValue(4)), 0, "");
	provider.processBaSysSet(path, serialized.dump());

	// Check for correctness
	ASSERT_EQ(stub->path, path);
	ASSERT_EQ(stub->value->getType(), BASYS_INT);
	ASSERT_EQ(static_cast<BRef<BValue>>(stub->value)->getInt(), 4);
}

/////////////////////////////////////////////////////////////////
// Tests processBaSysCreate method
TEST(TestJSONProvider, testCreate) {
	std::unique_ptr<JSONTools> jsonTools(new JSONTools());
	std::unique_ptr<ModelProviderStub> stub(new ModelProviderStub());
	JSONProvider<ModelProviderStub> provider(stub.get(), jsonTools.get());

	// Perform call
	std::string path = "TestPath3";
	json serialized = jsonTools->serialize(BRef<BValue>(new BValue(5)), 0, "");
	provider.processBaSysCreate(path, serialized.dump());

	// Check for correctness
	ASSERT_EQ(stub->path, path);
	ASSERT_EQ(stub->value->getType(), BASYS_INT);
	ASSERT_EQ(static_cast<BRef<BValue>>(stub->value)->getInt(), 5);
}

/////////////////////////////////////////////////////////////////
// Tests processBaSysDelete method
TEST(TestJSONProvider, testDelete) {
	std::unique_ptr<JSONTools> jsonTools(new JSONTools());
	std::unique_ptr<ModelProviderStub> stub(new ModelProviderStub());
	JSONProvider<ModelProviderStub> provider(stub.get(), jsonTools.get());

	// Perform call
	std::string path = "TestPath4";
	json serialized = jsonTools->serialize(BRef<BValue>(new BValue(6)), 0, "");
	provider.processBaSysDelete(path, serialized.dump());

	// Check for correctness
	ASSERT_EQ(stub->path, path);
	ASSERT_EQ(stub->value->getType(), BASYS_INT);
	ASSERT_EQ(static_cast<BRef<BValue>>(stub->value)->getInt(), 6);
}

/////////////////////////////////////////////////////////////////
// Tests processBaSysInvoke method
TEST(TestJSONProvider, testInvoke) {
	std::unique_ptr<JSONTools> jsonTools(new JSONTools());
	std::unique_ptr<ModelProviderStub> stub(new ModelProviderStub());
	JSONProvider<ModelProviderStub> provider(stub.get(), jsonTools.get());
	std::unique_ptr<char[]> output(new char[1000]);
	size_t size = 0;

	// Perform call and deserialize return value
	std::string path = "TestPath5";
	json serialized = jsonTools->serialize(BRef<BValue>(new BValue(7)), 0, "");
	provider.processBaSysInvoke(path, serialized.dump(), output.get(), &size);
	std::string retVal(output.get(), size);
	BRef<BValue> val = jsonTools->deserialize(json::parse(retVal), 0, "");

	// Check call for correctness
	ASSERT_EQ(stub->path, path);
	ASSERT_EQ(stub->value->getType(), BASYS_INT);
	ASSERT_EQ(static_cast<BRef<BValue>>(stub->value)->getInt(), 7);

	// Check return value for correctness
	ASSERT_EQ(val->getType(), BASYS_INT);
	ASSERT_EQ(val->getInt(), 3);

}
