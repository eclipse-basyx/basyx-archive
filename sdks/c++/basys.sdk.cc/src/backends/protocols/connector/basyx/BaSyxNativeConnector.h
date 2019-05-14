/*
 * BaSyxNativeConnector.h
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#ifndef BACKENDS_PROTOCOLS_CONNECTOR_BASYX_BASYXNATIVECONNECTOR_H_
#define BACKENDS_PROTOCOLS_CONNECTOR_BASYX_BASYXNATIVECONNECTOR_H_

#include "backends/protocols/connector/IBaSysConnector.h"
#include "backends/protocols/connector/basyx/frame/BaSyxNativeFrameBuilder.h"

#include "abstraction/Net.h"

#include <memory>

namespace basyx {
	namespace connector {

		class NativeConnector : public basyx::connector::IBaSysConnector {
		public:
			static constexpr std::size_t default_buffer_length = 1024;
		public:
			NativeConnector(std::string const& address, int port);

			virtual ~NativeConnector();
		public:
			virtual basyx::any basysGet(std::string const& path) override;

			virtual nlohmann::json basysGetRaw(std::string const& path) override;

			virtual void basysSet(std::string const& path, const basyx::any & newValue) override;

			virtual void basysCreate(std::string const& servicePath, const basyx::any & val)
				override;

			virtual basyx::any basysInvoke(std::string const& servicePath, const basyx::any & param)
				override;

			virtual void basysDelete(std::string const& servicePath) override;

			virtual void basysDelete(std::string const& servicePath, const basyx::any & obj) override;

		private:
			basyx::net::tcp::Socket socket;
			BaSyxNativeFrameBuilder builder;
			std::array<char, default_buffer_length> buffer;

			void sendData(char* data, size_t size);

			size_t receiveData(char* data);
			basyx::any decode(char* buffer);
			basyx::log log;
		};

	}
};

#endif /* BACKENDS_PROTOCOLS_CONNECTOR_BASYX_BASYXNATIVECONNECTOR_H_ */