/*
* BaSyxNativeConnector.h
*
*  Created on: 14.08.2018
*      Author: schnicke
*/

#ifndef VAB_VAB_BACKEND_CONNECTOR_NATIVE_BASYXCONNECTOR_H
#define VAB_VAB_BACKEND_CONNECTOR_NATIVE_BASYXCONNECTOR_H

#include <BaSyx/vab/backend/connector/IBaSyxConnector.h>
#include <BaSyx/vab/backend/connector/native/frame/Frame.h>

#include <BaSyx/abstraction/Net.h>

#include <memory>

namespace basyx {
namespace vab {
namespace connector {
namespace native {

    class NativeConnector : public IBaSyxConnector {
    public:
        static constexpr std::size_t default_buffer_length = 4096;
	private:
		basyx::net::tcp::Socket socket;
		std::array<char, default_buffer_length> buffer;
	private:
		basyx::log log;
	public:
        NativeConnector(std::string const& address, int port);
        virtual ~NativeConnector();
    public:
        virtual basyx::object basysGet(std::string const& path) override;
        virtual basyx::object basysSet(std::string const& path, const basyx::object& newValue) override;
        virtual basyx::object basysCreate(std::string const& servicePath, const basyx::object& val) override;
        virtual basyx::object basysInvoke(std::string const& servicePath, const basyx::object& param) override;
        virtual basyx::object basysDelete(std::string const& servicePath) override;
        virtual basyx::object basysDelete(std::string const& servicePath, const ::basyx::object& obj) override;
	protected:
		virtual basyx::object basysProcess(const Frame & frame);
	protected:
		void sendFrame(const Frame & frame);
		Frame recvFrame();
	private:
		void sendData(char* data, size_t size);
		size_t receiveData(char* data);
    };
}
}
}
};

#endif /* VAB_VAB_BACKEND_CONNECTOR_NATIVE_BASYXCONNECTOR_H */
