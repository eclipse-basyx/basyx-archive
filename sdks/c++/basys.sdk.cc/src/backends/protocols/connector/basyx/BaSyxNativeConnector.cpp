/*
 * NativeConnector.cpp
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#include "backends/protocols/connector/basyx/BaSyxNativeConnector.h"
#include "backends/protocols/provider/basyx/frame/BaSyxNativeFrameHelper.h"

#include <json/json.h>

#include <stdio.h>


// #include <WS2tcpip.h>

// Uncomment the following line to print receiving and sending frames
//#define PRINT_FRAME

// TODO: In all basys_X methods: Error Handling!

//static const size_t DEFAULT_BUFFER_LENGTH = 1024;

namespace basyx {
	namespace connector {

NativeConnector::NativeConnector(std::string const& address, int port)
	: builder{}
	, socket{basyx::net::tcp::Socket::Connect(address, port)}
	, log{ "NativeConnector" }
{
	log.trace("Connected to {}:{}",address,port);
}



NativeConnector::~NativeConnector() {
	this->socket.Close();
}



basyx::any NativeConnector::basysGet(std::string const& path) 
{
	return basyx::json::deserialize(basysGetRaw(path));
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

void NativeConnector::sendData(char* msg, size_t size) {
	CoderTools::setInt32(msg, 0, size);
	size += BASYX_FRAMESIZE_SIZE;
#ifdef PRINT_FRAME
	log.debug("Sending:\n{}", BaSyxNativeFrameHelper::printFrame(msg, size));
#endif

	int iResult = this->socket.Send(basyx::net::make_buffer(msg, size));

	if (iResult < 0) {
		log.error("Send failed! Error code: {}", this->socket.GetErrorCode());
	}
}

// TODO: Error handling
size_t NativeConnector::receiveData(char* data) {
	// recv(data, DEFAULT_BUFFER_LENGTH, 0);
	int iResult = this->socket.Receive(basyx::net::make_buffer(data, default_buffer_length));
	
	if (iResult > 0) {
#ifdef PRINT_FRAME
		log.debug("Received:\n{}", BaSyxNativeFrameHelper::printFrame(data, iResult));
#endif
		return iResult;
	} else {
		std::cout << "NativeConnector# ReceiveData failed" << std::endl;
		log.error("Receive failed! Error code: {}", iResult);
		return 0;
	}
}

basyx::any NativeConnector::decode(char* buffer) 
{
	std::string data = StringTools::fromArray(buffer);
	return basyx::json::deserialize(data);
};

}}