/*
 * BaSyxNativeFrameBuilder.cpp
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#include "BaSyxNativeFrameBuilder.h"

#include <basyx/serialization/json.h>

#include <util/tools/StringTools.h>

namespace basyx {
namespace vab {
namespace connector {
namespace native {
namespace frame {

	
BaSyxNativeFrameBuilder::BaSyxNativeFrameBuilder() { };

size_t BaSyxNativeFrameBuilder::buildGetFrame(std::string const& path, char * buffer)
{
	return encodeCommandAndPath(BaSyxCommand::GET, path, buffer);
}

size_t BaSyxNativeFrameBuilder::buildSetFrame(std::string const& path, const basyx::any & newVal, char * buffer)
{
	size_t size = encodeCommandAndPath(BaSyxCommand::SET, path, buffer);
	size += encodeValue(newVal, buffer + size);
	return size;
}

size_t BaSyxNativeFrameBuilder::buildCreateFrame(std::string const& path, const basyx::any & newVal, char * buffer)
{
	size_t size = encodeCommandAndPath(BaSyxCommand::CREATE, path, buffer);
	size += encodeValue(newVal, buffer + size);
	return size;
}

size_t BaSyxNativeFrameBuilder::buildDeleteFrame(std::string const& path, char * buffer)
{
	return encodeCommandAndPath(BaSyxCommand::DEL, path, buffer);
}

size_t BaSyxNativeFrameBuilder::buildDeleteFrame(std::string const& path, const basyx::any & deleteVal, char * buffer)
{
	size_t size = encodeCommandAndPath(BaSyxCommand::DEL, path, buffer);
	size += encodeValue(deleteVal, buffer + size);
	return size;
}

size_t BaSyxNativeFrameBuilder::buildInvokeFrame(std::string const& path, const basyx::any & param, char * buffer)
{
	size_t size = encodeCommandAndPath(BaSyxCommand::INVOKE, path, buffer);
	size += encodeValue(param, buffer + size);
	return size;
}

size_t BaSyxNativeFrameBuilder::buildInvokeFrame(std::string const& path, const basyx::objectCollection_t & params, char * buffer)
{
	size_t size = encodeCommandAndPath(BaSyxCommand::INVOKE, path, buffer);
	size += encodeValue(params, buffer + size);
	return size;
}

size_t BaSyxNativeFrameBuilder::encodeCommand(BaSyxCommand command, char* buffer)
{
	buffer[0] = command;
	return 1;
}

std::size_t BaSyxNativeFrameBuilder::encodeValue(const basyx::any & value, char * buffer)
{
	std::string dumped = basyx::serialization::json::serialize(value).dump(4);
	return StringTools::toArray(dumped, buffer);
}

size_t BaSyxNativeFrameBuilder::encodeCommandAndPath(BaSyxCommand command, std::string const& path, char* buffer)
{
	size_t size = encodeCommand(command, buffer);
	size += StringTools::toArray(path, buffer + size);
	return size;
}

}
}
}
}
}