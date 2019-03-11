/*
 * BaSyxNativeFrameBuilder.cpp
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#include "backends/protocols/connector/basyx/frame/BaSyxNativeFrameBuilder.h"
#include "backends/protocols/provider/basyx/StringTools.h"

BaSyxNativeFrameBuilder::BaSyxNativeFrameBuilder(JSONTools* jsonTools) {
	this->jsonTools = jsonTools;
}

size_t BaSyxNativeFrameBuilder::buildGetFrame(std::string const& path,
		char* buffer) {
	return encodeCommandAndPath(BaSyxCommand::GET, path, buffer);
}

size_t BaSyxNativeFrameBuilder::buildSetFrame(std::string const& path,
		BRef<BType> newVal, char* buffer) {
	size_t size = encodeCommandAndPath(BaSyxCommand::SET, path, buffer);
	size += encodeBRef(newVal, buffer + size);
	return size;
}

size_t BaSyxNativeFrameBuilder::buildCreateFrame(std::string const& path,
		BRef<BType> newVal, char* buffer) {
	size_t size = encodeCommandAndPath(BaSyxCommand::CREATE, path, buffer);
	size += encodeBRef(newVal, buffer + size);
	return size;
}

size_t BaSyxNativeFrameBuilder::buildDeleteFrame(std::string const& path,
		char* buffer) {
	return encodeCommandAndPath(BaSyxCommand::DEL, path, buffer);
}

size_t BaSyxNativeFrameBuilder::buildDeleteFrame(std::string const& path,
		BRef<BType> deleteVal, char* buffer) {
	size_t size = encodeCommandAndPath(BaSyxCommand::DEL, path, buffer);
	size += encodeBRef(deleteVal, buffer + size);
	return size;
}

size_t BaSyxNativeFrameBuilder::buildInvokeFrame(std::string const& path,
		BRef<BType> param, char* buffer) {
	size_t size = encodeCommandAndPath(BaSyxCommand::INVOKE, path, buffer);
	size += encodeBRef(param, buffer + size);
	return size;
}

size_t BaSyxNativeFrameBuilder::encodeCommand(BaSyxCommand command,
		char* buffer) {
	buffer[0] = command;
	return 1;
}

size_t BaSyxNativeFrameBuilder::encodeBRef(BRef<BType> val, char* buffer) {
	std::string dumped = jsonTools->serialize(val, 0, "").dump(); // TODO: Scope?
	return StringTools::toArray(dumped, buffer);
}

size_t BaSyxNativeFrameBuilder::encodeCommandAndPath(BaSyxCommand command,
		std::string const& path, char* buffer) {
	size_t size = encodeCommand(command, buffer);
	size += StringTools::toArray(path, buffer + size);
	return size;
}

