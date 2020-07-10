/*
 * BaSyxNativeProvider.cpp
 *
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#include <BaSyx/vab/provider/native/frame/BaSyxNativeFrameProcessor.h>

#include <BaSyx/vab/provider/native/frame/BaSyxNativeFrameHelper.h>

#include <BaSyx/util/tools/CoderTools.h>
#include <BaSyx/util/tools/StringTools.h>

#include <BaSyx/shared/types.h>

namespace basyx {
namespace vab {
namespace provider {
namespace native {
namespace frame {

using connector::native::Frame;


BaSyxNativeFrameProcessor::BaSyxNativeFrameProcessor(vab::core::IModelProvider* providerBackend) 
	: jsonProvider{ providerBackend}
{
}

Frame BaSyxNativeFrameProcessor::processInputFrame(const Frame & frame)
{
	auto command = static_cast<BaSyxCommand>(frame.getFlag());

	switch (command) 
	{
	case BaSyxCommand::Get:
		return processGet(frame);
	case BaSyxCommand::Set:
		return processSet(frame);
	case BaSyxCommand::Create:
		return processCreate(frame);
	case BaSyxCommand::Delete:
		return processDelete(frame);
	case BaSyxCommand::Invoke:
		return processInvoke(frame);
	}

	return Frame{};
}

Frame BaSyxNativeFrameProcessor::processGet(const Frame & frame)
{
	auto path = frame.getFirstValue();
	auto getResult = jsonProvider.processBaSysGet(path);

	return Frame{ 0x00, getResult };
}

Frame BaSyxNativeFrameProcessor::processSet(const Frame & frame)
{
	auto path = frame.getFirstValue();
	auto value = frame.getSecondValue();

	auto result = jsonProvider.processBaSysSet(path, value);

	return Frame{ 0x00, result };
};

Frame BaSyxNativeFrameProcessor::processCreate(const Frame & frame)
{
	auto path = frame.getFirstValue();
	auto value = frame.getSecondValue();

	auto result = jsonProvider.processBaSysCreate(path, value);

	return Frame{ 0x00, result };
};

Frame BaSyxNativeFrameProcessor::processDelete(const Frame & frame)
{
	auto path = frame.getFirstValue();
	auto value = frame.getSecondValue();

	// if no value specified, simple delete
	if (value.empty())
	{
		auto result = jsonProvider.processBaSysDelete(path);
		return Frame{ 0x00, result };
	};

	auto result = jsonProvider.processBaSysDelete(path, value);
	return Frame{ 0x00, result };
};

Frame BaSyxNativeFrameProcessor::processInvoke(const Frame & frame)
{
	auto path = frame.getFirstValue();
	auto value = frame.getSecondValue();

	auto result = jsonProvider.processBaSysInvoke(path, value);

	return Frame{ 0x00, result };
};


}
}
}
}
}
