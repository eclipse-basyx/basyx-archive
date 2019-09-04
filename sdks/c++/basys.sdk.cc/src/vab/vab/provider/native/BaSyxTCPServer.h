/*
* BaSyxTCPServer.h
*
*  Created on: 14.08.2018
*      Author: schnicke
*/

#ifndef VAB_VAB_PROVIDER_NATIVE_BASYXTCPSERVER_H
#define VAB_VAB_PROVIDER_NATIVE_BASYXTCPSERVER_H

#include <atomic>
#include <iostream>
#include <vector>

#include "abstraction/Net.h"
#include "abstraction/Thread.h"

#include "log/log.h"

#include "vab/provider/native/BaSyxNativeProvider.h"
#include "vab/provider/native/frame/BaSyxNativeFrameProcessor.h"

namespace basyx {
namespace vab {
namespace provider {
namespace native {

	template <typename T>
	class TCPServer {
	private:
		T* backend;

		basyx::net::tcp::Acceptor acceptor;
		std::vector<basyx::thread> threads;
		std::vector<basyx::net::tcp::Socket> sockets;

		bool closed;
		std::atomic_bool running;

		basyx::log log;

	public:
		// ToDo: Ownership of backend?
		TCPServer(T* backend, int port)
			: backend{ backend }
			, running{ true }
			, acceptor{ port }
			, log{ "TCPServer" }
		{
			// ToDo: Check health of acceptor
			log.info("Starting server on port {}", port);
		}

		void Close()
		{
			log.trace("Closing...");

			if (!isRunning())
				return;

			running.store(true);

			// Close the acceptor socket
			log.trace("Closing Acceptor...");
			acceptor.close();

			// Close all accepted connections
			// This will bring all open connection threads to a finish
			log.trace("Closing open connections...");
			for (auto& socket : sockets)
				socket.Close();

			// Wait for all threads to finish
			for (auto& thread : threads)
				thread.join();

			// ToDo: Check for errors during cleanup
		}

		~TCPServer()
		{
			this->Close();
		}

		/**
		* Has to be called periodically
		*/
		void update()
		{
			if (isRunning()) {
				log.info("Accepting new connections.");

				auto ClientSocket = this->acceptor.accept();

				if (!ClientSocket.Healthy()) {
					log.warn("Incoming connection failed");
					return;
				}

				log.info("Incoming new connection");
				sockets.emplace_back(std::move(ClientSocket));

				basyx::thread handlerThread{ &TCPServer<T>::processConnection, this, std::ref(sockets.back()) };
				threads.emplace_back(std::move(handlerThread));
			}
		}

		bool isRunning()
		{
			return running;
		}

	private:
		/**
	* Handles a BaSyxNativeProvider
	*/
		void processConnection(basyx::net::tcp::Socket& socket)
		{
			log.trace("Processing new connection");

			vab::provider::native::frame::BaSyxNativeFrameProcessor processor{ this->backend };
			vab::provider::native::NativeProvider provider{ socket, &processor };

			while (!provider.isClosed()) {
				provider.update();
			}
		}
	};
};
};
};
};

#endif /* VAB_VAB_PROVIDER_NATIVE_BASYXTCPSERVER_H */
