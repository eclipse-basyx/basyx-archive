/*
 * BaSyxNativeProvider.h
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#ifndef BACKENDS_PROTOCOLS_PROVIDER_BASYX_BASYXNATIVEPROVIDER_H_
#define BACKENDS_PROTOCOLS_PROVIDER_BASYX_BASYXNATIVEPROVIDER_H_

//#include "backends/protocols/provider/basyx/winsock_fix.h"

#include "backends/protocols/basyx/BaSyx.h"
#include "backends/protocols/provider/basyx/CoderTools.h"
#include "backends/protocols/provider/basyx/frame/BaSyxNativeFrameProcessor.h"

//#include "BaSyxSocket.h"

#include "abstraction/Net.h"

//#define DEFAULT_BUF_SIZE 1024

// Uncomment the following line to print receiving and sending frames
//#define PRINT_FRAME

/**
 * Provies access based on the basyx native protocol
 */
class BaSyxNativeProvider {
private:
	static constexpr std::size_t default_buffer_size = 1024;
    basyx::net::tcp::Socket& clientSocket;
	std::array<char, default_buffer_size> recv_buffer;
	std::array<char, default_buffer_size> ret;
	//    char recvbuf[DEFAULT_BUF_SIZE];
//    char ret[DEFAULT_BUF_SIZE];
	// ToDo: Ownership?
    BaSyxNativeFrameProcessor * frameProcessor;
    bool closed;

public:
    BaSyxNativeProvider(basyx::net::tcp::Socket& clientSocket,
        BaSyxNativeFrameProcessor* frameProcessor)
        : clientSocket(clientSocket)
        , frameProcessor(frameProcessor)
        , closed(false)
    {
    }

    ~BaSyxNativeProvider()
    {
		// Connection no longer needed, close it
		this->clientSocket.Close();
    }

    /**
	 * Has to be called periodically
	 */
    void update()
    {
		if (!closed)
		{
			std::size_t bytes_read = this->clientSocket.Receive(recv_buffer);
			if (bytes_read == 0 || !this->clientSocket.Healthy()) {
				std::cout << "BaSyxNativeProvider# Connection closed" << std::endl;
				closed = true;
			}
			else if (bytes_read < 0) {
				std::cout << "BaSyxNativeProvider# recv failed: " << this->clientSocket.GetErrorCode() << std::endl;
				closed = true;
			}  else {
#ifdef PRINT_FRAME
                std::cout << "BaSyxNativeProvider# Received: " << std::endl;
                BaSyxNativeFrameHelper::printFrame(recv_buffer.data(), bytes_read);
#endif
                std::size_t txSize = 0;

                frameProcessor->processInputFrame(recv_buffer.data() + BASYX_FRAMESIZE_SIZE, bytes_read - BASYX_FRAMESIZE_SIZE,
                    ret.data() + BASYX_FRAMESIZE_SIZE, &txSize);

                // Encode txSize
                txSize += BASYX_FRAMESIZE_SIZE;
                CoderTools::setInt32(ret.data(), 0, txSize);

				std::size_t bytes_sent = this->clientSocket.Send(basyx::net::make_buffer(ret.data(), txSize));

                if (bytes_sent < 0) {
                    std::cout << "BaSyxNativeProvider# send failed: " << this->clientSocket.GetErrorCode() << std::endl;
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

#endif /* BACKENDS_PROTOCOLS_PROVIDER_BASYX_BASYXNATIVEPROVIDER_H_ */