#define WIN32_LEAN_AND_MEAN

#include <WinSock2.h>
#include <WS2tcpip.h>
#include <iostream>
#include <string>

#include "JSONTools.h"


#define DEFAULT_PORT "27015"
#define DEFAULT_BUFFER_LENGTH	512

class Client {
public:
	Client(char* servername)
	{
		szServerName = servername;
		ConnectSocket = INVALID_SOCKET;
	}

	bool Start() {
		WSADATA wsaData;

		// Initialize Winsock
		int iResult = WSAStartup(MAKEWORD(2,2), &wsaData);
		if(iResult != 0)
		{
			printf("WSAStartup failed: %d\n", iResult);
			return false;
		}

		struct addrinfo	*result = NULL,
						*ptr = NULL,
						hints;

		ZeroMemory(&hints, sizeof(hints));

		hints.ai_family = AF_UNSPEC; //the address family specification. We usually use AF_INET which is for IPv4 format. For IPv6 format you have to use AF_INET6.
		hints.ai_socktype = SOCK_STREAM; //  SOCK_STREAM opens a connection between two distant computers and allows them to communicate: this protocol is called TCP . SOCK_DGRAM, which doesn't open any connection between the computers, but send immediately the message to the ip and port number specified: this protocol is called UDP
		hints.ai_protocol = IPPROTO_TCP; // The protocol to be used. The possible options for the protocol parameter are specific to the address family and socket type specified.

		// Resolve the server address and port
		iResult = getaddrinfo(szServerName, DEFAULT_PORT, &hints, &result);
		if (iResult != 0)
		{
			printf("getaddrinfo failed: %d\n", iResult);
			WSACleanup();
			return false;
		}

		ptr = result;

		std::cout<<"Address  family: " << ptr->ai_family << ", protocol to be used " << ptr->ai_protocol << " ";

		// Create a SOCKET for connecting to server
		ConnectSocket = socket(ptr->ai_family, ptr->ai_socktype, ptr->ai_protocol);

		if (ConnectSocket == INVALID_SOCKET)
		{
			printf("Error at socket(): %d\n", WSAGetLastError());
			freeaddrinfo(result);
			WSACleanup();
			return false;
		}

		// Connect to server
		// 1. server socket, 2. socket address information, 3. size of socket address information ( of the second parameter)
		iResult = connect(ConnectSocket, ptr->ai_addr, (int)ptr->ai_addrlen);

		if (iResult == SOCKET_ERROR)
		{
			closesocket(ConnectSocket);
			ConnectSocket = INVALID_SOCKET;
		}

		freeaddrinfo(result);

		if (ConnectSocket == INVALID_SOCKET)
		{
			printf("Unable to connect to server!\n");
			WSACleanup();
			return false;
		}

		return true;
	};

	// Free the resouces
	void Stop() {
		int iResult = shutdown(ConnectSocket, SD_SEND);

		if (iResult == SOCKET_ERROR)
		{
			printf("shutdown failed: %d\n", WSAGetLastError());
		}

		closesocket(ConnectSocket);
		WSACleanup();
	};

	// Send message to server
	bool Send(char* szMsg)
	{

		//1. server socket
		//2. the data
		//3. the size of the data
		int iResult = send(ConnectSocket, szMsg, strlen(szMsg), 0);

		if (iResult == SOCKET_ERROR)
		{
			printf("send failed: %d\n", WSAGetLastError());
			Stop();
			return false;
		}

		return true;
	};

	// Receive message from server
	bool Recv()
	{
		char recvbuf[DEFAULT_BUFFER_LENGTH];
		int iResult = recv(ConnectSocket, recvbuf, DEFAULT_BUFFER_LENGTH, 0);

		if (iResult > 0)
		{
			char msg[DEFAULT_BUFFER_LENGTH];
			memset(&msg, 0, sizeof(msg));
			strncpy(msg, recvbuf, iResult);

			printf("Received: %s\n", msg);

			return true;
		}


		return false;
	}

private:
	char* szServerName;
	SOCKET ConnectSocket;
};


int main(int argc, CHAR* argv[])
{

	std::string msg;
	JSONTools json_tools;
	//modular client
	Client client("127.0.0.1");

	if (!client.Start())
		return 1;

	//Client sends plain text command to the server.
	std::string s = "aas.iese.fraunhofer.de/PartModel.Property1";
	client.Send((char*)s.c_str());
	client.Recv();

	client.Stop();

	getchar();
	return 0;
}
