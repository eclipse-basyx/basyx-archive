/*
 * BaSyxNativeFrameBuilder.h
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#ifndef BACKENDS_PROTOCOLS_CONNECTOR_BASYX_BASYXNATIVEFRAMEBUILDER_H_
#define BACKENDS_PROTOCOLS_CONNECTOR_BASYX_BASYXNATIVEFRAMEBUILDER_H_

#include <string>

#include "backends/protocols/basyx/BaSyx.h"
#include "backends/protocols/provider/basyx/frame/BaSyxNativeFrameProcessor.h"
#include "types/BType.h"
#include "ref/BRef.h"

/**
 * Provides support methods for building native basyx frames 
 */
class BaSyxNativeFrameBuilder {
public:
	BaSyxNativeFrameBuilder(JSONTools* jsonTools);
	
	size_t buildGetFrame(std::string const& path, char* buffer);
	
	size_t buildSetFrame(std::string const& path, BRef<BType> newVal, char* buffer);
	
	size_t buildCreateFrame(std::string const& path, BRef<BType> newVal, char* buffer);
	
	size_t buildDeleteFrame(std::string const& path, char* buffer);
	
	size_t buildDeleteFrame(std::string const& path, BRef<BType> deleteVal, char* buffer);
	
	size_t buildInvokeFrame(std::string const& path, BRef<BType> param, char* buffer);
	
private:
	JSONTools* jsonTools;
	
	size_t encodeCommand(BaSyxCommand command, char* buffer);
	
	size_t encodeBRef(BRef<BType> val, char* buffer);
	
	size_t encodeCommandAndPath(BaSyxCommand command, std::string const& path, char* buffer);
};



#endif /* BACKENDS_PROTOCOLS_CONNECTOR_BASYX_BASYXNATIVEFRAMEBUILDER_H_ */
