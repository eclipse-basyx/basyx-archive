#define _WIN32_WINNT 0x0501

#include <WS2tcpip.h>
#include <stdio.h>
#include <stdlib.h>
#include <string>

//http://www.askyb.com/windows-socket/windows-socket-example-tcp-client-and-server/

#include "winsock_fix.h"

#define DEFAULT_PORT "27015"
#define DEFAULT_BUFFER_LENGTH	512

#include "regression/support/aas/ExampleAAS1.h"
#include "backends/provider/cxx/CXXModelProvider.h"
#include <iostream>
#include "src/json/JSONTools.h"

int main() {
	setbuf(stdout, NULL);

	WSADATA wsaData;

	// Initialize Winsock
	int iResult = WSAStartup(MAKEWORD(2,2), &wsaData);
	if(iResult != 0)
	{
		printf("WSAStartup failed: %d\n", iResult);
		return 1;
	}

	struct addrinfo	*result = NULL,
					hints;

	ZeroMemory(&hints, sizeof(hints));
	hints.ai_family = AF_INET;		// Internet address family is unspecified so that either an IPv6 or IPv4 address can be returned
	hints.ai_socktype = SOCK_STREAM;	// Requests the socket type to be a stream socket for the TCP protocol
	hints.ai_protocol = IPPROTO_TCP;
	hints.ai_flags = AI_PASSIVE;

	// Resolve the local address and port to be used by the server
	iResult = getaddrinfo(NULL, DEFAULT_PORT, &hints, &result);
	if (iResult != 0)
	{
		printf("getaddrinfo failed: %d\n", iResult);
		WSACleanup();
		return 1;
	}

	SOCKET ListenSocket = INVALID_SOCKET;

	// Create a SOCKET for the server to listen for client connections
	ListenSocket = socket(result->ai_family, result->ai_socktype, result->ai_protocol);

	if (ListenSocket == INVALID_SOCKET)
	{
		printf("Error at socket(): %d\n", WSAGetLastError());
		freeaddrinfo(result);
		WSACleanup();
		return 1;
	}

	// Setup the TCP listening socket
	//bind the socket with the IP address and the port number.  In a way it is like the connect() function (the parameters are the same)
	iResult = bind(ListenSocket, result->ai_addr, (int)result->ai_addrlen);

	if (iResult == SOCKET_ERROR)
	{
		printf("bind failed: %d", WSAGetLastError());
		freeaddrinfo(result);
		closesocket(ListenSocket);
		WSACleanup();
		return 1;
	}

	freeaddrinfo(result);

	// To listen on a socket
	// starts listening to allow clients to connect.
	if ( listen(ListenSocket, SOMAXCONN) == SOCKET_ERROR)
	{
		printf("listen failed: %d\n", WSAGetLastError());
		closesocket(ListenSocket);
		WSACleanup();
		return 1;
	}


	ExampleAAS1 *ex1AAS = new ExampleAAS1("aas1", "ExampleAAS1");
	ExampleAAS1 *ex2AAS = new ExampleAAS1("aas2", "ExampleAAS1");


		// Instantiate AAS provider
	CXXModelProvider *aasProvider = new CXXModelProvider();
		// - Attach AAS to provider
	std::string provider = "iese.fraunhofer.de";
	aasProvider->attach(ex1AAS, "iese.fraunhofer.de");
	aasProvider->attach(ex2AAS, "iese.fraunhofer.de");

	JSONTools* jTools = new JSONTools();


	printf("Startup finished, waiting for connection\n");

	SOCKET ClientSocket;

	ClientSocket = INVALID_SOCKET;

	// Accept a client socket
	// The accept function permits an incoming connection attempt on a socket.
	ClientSocket = accept(ListenSocket, NULL, NULL);

	if (ClientSocket == INVALID_SOCKET)
	{
		printf("accept failed: %d\n", WSAGetLastError());
		closesocket(ListenSocket);
		WSACleanup();
		return 1;
	}

	char recvbuf[DEFAULT_BUFFER_LENGTH];
	int iSendResult;




	// reveice until the client shutdown the connection
	do {
		iResult = recv(ClientSocket, recvbuf, DEFAULT_BUFFER_LENGTH, 0);
		if (iResult > 0)
		{
			char msg[DEFAULT_BUFFER_LENGTH];
			memset(&msg, 0, sizeof(msg));
			strncpy(msg, recvbuf, iResult);

			printf("Received: %s\n", msg);

			//split the string in order to retrieve the propertyName and the Scope

			std::string str = std::string(msg);

			std::cout << "Received: " << str << std::endl;
			std::string scope = aasProvider->getElementScope(str);
			printf("3a :%s\n", scope.c_str());
			if(scope != "iese.fraunhofer.de") {
				std::cout << "Unknown scope: " << scope << std::endl;
			}

			//(Scope is before the property name)
			//check if the scope is registered

			//retrieve the property value //??? From where??
			//serialize property value in JSON,
			//send the value to the client

			BRef<BValue> type = aasProvider->getModelPropertyValue(str);

			std::cout << "Type: " << type->getType() << std::endl;
			std::cout << "Value: " << type->getInt() << std::endl;

			json json = jTools->serialize(type, 0, scope);
			std::cout << "Responding: " << json.dump() << std::endl;
			std::string toSend = json.dump();
			iSendResult = send(ClientSocket, toSend.c_str(), toSend.size(), 0);

			if (iSendResult == SOCKET_ERROR)
			{
				printf("send failed: %d\n", WSAGetLastError());
				closesocket(ClientSocket);
				WSACleanup();
				return 1;
			}

			printf("Bytes sent: %i\n", iSendResult);
			return 0;
		}
		else if (iResult == 0)
			printf("Connection closed\n");
		else
		{
			printf("recv failed: %d\n", WSAGetLastError());
			closesocket(ClientSocket);
			WSACleanup();
			//return 1;
		}
	} while (iResult > 0);

	// Free the resouces
	closesocket(ListenSocket);
	WSACleanup();

	getchar();
	return 0;
}
