/*
 * Socket.h
 *
 *  Created on: 06.11.2018
 *      Author: schnicke
 */

#ifndef ABSTRACTION_SOCKET_IMPL_H_
#define ABSTRACTION_SOCKET_IMPL_H_

#include "impl/system_net_types.h"

#include <string>


//#include <unistd.h>
//#include <cstdlib>
//#include <iostream>

//#include <process.h>
//#include "backends/protocols/provider/basyx/winsock_fix.h"


// Forward declarations

namespace basyx {
	namespace net {
		namespace impl {

			class socket_impl
			{
			private:
				native_socket_type SocketDesc;
			public:
				socket_impl() : SocketDesc{ 0 } {};
				explicit socket_impl(native_socket_type socket) : SocketDesc{ socket } {};
				~socket_impl();
			public:
				int connect(std::string const&, std::string const&);
				int read(void*, size_t);
				int recv(void *, size_t, int);
				int write(void*, size_t);

				int shutdown(enum SocketShutdownDir how);
				int close();

				int getErrorCode();
			};
		}
	}
}
#endif /* ABSTRACTION_SOCKET_IMPL_H_ */
