/*
 * BaSyxNativeProvider.h
 *
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#ifndef VAB_VAB_PROVIDER_BASYX_FRAME_BASYXNATIVEFRAMEPROCESSOR_H
#define VAB_VAB_PROVIDER_BASYX_FRAME_BASYXNATIVEFRAMEPROCESSOR_H


#include <string>

#include <BaSyx/vab/backend/connector/JSONProvider.h>
#include <BaSyx/vab/backend/connector/native/frame/Frame.h>
#include <BaSyx/vab/provider/native/frame/BaSyxNativeFrameHelper.h>
#include <BaSyx/vab/core/IModelProvider.h>

#include <BaSyx/shared/serialization/json.h>
 
namespace basyx {
namespace vab {
namespace provider {
namespace native {
namespace frame {

class BaSyxNativeFrameProcessor 
{
private:
	JSONProvider<vab::core::IModelProvider> jsonProvider;
private:
	connector::native::Frame processGet(const connector::native::Frame & frame);
	connector::native::Frame processSet(const connector::native::Frame & frame);
	connector::native::Frame processCreate(const connector::native::Frame & frame);
	connector::native::Frame processDelete(const connector::native::Frame & frame);
	connector::native::Frame processInvoke(const connector::native::Frame & frame);
public:
	BaSyxNativeFrameProcessor(vab::core::IModelProvider* providerBackend);
	~BaSyxNativeFrameProcessor() = default;
public:
	connector::native::Frame processInputFrame(const connector::native::Frame & frame);
};


}
}
}
}
}

#endif /* VAB_VAB_PROVIDER_BASYX_FRAME_BASYXNATIVEFRAMEPROCESSOR_H */
