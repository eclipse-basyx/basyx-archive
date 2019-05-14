/*
 * test_BaSyxNativeProvider.cpp
 *
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#include <string>
#include <memory>

#include <gtest/gtest.h>

#include <backends/protocols/basyx/BaSyx.h>

#include <backends/protocols/provider/basyx/frame/BaSyxNativeFrameProcessor.h>
#include <backends/protocols/provider/basyx/CoderTools.h>
#include <backends/protocols/provider/basyx/StringTools.h>

#include <regression/backends/protocols/provider/basyx/frame/MockupModelProvider.h>

TEST(BaSyxNativeFrameProcessor, getTest) {
	auto mockup = util::make_unique<MockupModelProvider>();
	BaSyxNativeFrameProcessor provider(mockup.get());

	char frame[] = { BaSyxCommand::GET, 4, 0, 0, 0, 't', 'e', 's', 't' };

	std::unique_ptr<char> receive(new char[1000]);
	
	size_t txSize = 0;

	provider.processInputFrame(frame, sizeof frame, receive.get(), &txSize);

	ASSERT_EQ(mockup->path, "test");
	ASSERT_EQ(mockup->called, MockupModelProvider::CalledFunction::GET);
	ASSERT_EQ(receive.get()[0], (char ) 0);

	std::string serialized = StringTools::fromArray(receive.get() + 1);
	basyx::any val = basyx::json::deserialize(serialized);

	ASSERT_TRUE(val.InstanceOf<int>());
	ASSERT_EQ(val.Get<int>(), 2);
}

TEST(BaSyxNativeFrameProcessor, setTest) {
	auto mockup = util::make_unique<MockupModelProvider>();
	BaSyxNativeFrameProcessor provider(mockup.get());

	std::unique_ptr<char> frame(new char[1000]);

	// Build test frame
	frame.get()[0] = BaSyxCommand::SET;

	std::string path = "TestPath/aas";
	StringTools::toArray(path, frame.get() + 1);

	std::string newVal = basyx::json::serialize(10).dump(4);

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
	BaSyxNativeFrameProcessor provider(mockup.get());

	std::unique_ptr<char> frame(new char[1000]);

	// Build test frame
	frame.get()[0] = BaSyxCommand::CREATE;

	std::string path = "TestPath";
	StringTools::toArray(path, frame.get() + 1);

	std::string newVal = basyx::json::serialize(10).dump(4);

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
	BaSyxNativeFrameProcessor provider(mockup.get());

	std::unique_ptr<char> frame(new char[1000]);

	// Build test frame
	frame.get()[0] = BaSyxCommand::DEL;

	std::string path = "TestPath/aas/properties/C1";
	StringTools::toArray(path, frame.get() + 1);

	std::string deleteVal = basyx::json::serialize(10).dump(4);

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
	BaSyxNativeFrameProcessor provider(mockup.get());

	std::unique_ptr<char> frame(new char[1000]);

	// Build test frame
	frame.get()[0] = BaSyxCommand::DEL;

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

//TODO: invokeTest
//TEST(BaSyxNativeFrameProcessor, invokeTest) {
//	auto mockup = util::make_unique<MockupModelProvider>();
//	BaSyxNativeFrameProcessor provider(mockup.get());
//
//	std::unique_ptr<char> frame(new char[1000]);
//
//	// Build test frame
//	frame.get()[0] = BaSyxCommand::INVOKE;
//
//	std::string path = "TestPath";
//	StringTools::toArray(path, frame.get() + 1);
//
//	std::string newVal = jsonTools.get()->serialize(
//			BRef<BValue>(new BValue(10)), 0, "").dump();
//
//	// write serialized data after path
//	StringTools::toArray(newVal, frame.get() + 1 + 4 + path.length());
//
//	std::unique_ptr<char> receive(new char[1000]);
//	size_t txSize;
//	provider.processInputFrame(frame.get(),
//			1 + 4 + path.length() + 4 + newVal.length(), receive.get(),
//			&txSize);
//
//	ASSERT_EQ(mockup->called, MockupModelProvider::CalledFunction::INVOKE);
//	ASSERT_EQ(mockup->path, path);
//	ASSERT_EQ(mockup->val->getType(), BASYS_INT);
//	ASSERT_EQ(static_cast<BRef<BValue>>(mockup->val)->getInt(), 10);
//
//	ASSERT_EQ(receive.get()[0], (char ) 0);
//
//	// Deserialize return value of operation
//	std::string serialized = StringTools::fromArray(receive.get() + 1);
//	BRef<BValue> val = jsonTools->deserialize(json::parse(serialized), 0, "");
//	ASSERT_EQ(val->getType(), BASYS_INT);
//	ASSERT_EQ(val->getInt(),3);
//}