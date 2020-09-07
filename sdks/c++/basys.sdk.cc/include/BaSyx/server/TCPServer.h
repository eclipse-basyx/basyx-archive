#ifndef BASYX_SERVER_TCPSERVER_H
#define BASYX_SERVER_TCPSERVER_H

#include <atomic>
#include <iostream>
#include <vector>

#include <BaSyx/log/log.h>

#include <asio.hpp>

#include <BaSyx/server/BaSyxNativeProvider.h>
#include <BaSyx/vab/provider/native/frame/BaSyxNativeFrameProcessor.h>

namespace basyx {
namespace server {

template <typename Backend>
class TCPServer {
public:
    using socket_ptr_t = std::unique_ptr<asio::ip::tcp::socket>;

private:
    Backend* backend;

    asio::io_context io_context;
    asio::ip::tcp::acceptor acceptor;

    std::vector<std::thread> threads;
    std::vector<socket_ptr_t> sockets;

    bool closed;
    std::atomic_bool running;

    basyx::log log;

public:
    TCPServer(Backend* backend, int port)
        : backend { backend }
        , running { true }
        , io_context { 0 }
        , acceptor { io_context, asio::ip::tcp::endpoint(asio::ip::tcp::v4(), port) }
        , log { "TCPServer" }
    {
        // ToDo: Check health of acceptor
        log.info("Starting server on port {}", port);
        //			acceptor.listen();
        start_accept();
    }

    void run()
    {
        this->io_context.run();
    };

    void stop()
    {
        this->io_context.stop();
    };

    void start_accept()
    {
        asio::error_code ec;
        auto client_socket = util::make_unique<asio::ip::tcp::socket>(io_context);
        //this->acceptor.accept(*client_socket.get(), ec);

        //auto error = WSAGetLastError();

        //if (!client_socket->is_open()) {
        //	log.warn("Incoming connection failed");
        //	return;
        //}
        sockets.emplace_back(std::move(client_socket));

        acceptor.async_accept(*sockets.back(),
            std::bind(&TCPServer::handle_accept, this,
                std::placeholders::_1));
    };

    void handle_accept(
        const asio::error_code& error)
    {
        if (!error) {
            log.info("Incoming new connection");

            std::thread handlerThread { &TCPServer<Backend>::processConnection, this, std::ref(*sockets.back()) };
            threads.emplace_back(std::move(handlerThread));
        } else {
            sockets.pop_back();
        }

        start_accept();
    };

    void Close()
    {
        log.trace("Closing...");

        if (!isRunning())
            return;

        running.store(false);
        this->stop();

        //// Close the acceptor socket
        //log.trace("Closing Acceptor...");
        //acceptor.close();

        // Close all accepted connections
        // This will bring all open connection threads to a finish
        log.trace("Closing open connections...");
        for (auto& socket : sockets) {
            try {
                if (socket->is_open())
                    socket->close();
            } catch (std::exception& e) {
                log.warn("Socket closed unexpectedly.");
            }
        };

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

            std::thread handlerThread { &TCPServer<Backend>::processConnection, this, std::ref(*sockets.back()) };
            threads.emplace_back(std::move(handlerThread));
        }
    }

    bool isRunning()
    {
        return running.load();
    }

private:
    /**
* Handles a BaSyxNativeProvider
*/
    void processConnection(asio::ip::tcp::socket& socket)
    {
        log.trace("Processing new connection");

        vab::provider::native::frame::BaSyxNativeFrameProcessor processor { this->backend };
        vab::provider::native::NativeProvider provider { socket, &processor };

        while (!provider.isClosed()) {
            provider.update();
        }
    }
};
};
};

#endif /* BASYX_SERVER_TCPSERVER_H */
