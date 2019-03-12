/*
 * test_BaSyxNativeFrameBuilder.cpp
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#include <memory>

#include "gtest/gtest.h"
#include "gtest/gtest-all.cc"

#include "backends/protocols/connector/basyx/frame/BaSyxNativeFrameBuilder.h"
#include "backends/protocols/basyx/BaSyx.h"
#include "backends/protocols/provider/basyx/StringTools.h"


TEST(BaSyxNativeFrameBuilder, buildGetFrame) {
	std::unique_ptr<JSONTools> jsonTools(new JSONTools());
	std::unique_ptr<BaSyxNativeFrameBuilder> builder(new BaSyxNativeFrameBuilder(jsonTools.get()));
	
	std::unique_ptr<char> buffer(new char[1000]);
	std::string path = "TestPath";
	size_t size = builder->buildGetFrame(path, buffer.get());
	
	ASSERT_EQ(buffer.get()[0], BaSyxCommand::GET);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1), path);
	
	// 1 byte command, 4 byte string size, n byte string
	ASSERT_EQ(size, 1 + 4 + path.size());
}

TEST(BaSyxNativeFrameBuilder, buildSetFrame) {
	std::unique_ptr<JSONTools> jsonTools(new JSONTools());
	std::unique_ptr<BaSyxNativeFrameBuilder> builder(new BaSyxNativeFrameBuilder(jsonTools.get()));
	
	std::unique_ptr<char> buffer(new char[1000]);
	std::string path = "TestPath";
	BRef<BValue> val(new BValue(10));
	std::string valSerialize = jsonTools->serialize(val, 0, "").dump();
	
	size_t size = builder->buildSetFrame(path, val, buffer.get());
	ASSERT_EQ(buffer.get()[0], BaSyxCommand::SET);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1), path);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1 + 4 + path.length()), valSerialize);
	
	// 1 byte command, 4 byte string size, n byte string
	ASSERT_EQ(size, 1 + 4 + path.size() + 4 + valSerialize.length());
}

TEST(BaSyxNativeFrameBuilder, buildCreateFrame) {
	std::unique_ptr<JSONTools> jsonTools(new JSONTools());
	std::unique_ptr<BaSyxNativeFrameBuilder> builder(new BaSyxNativeFrameBuilder(jsonTools.get()));
	
	std::unique_ptr<char> buffer(new char[1000]);
	std::string path = "TestPath";
	BRef<BValue> val(new BValue(10));
	std::string valSerialize = jsonTools->serialize(val, 0, "").dump();
	
	size_t size = builder->buildCreateFrame(path, val, buffer.get());
	ASSERT_EQ(buffer.get()[0], BaSyxCommand::CREATE);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1), path);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1 + 4 + path.length()), valSerialize);
	
	// 1 byte command, 4 byte string size, n byte string
	ASSERT_EQ(size, 1 + 4 + path.size() + 4 + valSerialize.length());
}

TEST(BaSyxNativeFrameBuilder, buildSimpleDeleteFrame) {
	std::unique_ptr<JSONTools> jsonTools(new JSONTools());
	std::unique_ptr<BaSyxNativeFrameBuilder> builder(new BaSyxNativeFrameBuilder(jsonTools.get()));
	
	std::unique_ptr<char> buffer(new char[1000]);
	std::string path = "TestPath";
	size_t size = builder->buildDeleteFrame(path, buffer.get());
	
	ASSERT_EQ(buffer.get()[0], BaSyxCommand::DEL);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1), path);
	
	// 1 byte command, 4 byte string size, n byte string
	ASSERT_EQ(size, 1 + 4 + path.size());
}

TEST(BaSyxNativeFrameBuilder, buildComplexDeleteFrame) {
	std::unique_ptr<JSONTools> jsonTools(new JSONTools());
	std::unique_ptr<BaSyxNativeFrameBuilder> builder(new BaSyxNativeFrameBuilder(jsonTools.get()));
	
	std::unique_ptr<char> buffer(new char[1000]);
	std::string path = "TestPath";
	BRef<BValue> val(new BValue(10));
	std::string valSerialize = jsonTools->serialize(val, 0, "").dump();
	
	size_t size = builder->buildDeleteFrame(path, val, buffer.get());
	ASSERT_EQ(buffer.get()[0], BaSyxCommand::DEL);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1), path);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1 + 4 + path.length()), valSerialize);
	
	// 1 byte command, 4 byte string size, n byte string
	ASSERT_EQ(size, 1 + 4 + path.size() + 4 + valSerialize.length());
}

TEST(BaSyxNativeFrameBuilder, buildInvokeFrame) {
	std::unique_ptr<JSONTools> jsonTools(new JSONTools());
	std::unique_ptr<BaSyxNativeFrameBuilder> builder(new BaSyxNativeFrameBuilder(jsonTools.get()));
	
	std::unique_ptr<char> buffer(new char[1000]);
	std::string path = "TestPath";
	BRef<BValue> val(new BValue(10));
	std::string valSerialize = jsonTools->serialize(val, 0, "").dump();
	
	size_t size = builder->buildInvokeFrame(path, val, buffer.get());
	ASSERT_EQ(buffer.get()[0], BaSyxCommand::INVOKE);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1), path);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1 + 4 + path.length()), valSerialize);
	
	// 1 byte command, 4 byte string size, n byte string
	ASSERT_EQ(size, 1 + 4 + path.size() + 4 + valSerialize.length());
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
