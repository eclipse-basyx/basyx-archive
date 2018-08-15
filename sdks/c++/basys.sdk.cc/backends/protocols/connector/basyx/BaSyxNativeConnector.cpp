/*
 * BaSyxNativeConnector.cpp
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#include "backends/protocols/connector/basyx/BaSyxNativeConnector.h"
#include "backends/protocols/provider/basyx/frame/BaSyxNativeFrameHelper.h"
#include <stdio.h>
#include <WS2tcpip.h>

// Uncomment the following line to print receiving and sending frames
//#define PRINT_FRAME

// TODO: In all basys_X methods: Error Handling!

static const size_t DEFAULT_BUFFER_LENGTH = 1024;

BaSyxNativeConnector::BaSyxNativeConnector(std::string const& address,
		std::string const& port, BaSyxNativeFrameBuilder* builder,
		JSONTools* jsonTools) :
		builder(builder), buffer(new char[DEFAULT_BUFFER_LENGTH]), jsonTools(jsonTools) {
	WSADATA wsaData;

	// Initialize Winsock
	int iResult = WSAStartup(MAKEWORD(2, 2), &wsaData);
	if (iResult != 0) {
		std::cout << "BaSyxNativeConnector# WSAStartup failed: " << iResult << std::endl;
		return;
	}

	struct addrinfo *result = NULL, *ptr = NULL, hints;

	ZeroMemory(&hints, sizeof(hints));

	hints.ai_family = AF_UNSPEC; //the address family specification. We usually use AF_INET which is for IPv4 format. For IPv6 format you have to use AF_INET6.
	hints.ai_socktype = SOCK_STREAM; //  SOCK_STREAM opens a connection between two distant computers and allows them to communicate: this protocol is called TCP . SOCK_DGRAM, which doesn't open any connection between the computers, but send immediately the message to the ip and port number specified: this protocol is called UDP
	hints.ai_protocol = IPPROTO_TCP; // The protocol to be used. The possible options for the protocol parameter are specific to the address family and socket type specified.

	// Resolve the server address and port
	iResult = getaddrinfo(address.c_str(), port.c_str(), &hints, &result);
	if (iResult != 0) {
		std::cout << "BaSyxNativeConnector# getaddrinfo failed: " << iResult << std::endl;
		WSACleanup();
		return;
	}

	ptr = result;

	// Create a SOCKET for connecting to server
	listenSocket = socket(ptr->ai_family, ptr->ai_socktype, ptr->ai_protocol);

	if (listenSocket == INVALID_SOCKET) {
		std::cout << "BaSyxNativeConnector# Error at socket(): " << WSAGetLastError() << std::endl;
		freeaddrinfo(result);
		WSACleanup();
		return;
	}

	// Connect to server
	// 1. server socket, 2. socket address information, 3. size of socket address information ( of the second parameter)
	iResult = connect(listenSocket, ptr->ai_addr, (int) ptr->ai_addrlen);

	if (iResult == SOCKET_ERROR) {
		closesocket(listenSocket);
		listenSocket = INVALID_SOCKET;
		return;
	}

	freeaddrinfo(result);

	if (listenSocket == INVALID_SOCKET) {
		std::cout << "BaSyxNativeConnector# Unable to connect to server!" << std::endl;
		WSACleanup();
		return;
	}

	std::cout << "BaSyxNativeConnector# Connected!" << std::endl;
}

BaSyxNativeConnector::~BaSyxNativeConnector() {
	int iResult = shutdown(listenSocket, SD_SEND);

	if (iResult == SOCKET_ERROR)
	{
		std::cout << "BaSyxNativeConnector# shutdown failed: " << WSAGetLastError() << std::endl;
	}

	closesocket(listenSocket);
	WSACleanup();
	delete buffer;
}

BRef<BType> BaSyxNativeConnector::basysGet(std::string const& path) {
	return jsonTools->deserialize(basysGetRaw(path), 0,
			BaSysID::getScopeString(path));
}

nlohmann::json BaSyxNativeConnector::basysGetRaw(std::string const& path) {
	size_t size = builder->buildGetFrame(path, buffer + BASYX_FRAMESIZE_SIZE);
	sendData(buffer, size);
	size = receiveData(buffer);
	if (buffer[4] != 0) { // Error happened
		return ""_json; // TODO: Error handling
	}
	std::string data = StringTools::fromArray(buffer + BASYX_FRAMESIZE_SIZE + 1);
	return nlohmann::json::parse(data);
}

void BaSyxNativeConnector::basysSet(std::string const& path,
		BRef<BType> newValue) {
	size_t size = builder->buildSetFrame(path, newValue, buffer + BASYX_FRAMESIZE_SIZE);
	sendData(buffer, size);
	size = receiveData(buffer);
}

void BaSyxNativeConnector::basysCreate(std::string const& path,
		BRef<BType> val) {
	size_t size = builder->buildCreateFrame(path, val, buffer + BASYX_FRAMESIZE_SIZE);
	sendData(buffer, size);
	size = receiveData(buffer);
}

BRef<BType> BaSyxNativeConnector::basysInvoke(std::string const& path,
		BRef<BType> param) {
	size_t size = builder->buildInvokeFrame(path, param, buffer + BASYX_FRAMESIZE_SIZE);
	sendData(buffer, size);
	size = receiveData(buffer);
	return decode(buffer + 5);
}

void BaSyxNativeConnector::basysDelete(std::string const& path) {
	size_t size = builder->buildDeleteFrame(path, buffer + BASYX_FRAMESIZE_SIZE);
	sendData(buffer, size);
	size = receiveData(buffer);
}

void BaSyxNativeConnector::basysDelete(std::string const& path,
		BRef<BType> obj) {
	size_t size = builder->buildDeleteFrame(path, obj, buffer + BASYX_FRAMESIZE_SIZE);
	sendData(buffer, size);
	size = receiveData(buffer);
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
	int iResult = send(listenSocket, msg, size, 0);

	if (iResult == SOCKET_ERROR) {
		std::cout << "BaSyxNativeConnector# Send failed: " << WSAGetLastError()
				<< std::endl;
	}
}

// TODO: Error handling
size_t BaSyxNativeConnector::receiveData(char* data) {
	int iResult = recv(listenSocket, data, DEFAULT_BUFFER_LENGTH, 0);

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

