/*
 * winsock_fix.h
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#ifndef BACKENDS_PROTOCOLS_PROVIDER_BASYX_WINSOCK_FIX_H_
#define BACKENDS_PROTOCOLS_PROVIDER_BASYX_WINSOCK_FIX_H_

#include <windows.h>
#include <WinSock2.h>
#include <WS2tcpip.h>

#ifdef __cplusplus
extern "C" {
#endif
   void WSAAPI freeaddrinfo( struct addrinfo* );

   int WSAAPI getaddrinfo( const char*, const char*, const struct addrinfo*,
                 struct addrinfo** );

   int WSAAPI getnameinfo( const struct sockaddr*, socklen_t, char*, DWORD,
                char*, DWORD, int );
#ifdef __cplusplus
}
#endif




#endif /* BACKENDS_PROTOCOLS_PROVIDER_BASYX_WINSOCK_FIX_H_ */
