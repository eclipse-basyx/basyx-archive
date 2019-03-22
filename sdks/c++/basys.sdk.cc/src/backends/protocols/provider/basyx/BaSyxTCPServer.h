/*
 * BaSyxTCPServer.h
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#ifndef BACKENDS_PROTOCOLS_PROVIDER_BASYX_BASYXTCPSERVER_H_
#define BACKENDS_PROTOCOLS_PROVIDER_BASYX_BASYXTCPSERVER_H_

#include <iostream>
#include <vector>

#include "abstraction/Net.h"
#include "abstraction/Thread.h"

#include "backends/protocols/provider/basyx/BaSyxNativeProvider.h"
#include "json/JSONTools.h"

template <typename T>
class BaSyxTCPServer {
private:
	T* backend;

	basyx::net::tcp::Acceptor acceptor;
	std::vector<basyx::thread> threads;
	std::vector<basyx::net::tcp::Socket> sockets;

	JSONTools* jsonTools;
	volatile bool running;
public:
	// ToDo: Ownership of backend?
	BaSyxTCPServer(T* backend, int port, JSONTools* jsonTools)
		: backend(backend)
		, jsonTools(jsonTools)
		, running(true)
		, acceptor{ port }
    {
		// ToDo: Check health of acceptor
    }

	void Close()
	{
		if (!running)
			return;

		running = false;

		// Close the acceptor socket
		acceptor.close();

		// Close all accepted connections
		// This will bring all open connection threads to a finish 
		for(auto & socket : sockets)
			socket.Close();

		// Wait for all threads to finish
		for(auto & thread : threads)
			thread.join();


		// ToDo: Check for errors during cleanup
	}

    ~BaSyxTCPServer()
    {
		this->Close();
    }

    /**
	 * Has to be called periodically
	 */
    void update()
    {
        if (isRunning()) {	
            std::cout << "BaSyxTCPServer# accepting new connections: " << std::endl;

            auto ClientSocket = this->acceptor.accept();
			
			if (!ClientSocket.Healthy()) {
				std::cout << "BaSyxTCPServer# incoming connection failed: " << std::endl;
				return;
			}

			std::cout << "BaSyxTCPServer# incoming new connection: " << std::endl;
			sockets.emplace_back(std::move(ClientSocket));

			basyx::thread handlerThread{ &BaSyxTCPServer<T>::processConnection, this, std::ref(sockets.back()) };
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
	void processConnection(basyx::net::tcp::Socket & socket)
	{
		BaSyxNativeFrameProcessor processor{ this->backend, jsonTools };
		BaSyxNativeProvider provider{socket, &processor};

		while (!provider.isClosed()) {
			provider.update();
		}
	}
};

#endif /* BACKENDS_PROTOCOLS_PROVIDER_BASYX_BASYXTCPSERVER_H_ */
