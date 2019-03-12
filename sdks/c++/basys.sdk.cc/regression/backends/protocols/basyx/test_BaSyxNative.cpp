/*
 * test_BaSyxNative.cpp
 * Tests the whole loop of BaSyxNativeConnector -> BaSyxNativeProvider
 *  Created on: 15.08.2018
 *      Author: schnicke
 */

#include <memory>

#include "gtest/gtest.h"
#include "gtest/gtest-all.cc"
#include "json/JSONTools.h"

#include "backends/protocols/connector/basyx/BaSyxNativeConnector.h"
#include "backends/protocols/provider/basyx/BaSyxTCPServer.h"

#include "regression/tests/backends/protocols/provider/basyx/frame/MockupModelProvider.h"


#include <process.h>
#include <unistd.h>


bool stopped = false;


unsigned int __stdcall startProvider(void* arg) {
	BaSyxTCPServer<MockupModelProvider>* tcpServer = reinterpret_cast<BaSyxTCPServer<MockupModelProvider>*>(arg);
	while (!stopped) {
		tcpServer->update();
	}
	
    _endthreadex( 0 );  
    return 0;  
}

/**
 * Creates the test environment needed in the following tests:
 * - Provides BaSyxTCPServer
 * - Provides BaSyxNativeConnector
 */
class BaSyxNativeTest: public ::testing::Test {
protected:
	JSONTools* jTools;
	BaSyxNativeConnector* connector;
	BaSyxNativeFrameBuilder* builder;
	BaSyxTCPServer<MockupModelProvider>* tcpServer;
	MockupModelProvider* mockup;

	virtual void SetUp() {
		stopped = false;
		jTools = new JSONTools();
		builder = new BaSyxNativeFrameBuilder(jTools);

		mockup = new MockupModelProvider();
		std::string port = "27015";
		tcpServer = new BaSyxTCPServer<MockupModelProvider>(mockup, port,
				jTools);

		unsigned threadID;

		_beginthreadex(NULL, 0, &startProvider, tcpServer, 0, &threadID);

		connector = new BaSyxNativeConnector("127.0.0.1", port, builder,
				jTools);

	}

	virtual void TearDown() {
		stopped = true;
		
		delete connector;
		delete tcpServer;
		delete mockup;
		delete builder;
		delete jTools;
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


/* ************************************************
 * Run test suite
 * ************************************************/
int main(int argc, char **argv) {
// Init gtest framework
	::testing::InitGoogleTest(&argc, argv);

// Run all tests
	return RUN_ALL_TESTS();
}
