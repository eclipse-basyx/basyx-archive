/*
 * BaSyxNativeProvider.cpp
 *
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#include "backends/protocols/provider/basyx/frame/BaSyxNativeFrameProcessor.h"

#include "backends/protocols/provider/basyx/CoderTools.h"
#include "backends/protocols/provider/basyx/StringTools.h"
#include "backends/protocols/provider/basyx/frame/BaSyxNativeFrameHelper.h"
#include "backends/protocols/basyx/BaSyx.h"

BaSyxNativeFrameProcessor::BaSyxNativeFrameProcessor(IModelProvider* providerBackend,
		JSONTools* jsonTools) {
	this->jsonTools = jsonTools;
	this->jsonProvider = new JSONProvider<IModelProvider>(providerBackend,
			jsonTools);
}

BaSyxNativeFrameProcessor::~BaSyxNativeFrameProcessor() {
	delete jsonProvider;
}

void BaSyxNativeFrameProcessor::processInputFrame(char const* rxFrame,
		std::size_t rxSize, char* txFrame, std::size_t* txSize) {
	std::size_t offset;
	char command = BaSyxNativeFrameHelper::getCommand(rxFrame, &offset);
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

void BaSyxNativeFrameProcessor::processGet(char const* rxFrame, char* txFrame,
		std::size_t* txSize) {

	// Try to get the requested value

	// TODO: Error Handling?

	std::string path = BaSyxNativeFrameHelper::getString(rxFrame, 0);
	// Advance txFrame by 5 because of the following setup of txFrame:
	// 1 byte result field
	// 4 byte string size
	// N byte return value
	jsonProvider->processBaSysGet(path, txFrame + 5, txSize);

	// Set return string size
	CoderTools::setInt32(txFrame + 1, 0, *txSize);
	*txSize += BASYX_STRINGSIZE_SIZE;
	
	// Set result field to 0 to indicate success
	txFrame[0] = 0;
	*txSize += 1;
}

void BaSyxNativeFrameProcessor::processSet(char const* rxFrame, char* txFrame,
		std::size_t* txSize) {
	std::string path = BaSyxNativeFrameHelper::getString(rxFrame, 0);

	// TODO: Error Handling?
	std::string serializedValue = BaSyxNativeFrameHelper::getString(rxFrame, 1);
	jsonProvider->processBaSysSet(path, serializedValue);

	// Set result field to 0 to indicate success
	txFrame[0] = 0;
	*txSize += 1;
}

void BaSyxNativeFrameProcessor::processCreate(char const* rxFrame, char* txFrame,
		std::size_t* txSize) {
	std::string path = BaSyxNativeFrameHelper::getString(rxFrame, 0);

	// TODO: Error Handling?
	std::string serializedValue = BaSyxNativeFrameHelper::getString(rxFrame, 1);
	jsonProvider->processBaSysCreate(path, serializedValue);

	// Set result field to 0 to indicate success
	txFrame[0] = 0;
	*txSize += 1;
}

void BaSyxNativeFrameProcessor::processDelete(char const* rxFrame, std::size_t rxSize,
		char* txFrame, std::size_t* txSize) {
	std::string path = BaSyxNativeFrameHelper::getString(rxFrame, 0);

	// Check if there is a serialized json after the path to distinguish between map/collection delete and simple delete
	if (path.size() + BASYX_STRINGSIZE_SIZE < rxSize) {
		std::string serializedValue = BaSyxNativeFrameHelper::getString(rxFrame,
				1);
		jsonProvider->processBaSysDelete(path, serializedValue);
	} else {
		jsonProvider->processBaSysDelete(path);
	}

	// Set result field to 0 to indicate success
	txFrame[0] = 0;
	*txSize += 1;
}

void BaSyxNativeFrameProcessor::processInvoke(char const* rxFrame, char* txFrame,
		std::size_t* txSize) {
	std::string path = BaSyxNativeFrameHelper::getString(rxFrame, 0);

	// TODO: Error Handling?
	std::string serializedValue = BaSyxNativeFrameHelper::getString(rxFrame, 1);

	// Advance txFrame by 5 because of the following setup of txFrame:
	// 1 byte result field
	// 4 byte string size
	// N byte return value
	jsonProvider->processBaSysInvoke(path, serializedValue, txFrame + 5,
			txSize);

	// Set return value size
	CoderTools::setInt32(txFrame + 1, 0, *txSize);
	*txSize += BASYX_STRINGSIZE_SIZE;

	// Set result field to 0 to indicate success
	txFrame[0] = 0;
	*txSize += 1;
}
