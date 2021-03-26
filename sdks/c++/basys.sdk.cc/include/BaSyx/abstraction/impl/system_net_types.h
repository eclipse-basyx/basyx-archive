#ifndef BASYX_ABSTRACTION_IMPL_SYSTEM_NET_TYPES_H
#define BASYX_ABSTRACTION_IMPL_SYSTEM_NET_TYPES_H

#ifdef _WIN32
	#if _WIN32_WINNT <= 0x501
		#define _WIN32_WINNT 0x501
	#endif

	#include <winsock2.h>
	#include <ws2tcpip.h>

	using native_socket_type = ::SOCKET;

	enum SocketShutdownDir {
		SHUTDOWN_RD = SD_RECEIVE,
		SHUTDOWN_WR = SD_SEND,
		SHUTDOWN_RDWR = SD_BOTH
	};

#else // UNIX
	#include <sys/types.h>
	#include <sys/socket.h>
	#include <netdb.h>

	using native_socket_type = int;

	enum SocketShutdownDir {
		SHUTDOWN_RD = SHUT_RD,
		SHUTDOWN_WR = SHUT_WR,
		SHUTDOWN_RDWR = SHUT_RDWR
	};
#endif

#endif /* BASYX_ABSTRACTION_IMPL_SYSTEM_NET_TYPES_H */
