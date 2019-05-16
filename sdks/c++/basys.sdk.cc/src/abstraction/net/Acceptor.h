/*
 * Acceptor.h
 *
 *  Created on: 05.02.2018
 *      Author: psota
 */

#ifndef ABSTRACTION_NET_ACCEPTOR_H_
#define ABSTRACTION_NET_ACCEPTOR_H_

#include <memory>
#include <log/log.h>

#include "Socket.h"

namespace basyx {
	namespace net {
		
		// Forward declarations
		namespace impl {
			class acceptor_impl;
		}

		namespace tcp {

			class Acceptor
			{
			private:
				std::unique_ptr<basyx::net::impl::acceptor_impl> acceptor;
				basyx::log log;
				Acceptor & _move_acceptor(Acceptor && other);
			public:
				explicit Acceptor(int port);
				explicit Acceptor(const std::string & port);

				// Delete copy-assignment and constructor, there should only ever be one Acceptor object representing a single acceptor
				Acceptor(const Acceptor & other) = delete;
				Acceptor& operator=(const Acceptor & other) = delete;

				// Allow move-operations
				Acceptor & operator=(Acceptor && other);
				Acceptor(Acceptor && other);

				~Acceptor();
			public:
				void close();

				// Blockingly wait for connection
				Socket accept();
			};
		}
	}
}


#endif