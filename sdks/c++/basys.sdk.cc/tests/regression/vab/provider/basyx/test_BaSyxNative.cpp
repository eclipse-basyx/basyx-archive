#include <gtest/gtest.h>

#include <BaSyx/vab/provider/hashmap/VABHashmapProvider.h>
#include <BaSyx/vab/provider/hashmap/VABHashmapProvider.h>
#include <BaSyx/vab/backend/connector/native/BaSyxConnector.h>
#include <BaSyx/server/TCPServer.h>

#include <vab/stub/elements/SimpleVABElement.h>

#include "snippet/MapRead.h"
#include "snippet/MapCreateDelete.h"
#include "snippet/MapUpdate.h"
#include "snippet/MapInvoke.h"
#include "snippet/TestCollectionProperty.h"

#include <BaSyx/shared/serialization/json.h>

#include <atomic>
#include <memory>
#include <unordered_map>
#include <vector>
#include <thread>

using namespace basyx;

class TestBaSyxNative : public ::testing::Test {
	using TcpServer_t = server::TCPServer<vab::provider::VABModelProvider>;
public:
	static constexpr int port = 7002;
	std::thread server_thread;

	std::unique_ptr<vab::provider::VABModelProvider> provider;
	std::unique_ptr<TcpServer_t> tcpServer;

	std::atomic<bool> done;

	TestBaSyxNative() 
	{
	};

	virtual void SetUp()
	{
		done.store(false);

		provider = util::make_unique<vab::provider::VABModelProvider>(tests::support::make_simple_vab_element());
		tcpServer = util::make_unique<TcpServer_t>(provider.get(), port);

		server_thread = std::thread{ [this]() {
					tcpServer->run();
			}
		};

		// Wait until server started up
		std::this_thread::sleep_for(std::chrono::seconds(3));
	}

	virtual void TearDown()
	{
		tcpServer->Close();
		server_thread.join();
		std::this_thread::sleep_for(std::chrono::seconds(3));
	}
};

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

	virtual basyx::object::error  setModelPropertyValue(const std::string & path, const basyx::object newValue) override
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

TEST_F(TestBaSyxNative, MapRead)
{
	auto connector = util::make_unique<vab::connector::native::NativeConnector>("127.0.0.1", port);
	auto provider = ConnectedModelProvider(connector.get());

	tests::regression::vab::snippet::MapRead::test(&provider);
}

TEST_F(TestBaSyxNative, MapUpdate)
{
	auto connector = util::make_unique<vab::connector::native::NativeConnector>("127.0.0.1", port);
	auto provider = ConnectedModelProvider(connector.get());

	tests::regression::vab::snippet::MapUpdate::test(&provider);
}

TEST_F(TestBaSyxNative, MapCreateDelete)
{
	auto connector = util::make_unique<vab::connector::native::NativeConnector>("127.0.0.1", port);
	auto provider = ConnectedModelProvider(connector.get());

	tests::regression::vab::snippet::MapCreateDelete::test(&provider);
}

TEST_F(TestBaSyxNative, MapInvoke)
{
	auto connector = util::make_unique<vab::connector::native::NativeConnector>("127.0.0.1", port);
	auto provider = ConnectedModelProvider(connector.get());

	tests::regression::vab::snippet::MapInvoke::test(&provider);
}

TEST_F(TestBaSyxNative, TestCollectionProperty)
{
	auto connector = util::make_unique<vab::connector::native::NativeConnector>("127.0.0.1", port);
	auto provider = ConnectedModelProvider(connector.get());

	tests::regression::vab::snippet::TestCollectionProperty::test(&provider);
}

int constexpr TestBaSyxNative::port;