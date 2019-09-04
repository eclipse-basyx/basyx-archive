/*
 * BaSyxNativeProvider.h
 *
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#ifndef VAB_VAB_PROVIDER_BASYX_FRAME_BASYXNATIVEFRAMEPROCESSOR_H
#define VAB_VAB_PROVIDER_BASYX_FRAME_BASYXNATIVEFRAMEPROCESSOR_H


#include <string>

#include "vab/backend/connector/JSONProvider.h"
#include "vab/provider/native/frame/BaSyxNativeFrameHelper.h"
#include "vab/core/IModelProvider.h"

#include "basyx/serialization/json.h"
 
namespace basyx {
namespace vab {
namespace provider {
namespace native {
namespace frame {

class BaSyxNativeFrameProcessor {

public:
	BaSyxNativeFrameProcessor(vab::core::IModelProvider* providerBackend);
	~BaSyxNativeFrameProcessor();


	/**
	 * Processes a rxFrame and performs the encoded command
	 *
	 * The following structure is assumed:
	 * 1 byte command
	 * x byte depending on command
	 */
	void processInputFrame(char const* rxFrame, std::size_t rxSize, char* txFrame, std::size_t* txSize);
private:
	JSONProvider<vab::core::IModelProvider> jsonProvider;

	void processGet(char const* rxFrame, char* txFrame, std::size_t* txSize);
	void processSet(char const* rxFrame, char* txFrame, std::size_t* txSize);
	void processCreate(char const* rxFrame, char* txFrame, std::size_t* txSize);
	void processDelete(char const* rxFrame, std::size_t rxSize, char* txFrame, std::size_t* txSize);
	void processInvoke(char const* rxFrame, char* txFrame, std::size_t* txSize);
};


}
}
}
}
}

#endif /* VAB_VAB_PROVIDER_BASYX_FRAME_BASYXNATIVEFRAMEPROCESSOR_H */
