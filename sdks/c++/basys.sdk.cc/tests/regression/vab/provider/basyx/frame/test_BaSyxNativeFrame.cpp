#include <gtest/gtest.h>

#include <BaSyx/shared/types.h>

#include <BaSyx/vab/backend/connector/native/frame/Frame.h>

using namespace basyx::vab::connector::native;

TEST(BaSyxNativeFrame, frameTestNoValue)
{
	Frame frame;
	frame.setFlag(static_cast<uint8_t>(BaSyxCommand::Get));
	frame.setFirstValue("/test/path");

	char buffer[4096];
	memset(buffer, 0, 4096);

	Frame::write_to_buffer(basyx::net::make_buffer(buffer, 4096), frame);
	auto frame_b = Frame::read_from_buffer(basyx::net::make_buffer(buffer, 4096));

	ASSERT_EQ(buffer[frame.size()], 0x0);

	ASSERT_EQ(frame_b.getFlag(), frame.getFlag());
	ASSERT_EQ(frame_b.getFirstValue(), frame.getFirstValue());
	ASSERT_EQ(frame_b.getSecondValue(), frame.getSecondValue());
}

TEST(BaSyxNativeFrame, frameTestWithValue) 
{
	Frame frame;
	frame.setFlag(BaSyxCommand::Set);
	frame.setFirstValue("/test/path");
	frame.setSecondValue("{testValue}");

	char buffer[4096];
	memset(buffer, 0, 4096);

	Frame::write_to_buffer(basyx::net::make_buffer(buffer, 4096), frame);
	auto frame_b = Frame::read_from_buffer(basyx::net::make_buffer(buffer, 4096));

	ASSERT_EQ(buffer[frame.size()], 0x0);

	ASSERT_EQ(frame_b.getFlag(), frame.getFlag());
	ASSERT_EQ(frame_b.getFirstValue(), frame.getFirstValue());
	ASSERT_EQ(frame_b.getSecondValue(), frame.getSecondValue());
}

TEST(BaSyxNativeFrame, bufferTooSmall)
{
	Frame frame;
	frame.setFlag(BaSyxCommand::Set);
	frame.setFirstValue("/test/path");
	frame.setSecondValue("{testValue}");

	char buffer[4096];
	memset(buffer, 0, 4096);

	auto result = Frame::write_to_buffer(basyx::net::make_buffer(buffer, 10), frame);
	ASSERT_FALSE(result);
}
