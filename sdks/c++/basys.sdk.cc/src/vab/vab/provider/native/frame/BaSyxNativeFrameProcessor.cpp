/*
 * BaSyxNativeProvider.cpp
 *
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#include "BaSyxNativeFrameProcessor.h"

#include "vab/provider/native/frame/BaSyxNativeFrameHelper.h"

#include "util/tools/CoderTools.h"
#include "util/tools/StringTools.h"

#include "basyx/types.h"
 
namespace basyx {
namespace vab {
namespace provider {
namespace native {
namespace frame {

BaSyxNativeFrameProcessor::BaSyxNativeFrameProcessor(vab::core::IModelProvider* providerBackend) 
	: jsonProvider{ providerBackend}
{
}

BaSyxNativeFrameProcessor::~BaSyxNativeFrameProcessor() 
{
}

void BaSyxNativeFrameProcessor::processInputFrame(char const* rxFrame, std::size_t rxSize, char* txFrame, std::size_t* txSize) 
{
	std::size_t offset;
	char command = vab::provider::native::frame::BaSyxNativeFrameHelper::getCommand(rxFrame, &offset);
	rxFrame += offset;
	switch (command) {
	case BaSyxCommand::GET:
		processGet(rxFrame, txFrame, txSize);
		break;
	case BaSyxCommand::SET:
		processSet(rxFrame, txFrame, txSize);
		break;
	case BaSyxCommand::CREATE:
		processCreate(rxFrame, txFrame, txSize);
		break;
	case BaSyxCommand::DEL:
		processDelete(rxFrame, rxSize - offset, txFrame, txSize);
		break;
	case BaSyxCommand::INVOKE:
		processInvoke(rxFrame, txFrame, txSize);
		break;

	}
}

void BaSyxNativeFrameProcessor::processGet(char const* rxFrame, char* txFrame, std::size_t* txSize) 
{
	// Try to get the requested value
	// TODO: Error Handling?

	std::string path = BaSyxNativeFrameHelper::getString(rxFrame, 0);
	// Advance txFrame by 5 because of the following setup of txFrame:
	// 1 byte result field
	// 4 byte string size
	// N byte return value
	std::string getResult = jsonProvider.processBaSysGet(path);
	*txSize += getResult.size();
	memcpy(txFrame + 5, getResult.c_str(), getResult.size());

	// Set return string size
	CoderTools::setInt32(txFrame + 1, 0, *txSize);
	*txSize += BASYX_STRINGSIZE_SIZE;
	
	// Set result field to 0 to indicate success
	txFrame[0] = 0;
	*txSize += 1;
}

void BaSyxNativeFrameProcessor::processSet(char const* rxFrame, char* txFrame, std::size_t* txSize) 
{
	std::string path = BaSyxNativeFrameHelper::getString(rxFrame, 0);

	// TODO: Error Handling?
	std::string serializedValue = BaSyxNativeFrameHelper::getString(rxFrame, 1);
	jsonProvider.processBaSysSet(path, serializedValue);

	// Set result field to 0 to indicate success
	txFrame[0] = 0;
	*txSize += 1;
}

void BaSyxNativeFrameProcessor::processCreate(char const* rxFrame, char* txFrame, std::size_t* txSize)
{
	std::string path = BaSyxNativeFrameHelper::getString(rxFrame, 0);

	// TODO: Error Handling?
	std::string serializedValue = BaSyxNativeFrameHelper::getString(rxFrame, 1);
	jsonProvider.processBaSysCreate(path, serializedValue);

	// Set result field to 0 to indicate success
	txFrame[0] = 0;
	*txSize += 1;
}

void BaSyxNativeFrameProcessor::processDelete(char const* rxFrame, std::size_t rxSize, char* txFrame, std::size_t* txSize) 
{
	std::string path = BaSyxNativeFrameHelper::getString(rxFrame, 0);

	// Check if there is a serialized json after the path to distinguish between map/collection delete and simple delete
	if (path.size() + BASYX_STRINGSIZE_SIZE < rxSize) {
		std::string serializedValue = BaSyxNativeFrameHelper::getString(rxFrame,
				1);
		jsonProvider.processBaSysDelete(path, serializedValue);
	} else {
		jsonProvider.processBaSysDelete(path);
	}

	// Set result field to 0 to indicate success
	txFrame[0] = 0;
	*txSize += 1;
}

void BaSyxNativeFrameProcessor::processInvoke(char const* rxFrame, char* txFrame, std::size_t* txSize)
{
	std::string path = BaSyxNativeFrameHelper::getString(rxFrame, 0);

	// TODO: Error Handling?
	std::string serializedValue = BaSyxNativeFrameHelper::getString(rxFrame, 1);

	// Advance txFrame by 5 because of the following setup of txFrame:
	// 1 byte result field
	// 4 byte string size
	// N byte return value
	auto result = jsonProvider.processBaSysInvoke(path, serializedValue, txFrame + 5,
			txSize);

	*txSize = result.size();
	memcpy(txFrame + 5, result.c_str(), result.size());

	// Set return value size
	CoderTools::setInt32(txFrame + 1, 0, *txSize);
	*txSize += BASYX_STRINGSIZE_SIZE;

	// Set result field to 0 to indicate success
	txFrame[0] = 0;
	*txSize += 1;
}


}
}
}
}
}
