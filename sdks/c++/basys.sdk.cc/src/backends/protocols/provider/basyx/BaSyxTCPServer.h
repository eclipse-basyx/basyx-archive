/*
 * BaSyxTCPServer.h
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#ifndef BACKENDS_PROTOCOLS_PROVIDER_BASYX_BASYXTCPSERVER_H_
#define BACKENDS_PROTOCOLS_PROVIDER_BASYX_BASYXTCPSERVER_H_

#include <iostream>

#include <process.h>

#include <WS2tcpip.h>
#include "backends/protocols/provider/basyx/winsock_fix.h"
#include "backends/protocols/provider/basyx/BaSyxNativeProvider.h"
#include "json/JSONTools.h"
#include <vector>

template<typename T>
class BaSyxTCPServer {
public:
	BaSyxTCPServer(T* backend, std::string const& port, JSONTools* jsonTools) :
			backend(backend), jsonTools(jsonTools), running(true) {
		WSADATA wsaData;

		// Initialize Winsock
		int iResult = WSAStartup(MAKEWORD(2, 2), &wsaData);
		if (iResult != 0) {
			std::cout << "BaSyxTCPServer# WSAStartup failed: " << iResult
					<< std::endl;
		}

		struct addrinfo *result = NULL, hints;

		ZeroMemory(&hints, sizeof(hints));
		hints.ai_family = AF_INET; // Internet address family is unspecified so that either an IPv6 or IPv4 address can be returned
		hints.ai_socktype = SOCK_STREAM; // Requests the socket type to be a stream socket for the TCP protocol
		hints.ai_protocol = IPPROTO_TCP;
		hints.ai_flags = AI_PASSIVE;

		// Resolve the local address and port to be used by the server
		iResult = getaddrinfo(NULL, port.c_str(), &hints, &result);
		if (iResult != 0) {
			std::cout << "BaSyxTCPServer# getaddrinfo failed: " << iResult
					<< std::endl;
			WSACleanup();
			return;
		}

		ListenSocket = INVALID_SOCKET;

		// Create a SOCKET for the server to listen for client connections
		ListenSocket = socket(result->ai_family, result->ai_socktype,
				result->ai_protocol);

		if (ListenSocket == INVALID_SOCKET) {
			std::cout << "BaSyxTCPServer# Error at socket(): "
					<< WSAGetLastError() << std::endl;
			freeaddrinfo(result);
			WSACleanup();
			return;
		}

		// Setup the TCP listening socket
		//bind the socket with the IP address and the port number.  In a way it is like the connect() function (the parameters are the same)
		iResult = bind(ListenSocket, result->ai_addr, (int) result->ai_addrlen);

		if (iResult == SOCKET_ERROR) {
			std::cout << "BaSyxTCPServer# bind failed:" << WSAGetLastError()
					<< std::endl;
			freeaddrinfo(result);
			closesocket(ListenSocket);
			WSACleanup();
			return;
		}

		freeaddrinfo(result);

		// To listen on a socket
		// starts listening to allow clients to connect.
		if (listen(ListenSocket, SOMAXCONN) == SOCKET_ERROR) {
			std::cout << "BaSyxTCPServer# listen failed: " << WSAGetLastError()
					<< std::endl;
			closesocket(ListenSocket);
			WSACleanup();
			return;
		}

		std::cout << "BaSyxTCPServer# TCP Server Listening!" << std::endl;
	}

	~BaSyxTCPServer() {
		running = false;
		int iResult = shutdown(ListenSocket, SD_SEND);

		if (iResult == SOCKET_ERROR) {
			std::cout << "BaSyxTCPServer# shutdown failed: "
					<< WSAGetLastError() << std::endl;
		}

		// Close all threads
		std::for_each(threads.begin(), threads.end(),
				[](HANDLE &h) {CloseHandle(h);});

		closesocket(ListenSocket);
		WSACleanup();
	}

	/**
	 * Has to be called periodically
	 */
	void update() {
		if (isRunning()) {
			SOCKET ClientSocket = INVALID_SOCKET;

			// Accept a client socket
			// The accept function permits an incoming connection attempt on a socket.
			ClientSocket = accept(ListenSocket, NULL, NULL);
			if (ClientSocket == INVALID_SOCKET) {
				std::cout << "BaSyxTCPServer# accept failed: "
						<< WSAGetLastError() << std::endl;
				closesocket(ListenSocket);
				WSACleanup();
				running = false;
			} else {
				std::cout
						<< "BaSyxTCPServer# TCP Server accepted new connection"
						<< std::endl;
				unsigned threadID;

				BaSyxNativeFrameProcessor* processor =
						new BaSyxNativeFrameProcessor(backend, jsonTools);
				BaSyxNativeProvider* provider = new BaSyxNativeProvider(
						ClientSocket, processor);
				
				// Pack the relevant parameters into a std::pair to be able to pass it to the new thread
				std::pair<BaSyxNativeFrameProcessor*, BaSyxNativeProvider*> pair(
						processor, provider);
				
				// Create the new thread and store the handle for later termination
				// Uses _beginthreadex for thread creation since the used mingw does not have an implementation of std::thread
				HANDLE handle = reinterpret_cast<HANDLE>(_beginthreadex(NULL, 0,
						&process, &pair, 0, &threadID));
				threads.push_back(handle);
			}
		}
	}

	bool isRunning() {
		return running;
	}

private:
	T* backend;
	SOCKET ListenSocket;
	JSONTools* jsonTools;
	bool running;
	std::vector<HANDLE> threads;
	
	
	/**
	 * Handles a BaSyxNativeProvider 
	 */
	static unsigned int __stdcall process(void* pArguments) {
		// Unpack the passed std::pair
		std::pair<BaSyxNativeFrameProcessor*, BaSyxNativeProvider*>* pair =
				reinterpret_cast<std::pair<BaSyxNativeFrameProcessor*,
						BaSyxNativeProvider*>*>(pArguments);
		BaSyxNativeFrameProcessor* processor = pair->first;
		BaSyxNativeProvider* provider = pair->second;

		while (!provider->isClosed()) {
			provider->update();
		}
		delete provider;
		delete processor;

		return 0;
	}
};

#endif /* BACKENDS_PROTOCOLS_PROVIDER_BASYX_BASYXTCPSERVER_H_ */
