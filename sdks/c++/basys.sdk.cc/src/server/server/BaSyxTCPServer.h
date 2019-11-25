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

#include "log/log.h"

#include <asio.hpp>

#include "BaSyxNativeProvider.h"
#include "vab/provider/native/frame/BaSyxNativeFrameProcessor.h"

namespace basyx {
namespace vab {
namespace provider {
namespace native {

	template <typename T>
	class TCPServer {
	private:
		T* backend;

		//basyx::net::tcp::Acceptor acceptor;

		asio::io_context io_context;
	//	asio::ip::tcp::endpoint endpoint;
		asio::ip::tcp::acceptor acceptor;

		std::vector<basyx::thread> threads;
		std::vector<std::unique_ptr<asio::ip::tcp::socket>> sockets;

		bool closed;
		std::atomic_bool running;

		basyx::log log;
		
	public:
		// ToDo: Ownership of backend?
		TCPServer(T* backend, int port)
			: backend{ backend }
			, running{ true }
		//	, endpoint{ asio::ip::tcp::v4(), port }
			, acceptor{ io_context, asio::ip::tcp::endpoint(asio::ip::tcp::v4(), port) }
			, log{ "TCPServer" }
		{
			// ToDo: Check health of acceptor
			log.info("Starting server on port {}", port);
			acceptor.listen();
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
				socket->close();

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

				auto ClientSocket = util::make_unique<asio::ip::tcp::socket>(io_context);
				this->acceptor.accept(*ClientSocket.get());

				//auto error = WSAGetLastError();

				if (!ClientSocket->is_open()) {
					log.warn("Incoming connection failed");
					return;
				}

				log.info("Incoming new connection");
				sockets.emplace_back(std::move(ClientSocket));

				basyx::thread handlerThread{ &TCPServer<T>::processConnection, this, std::ref(*sockets.back()) };
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
		void processConnection(asio::ip::tcp::socket & socket)
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