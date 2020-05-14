/*
 * BaSyxNativeFrameBuilder.cpp
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#include <BaSyx/vab/backend/connector/native/frame/BaSyxNativeFrameBuilder.h>

#include <BaSyx/shared/serialization/json.h>

#include <BaSyx/util/tools/StringTools.h>

namespace basyx {
namespace vab {
namespace connector {
namespace native {
namespace frame {

	
BaSyxNativeFrameBuilder::BaSyxNativeFrameBuilder() { };

size_t BaSyxNativeFrameBuilder::buildGetFrame(std::string const& path, char * buffer)
{
	return encodeCommandAndPath(BaSyxCommand::Get, path, buffer);
}

size_t BaSyxNativeFrameBuilder::buildSetFrame(std::string const& path, const basyx::object & newVal, char * buffer)
{
	size_t size = encodeCommandAndPath(BaSyxCommand::Set, path, buffer);
	size += encodeValue(newVal, buffer + size);
	return size;
}

size_t BaSyxNativeFrameBuilder::buildCreateFrame(std::string const& path, const basyx::object & newVal, char * buffer)
{
	size_t size = encodeCommandAndPath(BaSyxCommand::Create, path, buffer);
	size += encodeValue(newVal, buffer + size);
	return size;
}

size_t BaSyxNativeFrameBuilder::buildDeleteFrame(std::string const& path, char * buffer)
{
	return encodeCommandAndPath(BaSyxCommand::Delete, path, buffer);
}

size_t BaSyxNativeFrameBuilder::buildDeleteFrame(std::string const& path, const basyx::object & deleteVal, char * buffer)
{
	size_t size = encodeCommandAndPath(BaSyxCommand::Delete, path, buffer);
	size += encodeValue(deleteVal, buffer + size);
	return size;
}

size_t BaSyxNativeFrameBuilder::buildInvokeFrame(std::string const& path, const basyx::object & param, char * buffer)
{
	size_t size = encodeCommandAndPath(BaSyxCommand::Invoke, path, buffer);
	size += encodeValue(param, buffer + size);
	return size;
}

size_t BaSyxNativeFrameBuilder::buildInvokeFrame(std::string const& path, const basyx::object::object_list_t & params, char * buffer)
{
	size_t size = encodeCommandAndPath(BaSyxCommand::Invoke, path, buffer);
	size += encodeValue(params, buffer + size);
	return size;
}

size_t BaSyxNativeFrameBuilder::encodeCommand(BaSyxCommand command, char* buffer)
{
	buffer[0] = static_cast<uint8_t>(command);
	return 1;
}

std::size_t BaSyxNativeFrameBuilder::encodeValue(const basyx::object & value, char * buffer)
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
