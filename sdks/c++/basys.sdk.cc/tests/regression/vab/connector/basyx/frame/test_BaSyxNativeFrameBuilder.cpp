/*
 * test_BaSyxNativeFrameBuilder.cpp
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#include <memory>

#include <gtest/gtest.h>

#include "vab/backend/connector/native/frame/BaSyxNativeFrameBuilder.h"

#include "basyx/types.h"

#include "util/util.h"
#include "util/tools/StringTools.h"


TEST(BaSyxNativeFrameBuilder, buildGetFrame) {
	auto builder = util::make_unique<basyx::vab::connector::native::frame::BaSyxNativeFrameBuilder>();

	std::unique_ptr<char> buffer(new char[1000]);
	std::string path = "TestPath";
	size_t size = builder->buildGetFrame(path, buffer.get());
	
	ASSERT_EQ(buffer.get()[0], BaSyxCommand::GET);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1), path);
	
	// 1 byte command, 4 byte string size, n byte string
	ASSERT_EQ(size, 1 + 4 + path.size());
}

TEST(BaSyxNativeFrameBuilder, buildSetFrame) {
	auto builder = util::make_unique<basyx::vab::connector::native::frame::BaSyxNativeFrameBuilder>();

	std::unique_ptr<char> buffer(new char[1000]);
	std::string path = "TestPath";
	basyx::any val = 10;
	std::string valSerialize = basyx::serialization::json::serialize(val).dump(4);

	size_t size = builder->buildSetFrame(path, val, buffer.get());
	ASSERT_EQ(buffer.get()[0], BaSyxCommand::SET);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1), path);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1 + 4 + path.length()), valSerialize);
	
	// 1 byte command, 4 byte string size, n byte string
	ASSERT_EQ(size, 1 + 4 + path.size() + 4 + valSerialize.length());
}

TEST(BaSyxNativeFrameBuilder, buildCreateFrame) {
	auto builder = util::make_unique<basyx::vab::connector::native::frame::BaSyxNativeFrameBuilder>();

	std::unique_ptr<char> buffer(new char[1000]);
	std::string path = "TestPath";
	basyx::any val = 10;
	std::string valSerialize = basyx::serialization::json::serialize(val).dump(4);

	size_t size = builder->buildCreateFrame(path, val, buffer.get());
	ASSERT_EQ(buffer.get()[0], BaSyxCommand::CREATE);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1), path);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1 + 4 + path.length()), valSerialize);
	
	// 1 byte command, 4 byte string size, n byte string
	ASSERT_EQ(size, 1 + 4 + path.size() + 4 + valSerialize.length());
}

TEST(BaSyxNativeFrameBuilder, buildSimpleDeleteFrame) {
	auto builder = util::make_unique<basyx::vab::connector::native::frame::BaSyxNativeFrameBuilder>();

	std::unique_ptr<char> buffer(new char[1000]);
	std::string path = "TestPath";
	size_t size = builder->buildDeleteFrame(path, buffer.get());
	
	ASSERT_EQ(buffer.get()[0], BaSyxCommand::DEL);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1), path);
	
	// 1 byte command, 4 byte string size, n byte string
	ASSERT_EQ(size, 1 + 4 + path.size());
}

TEST(BaSyxNativeFrameBuilder, buildComplexDeleteFrame) {
	auto builder = util::make_unique<basyx::vab::connector::native::frame::BaSyxNativeFrameBuilder>();

	std::unique_ptr<char> buffer(new char[1000]);
	std::string path = "TestPath";
	basyx::any val = 10;
	std::string valSerialize = basyx::serialization::json::serialize(val).dump(4);

	size_t size = builder->buildDeleteFrame(path, val, buffer.get());
	ASSERT_EQ(buffer.get()[0], BaSyxCommand::DEL);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1), path);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1 + 4 + path.length()), valSerialize);
	
	// 1 byte command, 4 byte string size, n byte string
	ASSERT_EQ(size, 1 + 4 + path.size() + 4 + valSerialize.length());
}

TEST(BaSyxNativeFrameBuilder, buildInvokeFrame) {
	auto builder = util::make_unique<basyx::vab::connector::native::frame::BaSyxNativeFrameBuilder>();

	std::unique_ptr<char> buffer(new char[1000]);
	std::string path = "TestPath";
	basyx::any val = 10;
	std::string valSerialize = basyx::serialization::json::serialize(val).dump(4);

	size_t size = builder->buildInvokeFrame(path, val, buffer.get());
	ASSERT_EQ(buffer.get()[0], BaSyxCommand::INVOKE);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1), path);
	ASSERT_EQ(StringTools::fromArray(buffer.get() + 1 + 4 + path.length()), valSerialize);
	
	// 1 byte command, 4 byte string size, n byte string
	ASSERT_EQ(size, 1 + 4 + path.size() + 4 + valSerialize.length());
}