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
#include "backends/protocols/provider/basyx/winsock_fix.h"



class BaSyxNativeConnector: public IBaSysConnector {
public:
	virtual ~BaSyxNativeConnector();

	BaSyxNativeConnector(std::string const& address, std::string const& port,
			BaSyxNativeFrameBuilder* builder, JSONTools* jsonTools);

	virtual BRef<BType> basysGet(std::string const& path) override;

	virtual nlohmann::json basysGetRaw(std::string const& path) override;

	virtual void basysSet(std::string const& path, BRef<BType> newValue) override;

	virtual void basysCreate(std::string const& servicePath, BRef<BType> val)
			override;

	virtual BRef<BType> basysInvoke(std::string const& servicePath, BRef<BType> param)
			override;

	virtual void basysDelete(std::string const& servicePath) override;

	virtual void basysDelete(std::string const& servicePath, BRef<BType> obj) override;

private:
	SOCKET listenSocket;
	BaSyxNativeFrameBuilder* builder;
	char* buffer;
	void sendData(char* data, size_t size);
	size_t receiveData(char* data);
	JSONTools* jsonTools;

	BRef<BType> decode(char* buffer);
};

#endif /* BACKENDS_PROTOCOLS_CONNECTOR_BASYX_BASYXNATIVECONNECTOR_H_ */
