/*
 * BaSyxNativeProvider.h
 *
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#ifndef BACKENDS_PROTOCOLS_BASYX_BASYXNATIVEPROVIDER_H_
#define BACKENDS_PROTOCOLS_BASYX_BASYXNATIVEPROVIDER_H_


#include <string>

#include "api/IModelProvider.h"
#include "json/JSONTools.h"
#include "backends/provider/json/JSONProvider.h"
#include "backends/protocols/provider/basyx/frame/BaSyxNativeFrameHelper.h"

class BaSyxNativeFrameProcessor {

public:
	BaSyxNativeFrameProcessor(IModelProvider* providerBackend, JSONTools* jsonTools);
	~BaSyxNativeFrameProcessor();


	/**
	 * Processes a rxFrame and performs the encoded command
	 *
	 * The following structure is assumed:
	 * 1 byte command
	 * x byte depending on command
	 */
	void processInputFrame(char const* rxFrame, std::size_t rxSize, char* txFrame,
			std::size_t* txSize);
private:
	JSONTools* jsonTools;
	JSONProvider<IModelProvider>* jsonProvider;


	void processGet(char const* rxFrame, char* txFrame, std::size_t* txSize);
	void processSet(char const* rxFrame, char* txFrame, std::size_t* txSize);
	void processCreate(char const* rxFrame, char* txFrame, std::size_t* txSize);
	void processDelete(char const* rxFrame, std::size_t rxSize, char* txFrame, std::size_t* txSize);
	void processInvoke(char const* rxFrame, char* txFrame, std::size_t* txSize);
};


#endif /* BACKENDS_PROTOCOLS_BASYX_BASYXNATIVEPROVIDER_H_ */
