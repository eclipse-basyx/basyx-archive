/*
* NativeConnector.cpp
*
*  Created on: 14.08.2018
*      Author: schnicke
*/

#include "vab/backend/connector/native/BaSyxConnector.h"
#include "vab/backend/connector/native/frame/BaSyxNativeFrameBuilder.h"
#include "vab/provider/native/frame/BaSyxNativeFrameHelper.h"

#include <basyx/serialization/json.h>

#include <stdio.h>

#include <util/tools/StringTools.h>


namespace basyx {
namespace vab {
namespace connector {
namespace native {

NativeConnector::NativeConnector(std::string const& address, int port)
	: builder{}
	, socket{ basyx::net::tcp::Socket::Connect(address, port) }
	, log{ "NativeConnector" }
{
	log.trace("Connected to {}:{}", address, port);
}



NativeConnector::~NativeConnector() {
	this->socket.Close();
}



basyx::any NativeConnector::basysGet(std::string const& path)
{
	log.trace("basysGet() called:");
	log.trace("    path: {}", path);

	auto entityWrapper = basysGetRaw(path);
	auto value = basyx::serialization::json::deserialize(entityWrapper["entity"]);
	return value;
}

nlohmann::json NativeConnector::basysGetRaw(std::string const& path) {
	size_t size = builder.buildGetFrame(path, buffer.data() + BASYX_FRAMESIZE_SIZE);
	sendData(buffer.data(), size);
	size = receiveData(buffer.data());
	if (buffer[4] != 0) { // Error happened
		return ""_json; // TODO: Error handling
	}
	std::string data = StringTools::fromArray(buffer.data() + BASYX_FRAMESIZE_SIZE + 1);
	return nlohmann::json::parse(data);
}

void NativeConnector::basysSet(std::string const& path, const basyx::any & newValue)
{
	log.trace("basysSet() called:");
	log.trace("    path: {}", path);

	size_t size = builder.buildSetFrame(path, newValue, buffer.data() + BASYX_FRAMESIZE_SIZE);
	sendData(buffer.data(), size);
	size = receiveData(buffer.data());
}

void NativeConnector::basysCreate(std::string const& path, const basyx::any & val)
{
	size_t size = builder.buildCreateFrame(path, val, buffer.data() + BASYX_FRAMESIZE_SIZE);
	sendData(buffer.data(), size);
	size = receiveData(buffer.data());
}

basyx::any NativeConnector::basysInvoke(std::string const& path, const basyx::any & param)
{
	size_t size = builder.buildInvokeFrame(path, param, buffer.data() + BASYX_FRAMESIZE_SIZE);
	sendData(buffer.data(), size);
	size = receiveData(buffer.data());
	return decode(buffer.data() + 5);
}

void NativeConnector::basysDelete(std::string const& path)
{
	size_t size = builder.buildDeleteFrame(path, buffer.data() + BASYX_FRAMESIZE_SIZE);
	sendData(buffer.data(), size);
	size = receiveData(buffer.data());
}

void NativeConnector::basysDelete(std::string const& path, const basyx::any & obj) {
	size_t size = builder.buildDeleteFrame(path, obj, buffer.data() + BASYX_FRAMESIZE_SIZE);
	sendData(buffer.data(), size);
	size = receiveData(buffer.data());
}

// TODO: Error handling
/**
	* Builds a send frame and sends it to server
	* @param msg a frame constructed with the BaSyxNativeFrameBuilder
	*/

void NativeConnector::sendData(char* msg, size_t size) 
{
	log.trace("sendData() called");
	log.trace("    msg: 0x{0:x}", (std::size_t)msg);
	log.trace("    size: {}", size);

	CoderTools::setInt32(msg, 0, size);
	size += BASYX_FRAMESIZE_SIZE;
#ifdef PRINT_FRAME
	log.debug("Sending:");
	vab::provider::native::frame::BaSyxNativeFrameHelper::printFrame(msg, size);
#endif

	log.debug("Sending {} bytes.", size);
	int sent_bytes = this->socket.Send(basyx::net::make_buffer(msg, size));
	log.debug("Sent {} bytes.", sent_bytes);

	if (sent_bytes < 0) {
		log.error("Send failed! Error code: {}", this->socket.GetErrorCode());
	}
}

// TODO: Error handling
size_t NativeConnector::receiveData(char* data) {
	log.trace("receiveData() called");
	log.trace("    data: 0x{0:x}", (std::size_t)data);

	// recv(data, DEFAULT_BUFFER_LENGTH, 0);
	int recv_bytes = this->socket.Receive(basyx::net::make_buffer(data, default_buffer_length));

	log.debug("Received {} bytes.", recv_bytes);

	if (recv_bytes > 0) {
#ifdef PRINT_FRAME
		log.debug("Received:");
		vab::provider::native::frame::BaSyxNativeFrameHelper::printFrame(data, recv_bytes);
#endif
		return recv_bytes;
	}
	else {
		log.error("Receive failed! Error code: {}", recv_bytes);
		return 0;
	}
}

basyx::any NativeConnector::decode(char* buffer)
{
	std::string data = StringTools::fromArray(buffer);
	return basyx::serialization::json::deserialize(data).Get<basyx::objectMap_t&>()["entity"];
};

}
}
}
}