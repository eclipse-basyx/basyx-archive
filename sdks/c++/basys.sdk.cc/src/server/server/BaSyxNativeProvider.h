/*
 * BaSyxNativeProvider.h
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#ifndef VAB_VAB_PROVIDER_NATIVE_BASYXNATIVEPROVIDER_H
#define VAB_VAB_PROVIDER_NATIVE_BASYXNATIVEPROVIDER_H

#include "basyx/types.h"

#include "util/tools/CoderTools.h"

#include "vab/provider/native/frame/BaSyxNativeFrameProcessor.h"

#include <log/log.h>

#include <asio.hpp>

//#define DEFAULT_BUF_SIZE 1024


/**
 * Provies access based on the basyx native protocol
 */

namespace basyx {
namespace vab {
namespace provider {
namespace native {

	class NativeProvider {
	private:
		static constexpr std::size_t default_buffer_size = 1024;
		asio::ip::tcp::socket & clientSocket;
		std::array<char, default_buffer_size> recv_buffer;
		std::array<char, default_buffer_size> ret;
		//    char recvbuf[DEFAULT_BUF_SIZE];
	//    char ret[DEFAULT_BUF_SIZE];
		// ToDo: Ownership?

		frame::BaSyxNativeFrameProcessor * frameProcessor;
		bool closed;
		basyx::log log;
	public:
		NativeProvider(asio::ip::tcp::socket & clientSocket,
			frame::BaSyxNativeFrameProcessor* frameProcessor)
			: clientSocket(clientSocket)
			, frameProcessor(frameProcessor)
			, closed(false)
			, log{ "NativeProvider" }
		{
		}

		~NativeProvider()
		{
			// Connection no longer needed, close it
			this->clientSocket.close();
		}

		/**
			* Has to be called periodically
			*/
		void update()
		{
			log.trace("Updating...");
			if (!closed)
			{
				auto huh = clientSocket.is_open();

				asio::error_code ec;
				log.trace("Waiting for incoming message");
				std::size_t bytes_read = this->clientSocket.receive(asio::buffer(recv_buffer.data(), recv_buffer.size()),0, ec);
				log.debug("Received {} bytes.", bytes_read);

				if(ec == asio::error::eof) {
//				if (bytes_read == 0 || !this->clientSocket.is_open()) {
					log.info("Connection closed");
					closed = true;
				}
				else if (bytes_read < 0) {
					log.error("Receive failed!");
					closed = true;
				}
				else {
					log.trace("Received frame.");
#ifdef PRINT_FRAME
					log.debug("Received:");
					vab::provider::native::frame::BaSyxNativeFrameHelper::printFrame(recv_buffer.data(), bytes_read);
#endif
					std::size_t txSize = 0;

					frameProcessor->processInputFrame(
						recv_buffer.data() + BASYX_FRAMESIZE_SIZE, 
						bytes_read - BASYX_FRAMESIZE_SIZE,
						ret.data() + BASYX_FRAMESIZE_SIZE, 
						&txSize);

					// Encode txSize
					CoderTools::setInt32(ret.data(), 0, txSize);
					txSize += BASYX_FRAMESIZE_SIZE;

					log.info("Sending reply.");
#ifdef PRINT_FRAME
					log.debug("Sending:");
					vab::provider::native::frame::BaSyxNativeFrameHelper::printFrame(ret.data(), txSize);
#endif
					log.debug("Sending {} bytes.", txSize);
					std::size_t bytes_sent = this->clientSocket.send(asio::buffer(ret.data(), txSize));
					log.debug("Sent {} bytes.", bytes_sent);

					if (bytes_sent < 0) {
						log.error("Sending failed: {}", "ERROR");
						closed = true;
					}
				}
			}
		}

		bool isClosed()
		{
			return closed;
		}
	};

};
};
};
};

#endif /* VAB_VAB_PROVIDER_NATIVE_BASYXNATIVEPROVIDER_H */
