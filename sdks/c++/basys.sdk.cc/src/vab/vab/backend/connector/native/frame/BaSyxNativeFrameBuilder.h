/*
 * BaSyxNativeFrameBuilder.h
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#ifndef VAB_VAB_BACKEND_CONNECTOR_NATIVE_FRAME_BASYXNATIVEFRAMEBUILDER_H
#define VAB_VAB_BACKEND_CONNECTOR_NATIVE_FRAME_BASYXNATIVEFRAMEBUILDER_H

#include <string>



#include <basyx/types.h>
#include <basyx/any.h>

//#include <vab/provider/basyx/frame/BaSyxNativeFrameProcessor.h>


namespace basyx {
namespace vab {
namespace connector {
namespace native {
namespace frame {

    /**
     * Provides support methods for building native basyx frames 
     */
    class BaSyxNativeFrameBuilder {
    public:
        BaSyxNativeFrameBuilder();

        size_t buildGetFrame(std::string const& path, char* buffer);

        size_t buildSetFrame(std::string const& path, const basyx::any& newVal, char* buffer);

        size_t buildCreateFrame(std::string const& path, const basyx::any& newVal, char* buffer);

        size_t buildDeleteFrame(std::string const& path, char* buffer);

        size_t buildDeleteFrame(std::string const& path, const basyx::any& deleteVal, char* buffer);

        size_t buildInvokeFrame(std::string const& path, const basyx::any& param, char* buffer);
		size_t buildInvokeFrame(std::string const& path, const basyx::objectCollection_t & params, char * buffer);
    private:
        size_t encodeCommand(BaSyxCommand command, char* buffer);

        size_t encodeCommandAndPath(BaSyxCommand command, std::string const& path, char* buffer);

        std::size_t encodeValue(const ::basyx::any& value, char* buffer);
    };


}
}
}
}
}


#endif /* VAB_VAB_BACKEND_CONNECTOR_NATIVE_FRAME_BASYXNATIVEFRAMEBUILDER_H */
