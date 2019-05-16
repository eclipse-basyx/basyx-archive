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

/**
 * Provides support methods for building native basyx frames 
 */
class BaSyxNativeFrameBuilder {
public:
	BaSyxNativeFrameBuilder();
	
	size_t buildGetFrame(std::string const& path, char* buffer);
	
	size_t buildSetFrame(std::string const& path, const basyx::any & newVal, char* buffer);
	
	size_t buildCreateFrame(std::string const& path, const basyx::any & newVal, char* buffer);
	
	size_t buildDeleteFrame(std::string const& path, char* buffer);
	
	size_t buildDeleteFrame(std::string const& path, const basyx::any & deleteVal, char* buffer);
	
	size_t buildInvokeFrame(std::string const& path, const basyx::any & param, char* buffer);
private:
	size_t encodeCommand(BaSyxCommand command, char* buffer);
	
	size_t encodeCommandAndPath(BaSyxCommand command, std::string const& path, char* buffer);

	std::size_t encodeValue(const basyx::any & value, char * buffer);
};



#endif /* BACKENDS_PROTOCOLS_CONNECTOR_BASYX_BASYXNATIVEFRAMEBUILDER_H_ */
