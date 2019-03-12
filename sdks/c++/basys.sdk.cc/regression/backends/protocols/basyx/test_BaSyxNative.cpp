/*
 * test_BaSyxNative.cpp
 * Tests the whole loop of BaSyxNativeConnector -> BaSyxNativeProvider
 *  Created on: 15.08.2018
 *      Author: schnicke
 */

#include <atomic>
#include <memory>

//#include "BaSyxThread.h"

#include "abstraction/Thread.h"

#include "gtest/gtest.h"
//#include "gtest/gtest-all.cc"
#include "json/JSONTools.h"

#include "backends/protocols/connector/basyx/BaSyxNativeConnector.h"
#include "backends/protocols/provider/basyx/BaSyxTCPServer.h"

#include "regression/backends/protocols/provider/basyx/frame/MockupModelProvider.h"



std::atomic_flag running = ATOMIC_FLAG_INIT;

void serverLoop(std::unique_ptr<BaSyxTCPServer<MockupModelProvider>> & tcpServer)
{
	while (running.test_and_set(std::memory_order_acquire))
	{
		tcpServer->update();
	}
};

/**
 * Creates the test environment needed in the following tests:
 * - Provides BaSyxTCPServer
 * - Provides BaSyxNativeConnector
 */
class BaSyxNativeTest: public ::testing::Test {
protected:
	const int port = 6112;

	std::unique_ptr<JSONTools> jTools;
	std::unique_ptr<BaSyxNativeConnector> connector;
	std::unique_ptr<BaSyxTCPServer<MockupModelProvider>> tcpServer;
	std::unique_ptr<MockupModelProvider> mockup;
	basyx::thread serverThread{};

	virtual void SetUp() {
		running.test_and_set(std::memory_order_acquire);

		jTools = util::make_unique<JSONTools>();

		mockup = util::make_unique<MockupModelProvider>();
		tcpServer = util::make_unique<BaSyxTCPServer<MockupModelProvider>>(mockup.get(), port, jTools.get());

		serverThread = basyx::thread{serverLoop, std::ref(tcpServer)};

		connector = util::make_unique<BaSyxNativeConnector>("127.0.0.1", port, jTools.get());
	}

	virtual void TearDown() {
		running.clear(std::memory_order_release);

		tcpServer->Close();

		serverThread.join();
	}

};

TEST_F(BaSyxNativeTest, getTest) {
	std::string path = "TestPath";
	BRef<BValue> val = static_cast<BRef<BValue>>(connector->basysGet(path));

	// Check if correct call occured
	ASSERT_EQ(mockup->called, MockupModelProvider::CalledFunction::GET);
	ASSERT_EQ(mockup->path, path);
	
	// Check return value
	ASSERT_EQ(val->getType(), BASYS_INT);
	ASSERT_EQ(val->getInt(), 2);
}

TEST_F(BaSyxNativeTest, setTest) {
	std::string path = "TestPath";
	BRef<BValue> val = BRef<BValue>(new BValue(10));
	connector->basysSet(path, val);
	
	while (mockup->called != MockupModelProvider::CalledFunction::SET)
		;

	// Check if correct call occured
	ASSERT_EQ(mockup->called, MockupModelProvider::CalledFunction::SET);
	ASSERT_EQ(mockup->path, path);
}

TEST_F(BaSyxNativeTest, createTest) {
	std::string path = "TestPath";
	BRef<BValue> val = BRef<BValue>(new BValue(10));
	connector->basysCreate(path, val);
	
	// Check if correct call occured
	ASSERT_EQ(mockup->called, MockupModelProvider::CalledFunction::CREATE);
	ASSERT_EQ(mockup->path, path);
	ASSERT_EQ(mockup->val->getType(), BASYS_INT);
	ASSERT_EQ(static_cast<BRef<BValue>>(mockup->val)->getInt(), 10);
}

TEST_F(BaSyxNativeTest, deleteSimpleTest) {
	std::string path = "TestPath";
	connector->basysDelete(path);
	
	// Check if correct call occured
	ASSERT_EQ(mockup->called, MockupModelProvider::CalledFunction::DELETE_SIMPLE);
	ASSERT_EQ(mockup->path, path);
}

TEST_F(BaSyxNativeTest, deleteComplexTest) {
	std::string path = "TestPath";
	BRef<BValue> val = BRef<BValue>(new BValue(10));
	connector->basysDelete(path, val);
	
	// Check if correct call occured
	ASSERT_EQ(mockup->called, MockupModelProvider::CalledFunction::DELETE_COMPLEX);
	ASSERT_EQ(mockup->path, path);
	ASSERT_EQ(mockup->val->getType(), BASYS_INT);
	ASSERT_EQ(static_cast<BRef<BValue>>(mockup->val)->getInt(), 10);
}

TEST_F(BaSyxNativeTest, invokeTest) {
	std::string path = "TestPath";
	BRef<BValue> val = BRef<BValue>(new BValue(10));
	BRef<BValue> retVal = static_cast<BRef<BValue>>(connector->basysInvoke(path, val));
	
	// Check if correct call occured
	ASSERT_EQ(mockup->called, MockupModelProvider::CalledFunction::INVOKE);
	ASSERT_EQ(mockup->path, path);
	ASSERT_EQ(mockup->val->getType(), BASYS_INT);
	ASSERT_EQ(static_cast<BRef<BValue>>(mockup->val)->getInt(), 10);
	
	// Check return value
	ASSERT_EQ(retVal->getType(), BASYS_INT);
	ASSERT_EQ(retVal->getInt(), 3);
}


///* ************************************************
// * Run test suite
// * ************************************************/
//int main(int argc, char **argv) {
//// Init gtest framework
//	::testing::InitGoogleTest(&argc, argv);
//
//// Run all tests
//	return RUN_ALL_TESTS();
//}
