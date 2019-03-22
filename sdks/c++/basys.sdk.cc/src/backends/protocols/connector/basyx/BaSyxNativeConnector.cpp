/*
 * BaSyxNativeConnector.cpp
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#include "backends/protocols/connector/basyx/BaSyxNativeConnector.h"
#include "backends/protocols/provider/basyx/frame/BaSyxNativeFrameHelper.h"
#include <stdio.h>


// #include <WS2tcpip.h>

// Uncomment the following line to print receiving and sending frames
//#define PRINT_FRAME

// TODO: In all basys_X methods: Error Handling!

//static const size_t DEFAULT_BUFFER_LENGTH = 1024;

BaSyxNativeConnector::BaSyxNativeConnector(
	std::string const& address,
	int port, 
	JSONTools* jsonTools)
	: builder{jsonTools}
//	, buffer(new char[DEFAULT_BUFFER_LENGTH])
	, jsonTools{jsonTools}
	, socket{}
{
//	int iRetval;

	this->socket = basyx::net::tcp::Socket::Connect(address, port);

	int x = 2;
//	this->ConnectorSocket = new basyx::abstraction::TCPSocket();
//	iRetval = this->ConnectorSocket->connect(address.c_str(), port.c_str());

	//if (!iRetval) {
	//	std::cout << "BaSyxNativeConnector# Connected!" << std::endl;
	//}
}



BaSyxNativeConnector::~BaSyxNativeConnector() {
	this->socket.Close();
	//int iResult = this->ConnectorSocket->shutdown(basyx::abstraction::SHUTDOWN_RDWR);

	//if (iResult < 0)
	//{
	//	std::cout << "BaSyxNativeConnector# shutdown() failed: " << this->ConnectorSocket->getErrorCode()
	//			<< std::endl;
	//}

	//iResult = this->ConnectorSocket->close();
	//if (iResult < 0)
	//{
	//	std::cout << "BaSyxNativeConnector# close() failed: " << this->ConnectorSocket->getErrorCode()
	//			<< std::endl;
	//	}

	//delete buffer;
}



BRef<BType> BaSyxNativeConnector::basysGet(std::string const& path) {
	return jsonTools->deserialize(basysGetRaw(path), 0,
			BaSysID::getScopeString(path));
}

nlohmann::json BaSyxNativeConnector::basysGetRaw(std::string const& path) {
	size_t size = builder.buildGetFrame(path, buffer.data() + BASYX_FRAMESIZE_SIZE);
	sendData(buffer.data(), size);
	size = receiveData(buffer.data());
	if (buffer[4] != 0) { // Error happened
		return ""_json; // TODO: Error handling
	}
	std::string data = StringTools::fromArray(buffer.data() + BASYX_FRAMESIZE_SIZE + 1);
	return nlohmann::json::parse(data);
}

void BaSyxNativeConnector::basysSet(std::string const& path,
		BRef<BType> newValue) {
	size_t size = builder.buildSetFrame(path, newValue, buffer.data() + BASYX_FRAMESIZE_SIZE);
	sendData(buffer.data(), size);
	size = receiveData(buffer.data());
}

void BaSyxNativeConnector::basysCreate(std::string const& path,
		BRef<BType> val) {
	size_t size = builder.buildCreateFrame(path, val, buffer.data() + BASYX_FRAMESIZE_SIZE);
	sendData(buffer.data(), size);
	size = receiveData(buffer.data());
}

BRef<BType> BaSyxNativeConnector::basysInvoke(std::string const& path,
		BRef<BType> param) {
	size_t size = builder.buildInvokeFrame(path, param, buffer.data() + BASYX_FRAMESIZE_SIZE);
	sendData(buffer.data(), size);
	size = receiveData(buffer.data());
	return decode(buffer.data() + 5);
}

void BaSyxNativeConnector::basysDelete(std::string const& path) {
	size_t size = builder.buildDeleteFrame(path, buffer.data() + BASYX_FRAMESIZE_SIZE);
	sendData(buffer.data(), size);
	size = receiveData(buffer.data());
}

void BaSyxNativeConnector::basysDelete(std::string const& path,
		BRef<BType> obj) {
	size_t size = builder.buildDeleteFrame(path, obj, buffer.data() + BASYX_FRAMESIZE_SIZE);
	sendData(buffer.data(), size);
	size = receiveData(buffer.data());
}

// TODO: Error handling
/**
 * Builds a send frame and sends it to server
 * @param msg a frame constructed with the BaSyxNativeFrameBuilder
 */

void BaSyxNativeConnector::sendData(char* msg, size_t size) {
	size += BASYX_FRAMESIZE_SIZE;
	CoderTools::setInt32(msg, 0, size);
#ifdef PRINT_FRAME
	BaSyxNativeFrameHelper::printFrame(msg, size);
#endif

	int iResult = this->socket.Send(basyx::net::make_buffer(msg, size));

	if (iResult < 0) {
		std::cout << "BaSyxNativeConnector# Send failed: " << this->socket.GetErrorCode()
				<< std::endl;
	}
}

// TODO: Error handling
size_t BaSyxNativeConnector::receiveData(char* data) {
	// recv(data, DEFAULT_BUFFER_LENGTH, 0);
	int iResult = this->socket.Receive(basyx::net::make_buffer(data, default_buffer_length));
	
	if (iResult > 0) {
#ifdef PRINT_FRAME
		std::cout << "BaSyxNativeConnector# Received: " << std::endl;
		BaSyxNativeFrameHelper::printFrame(data, iResult);
#endif
		return iResult;
	} else {
		std::cout << "BaSyxNativeConnector# ReceiveData failed" << std::endl;
		return 0;
	}
}

BRef<BType> BaSyxNativeConnector::decode(char* buffer) {
	std::string data = StringTools::fromArray(buffer);
	BRef<BType> val = jsonTools->deserialize(json::parse(data), 0, ""); // TODO: Scope?
	return val;
}

