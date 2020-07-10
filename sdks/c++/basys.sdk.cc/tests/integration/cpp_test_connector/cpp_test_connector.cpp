#include <string>
#include <iostream>
#include <unordered_map>
#include <stdio.h>

#include <BaSyx/log/log.h>

#include <BaSyx/util/function_traits.h>
#include <BaSyx/util/make_function.h>

#include <BaSyx/vab/backend/connector/native/BaSyxConnector.h>
#include <BaSyx/vab/provider/hashmap/VABHashmapProvider.h>
#include <BaSyx/vab/provider/native/frame/BaSyxNativeFrameProcessor.h>

#include <BaSyx/shared/serialization/json.h>

#include <chrono>

#include <tuple>
#include <iostream>
#include <array>
#include <utility>

#include <BaSyx/shared/object.h>

#include <BaSyx/vab/provider/VABModelProvider.h>

#include "../tests/regression/vab/snippet/MapRead.h"
#include "../tests/regression/vab/snippet/MapUpdate.h"
#include "../tests/regression/vab/snippet/MapCreateDelete.h"
#include "../tests/regression/vab/snippet/MapInvoke.h"
#include "../tests/regression/vab/snippet/TestCollectionProperty.h"


using namespace basyx;

int test(int a)
{
	return a + 3;
}

int test2(int a, int b)
{
	return a + b;
}

class ConnectedModelProvider : public vab::core::IModelProvider
{
public:
	using Connector = vab::connector::IBaSyxConnector;
	Connector * connector;
public:
	ConnectedModelProvider(Connector * connector) : connector(connector)
	{};

	// Inherited via IModelProvider
	virtual basyx::object getModelPropertyValue(const std::string & path) override
	{
		return connector->basysGet(path);
	}

	virtual basyx::object::error setModelPropertyValue(const std::string & path, const basyx::object newValue) override
	{
		auto obj = connector->basysSet(path, newValue);
		return obj.getError();
	}

	virtual basyx::object::error createValue(const std::string & path, const basyx::object addedValue) override
	{
		auto obj = connector->basysCreate(path, addedValue);
		return obj.getError();
	}

	virtual basyx::object::error deleteValue(const std::string & path, basyx::object deletedValue) override
	{
		auto obj = connector->basysDelete(path, deletedValue);
		return obj.getError();
	}

	virtual basyx::object::error deleteValue(const std::string & path) override
	{
		auto obj = connector->basysDelete(path);
		return obj.getError();
	}
	virtual basyx::object invokeOperation(const std::string & path, basyx::object parameter) override
	{
		return connector->basysInvoke(path, parameter);
	}
};

#define HOST "127.0.0.1"

int test() 
{
	//std::string host;
	//if (argc > 1)
	//	host = argv[1];
	//else
	//	host = HOST;

	std::string host = HOST;

   	auto connector = util::make_unique<vab::connector::native::NativeConnector>(host, 7001);

	auto provider = ConnectedModelProvider(connector.get());

	basyx::tests::regression::vab::snippet::MapRead::test(&provider);
	basyx::tests::regression::vab::snippet::MapUpdate::test(&provider);
	basyx::tests::regression::vab::snippet::MapCreateDelete::test(&provider);
	basyx::tests::regression::vab::snippet::TestCollectionProperty::test(&provider);
	basyx::tests::regression::vab::snippet::MapInvoke::test(&provider);

	//auto get = connector->basysGet("primitives/integer");

	//connector->basysSet("primitives/integer", 20);

	//auto get2 = connector->basysGet("primitives/integer");

	//auto inv = connector->basysInvoke("operations/complex", basyx::object::object_list_t{ 1,2 });

	return 0;
};

TEST(Test_Case, Test)
{
	test();
};

int main(int argc, char **argv) {
	// Init gtest framework
	::testing::InitGoogleTest(&argc, argv);

	// Run all tests
	return RUN_ALL_TESTS();
}