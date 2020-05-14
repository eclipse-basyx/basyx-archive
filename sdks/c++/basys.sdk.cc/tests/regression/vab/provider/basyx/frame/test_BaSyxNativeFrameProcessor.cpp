/*
 * test_BaSyxNativeProvider.cpp
 *
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#include <string>
#include <memory>

#include <gtest/gtest.h>

#include <BaSyx/shared/types.h>

#include <BaSyx/vab/provider/native/frame/BaSyxNativeFrameProcessor.h>

#include <BaSyx/util/tools/CoderTools.h>
#include <BaSyx/util/tools/StringTools.h>

#include "support/MockupModelProvider.h"

using namespace basyx;

TEST(BaSyxNativeFrameProcessor, getTest) {
	auto mockup = util::make_unique<MockupModelProvider>();
	vab::provider::native::frame::BaSyxNativeFrameProcessor provider(mockup.get());

	char frame[] = { static_cast<uint8_t>(BaSyxCommand::Get), 4, 0, 0, 0, 't', 'e', 's', 't' };

	std::unique_ptr<char> receive(new char[1000]);
	
	size_t txSize = 0;

	provider.processInputFrame(frame, sizeof frame, receive.get(), &txSize);

	ASSERT_EQ(mockup->path, "test");
	ASSERT_EQ(mockup->called, MockupModelProvider::CalledFunction::GET);
	ASSERT_EQ(receive.get()[0], (char ) 0);

	std::string serialized = StringTools::fromArray(receive.get() + 1);
	basyx::object deserialzed = basyx::serialization::json::deserialize(serialized);

	ASSERT_TRUE(deserialzed.InstanceOf<basyx::object::object_map_t>());
	auto & entityWrapper = deserialzed.Get<basyx::object::object_map_t&>();

	ASSERT_TRUE(entityWrapper.count("entity") == 1);
	auto val = entityWrapper["entity"];

	ASSERT_TRUE(val.InstanceOf<int>());
	ASSERT_EQ(val.Get<int>(), 2);
}

TEST(BaSyxNativeFrameProcessor, setTest) {
	auto mockup = util::make_unique<MockupModelProvider>();
	vab::provider::native::frame::BaSyxNativeFrameProcessor provider(mockup.get());

	std::unique_ptr<char> frame(new char[1000]);

	// Build test frame
	frame.get()[0] = static_cast<uint8_t>(BaSyxCommand::Set);

	std::string path = "TestPath/aas";
	StringTools::toArray(path, frame.get() + 1);

	std::string newVal = basyx::serialization::json::serialize(10).dump(4);

	// write serialized data after path
	StringTools::toArray(newVal, frame.get() + 1 + 4 + path.length());

	std::unique_ptr<char> receive(new char[1000]);
	size_t txSize;
	
	provider.processInputFrame(
		frame.get(),
		1 + 4 + path.length() + 4 + newVal.length(), 
		receive.get(),
		&txSize
	);

	ASSERT_EQ(receive.get()[0], (char ) 0);

	ASSERT_EQ(mockup->called, MockupModelProvider::CalledFunction::SET);

	ASSERT_EQ(mockup->path, path);

	ASSERT_TRUE(mockup->val.InstanceOf<int>());
	ASSERT_EQ(mockup->val.Get<int>(), 10);
}

TEST(BaSyxNativeFrameProcessor, createTest) {
	auto mockup = util::make_unique<MockupModelProvider>();
	vab::provider::native::frame::BaSyxNativeFrameProcessor provider(mockup.get());

	std::unique_ptr<char> frame(new char[1000]);

	// Build test frame
	frame.get()[0] = static_cast<uint8_t>(BaSyxCommand::Create);

	std::string path = "TestPath";
	StringTools::toArray(path, frame.get() + 1);

	std::string newVal = basyx::serialization::json::serialize(10).dump(4);

	// write serialized data after path
	StringTools::toArray(newVal, frame.get() + 1 + 4 + path.length());

	std::unique_ptr<char> receive(new char[1000]);
	size_t txSize;
	provider.processInputFrame(frame.get(),
			1 + 4 + path.length() + 4 + newVal.length(), receive.get(),
			&txSize);

	ASSERT_EQ(receive.get()[0], (char ) 0);

	ASSERT_EQ(mockup->called, MockupModelProvider::CalledFunction::CREATE);

	ASSERT_EQ(mockup->path, path);

	ASSERT_TRUE(mockup->val.InstanceOf<int>());
	ASSERT_EQ(mockup->val.Get<int>(), 10);
}

TEST(BaSyxNativeFrameProcessor, deleteComplexTest) {
	auto mockup = util::make_unique<MockupModelProvider>();
	vab::provider::native::frame::BaSyxNativeFrameProcessor provider(mockup.get());

	std::unique_ptr<char> frame(new char[1000]);

	// Build test frame
	frame.get()[0] = static_cast<uint8_t>(BaSyxCommand::Delete);

	std::string path = "TestPath/aas/properties/C1";
	StringTools::toArray(path, frame.get() + 1);

	std::string deleteVal = basyx::serialization::json::serialize(10).dump(4);

	// write serialized data after path
	StringTools::toArray(deleteVal, frame.get() + 1 + 4 + path.length());

	std::unique_ptr<char> receive(new char[1000]);
	size_t txSize;
	provider.processInputFrame(frame.get(),
			1 + 4 + path.length() + 4 + deleteVal.length(), receive.get(),
			&txSize);

	ASSERT_EQ(receive.get()[0], (char ) 0);

	ASSERT_EQ(mockup->called,
			MockupModelProvider::CalledFunction::DELETE_COMPLEX);

	ASSERT_EQ(mockup->path, path);

	ASSERT_TRUE(mockup->val.InstanceOf<int>());
	ASSERT_EQ(mockup->val.Get<int>(), 10);
}

TEST(BaSyxNativeFrameProcessor, deleteSimpleTest) {
	auto mockup = util::make_unique<MockupModelProvider>();
	vab::provider::native::frame::BaSyxNativeFrameProcessor provider(mockup.get());

	std::unique_ptr<char> frame(new char[1000]);

	// Build test frame
	frame.get()[0] = static_cast<uint8_t>(BaSyxCommand::Delete);

	std::string path = "TestPath/aas/properties/P1";
	StringTools::toArray(path, frame.get() + 1);

	std::unique_ptr<char> receive(new char[1000]);
	size_t txSize;
	provider.processInputFrame(frame.get(), 1 + 4 + path.length(),
			receive.get(), &txSize);

	ASSERT_EQ(receive.get()[0], (char ) 0);

	ASSERT_EQ(mockup->called, MockupModelProvider::CalledFunction::DELETE_SIMPLE);

	ASSERT_EQ(mockup->path, path);
}

TEST(BaSyxNativeFrameProcessor, invokeTest) {
	auto mockup = util::make_unique<MockupModelProvider>();
	vab::provider::native::frame::BaSyxNativeFrameProcessor provider{ mockup.get() };

	std::unique_ptr<char> frame(new char[1000]);

	// Build test frame
	frame.get()[0] = static_cast<uint8_t>(BaSyxCommand::Invoke);

	std::string path = "TestPath";
	StringTools::toArray(path, frame.get() + 1);


	std::string newVal = basyx::serialization::json::serialize(10).dump();

	// write serialized data after path
	StringTools::toArray(newVal, frame.get() + 1 + 4 + path.length());

	std::unique_ptr<char> receive(new char[1000]);
	size_t txSize;
	provider.processInputFrame(frame.get(),
			1 + 4 + path.length() + 4 + newVal.length(), receive.get(),
			&txSize);

	ASSERT_EQ(mockup->called, MockupModelProvider::CalledFunction::INVOKE);
	ASSERT_EQ(mockup->path, path);
	ASSERT_TRUE(mockup->val.InstanceOf<int>());
	ASSERT_EQ(mockup->val.Get<int>(), 10);

	ASSERT_EQ(receive.get()[0], (char ) 0);

	// Deserialize return value of operation
	std::string serialized = StringTools::fromArray(receive.get() + 1);
	auto val = basyx::serialization::json::deserialize(nlohmann::json::parse(serialized)["entity"]);
	ASSERT_TRUE(val.InstanceOf<int>());
	ASSERT_EQ(val.Get<int>(),3);
}
