/*
 * Socket.h
 *
 *  Created on: 06.11.2018
 *      Author: schnicke
 */

#ifndef ABSTRACTION_ACCEPTOR_IMPL_H_
#define ABSTRACTION_ACCEPTOR_IMPL_H_

#include <cstdlib>

#include <memory>
#include <string>

#include "abstraction/impl/system_net_types.h"

#include <log/log.h>

namespace basyx {
	namespace net {
		namespace impl {

			// Forward declarations
			class socket_impl;

			class acceptor_impl 
			{
			public:
				acceptor_impl()
					: log{ "AcceptorImpl" } {};
				~acceptor_impl();
			public:
				int listen(const std::string & port);

				std::unique_ptr<socket_impl> accept();
				int shutdown(enum SocketShutdownDir how);
				int close();

				int getErrorCode();
			private:
				native_socket_type socketDesc = 0;
				basyx::log log;
			};


		}
	}
}

#endif /* ABSTRACTION_ACCEPTOR_IMPL_H_ */
