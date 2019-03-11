/*
 * BaSyxNativeProvider.h
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#ifndef BACKENDS_PROTOCOLS_PROVIDER_BASYX_BASYXNATIVEPROVIDER_H_
#define BACKENDS_PROTOCOLS_PROVIDER_BASYX_BASYXNATIVEPROVIDER_H_

#include "backends/protocols/provider/basyx/winsock_fix.h"
#include "backends/protocols/provider/basyx/frame/BaSyxNativeFrameProcessor.h"
#include "backends/protocols/provider/basyx/CoderTools.h"
#include "backends/protocols/basyx/BaSyx.h"

#define DEFAULT_BUF_SIZE 1024


// Uncomment the following line to print receiving and sending frames
//#define PRINT_FRAME


/**
 * Provies access based on the basyx native protocol
 */
class BaSyxNativeProvider {

public:
	BaSyxNativeProvider(SOCKET ClientSocket,
			BaSyxNativeFrameProcessor* frameProcessor) :
			ClientSocket(ClientSocket), frameProcessor(frameProcessor), closed(
					false) {
	}

	/**
	 * Has to be called periodically
	 */
	void update() {
		if (!closed) {
			int iResult = recv(ClientSocket, recvbuf, DEFAULT_BUF_SIZE, 0);
			if (iResult == 0) {
				std::cout << "BaSyxNativeProvider# Connection closed" << std::endl;
				// TODO: Explicit closing needed?
				closed = true;
			} else if (iResult < 0) {
				std::cout << "BaSyxNativeProvider# recv failed: " << WSAGetLastError() << std::endl;
				closesocket(ClientSocket);
				WSACleanup();
				closed = true;
			} else {
#ifdef PRINT_FRAME
				std::cout << "BaSyxNativeProvider# Received: "  << std::endl;
				BaSyxNativeFrameHelper::printFrame(recvbuf, iResult);
#endif
				size_t txSize = 0;

				frameProcessor->processInputFrame(recvbuf + BASYX_FRAMESIZE_SIZE, iResult - BASYX_FRAMESIZE_SIZE,
						ret + BASYX_FRAMESIZE_SIZE, &txSize);

				// Encode txSize
				txSize += BASYX_FRAMESIZE_SIZE;
				CoderTools::setInt32(ret, 0, txSize);

				int iSendResult = send(ClientSocket, ret, txSize, 0);

				if (iSendResult == SOCKET_ERROR) {
					std::cout << "BaSyxNativeProvider# send failed: " << WSAGetLastError()
							<< std::endl;
					closesocket(ClientSocket);
					WSACleanup();
					closed = true;
				}

			}
		}
	}

	bool isClosed() {
		return closed;
	}

private:
	SOCKET ClientSocket;
	char recvbuf[DEFAULT_BUF_SIZE];
	char ret[DEFAULT_BUF_SIZE];
	BaSyxNativeFrameProcessor* frameProcessor;
	bool closed;
};

#endif /* BACKENDS_PROTOCOLS_PROVIDER_BASYX_BASYXNATIVEPROVIDER_H_ */
