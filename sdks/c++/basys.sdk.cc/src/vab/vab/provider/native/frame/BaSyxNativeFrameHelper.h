/*
 * BaSyxNativeGetFrame.h
 *
 *  Created on: 08.08.2018
 *      Author: schnicke
 */

#ifndef VAB_VAB_PROVIDER_BASYX_FRAME_BASYXNATIVEFRAMEHELPER_H
#define VAB_VAB_PROVIDER_BASYX_FRAME_BASYXNATIVEFRAMEHELPER_H


#include "basyx/types.h"

#include "util/tools/StringTools.h"

#include <iostream>
#include <sstream>
#include <string>


namespace basyx {
namespace vab {
namespace provider {
namespace native {
namespace frame {


    class BaSyxNativeFrameHelper {
    public:
        /**
	 * Retrieves a string from an array
	 * The assumed array format is:
	 * 	repeated:
	 * 		4 byte string size
	 * 		N byte string data
	 */
    static std::string getString(char const* data, std::size_t num)
    {
        for (std::size_t i = 0; i < num; i++) {
            std::size_t stringSize = CoderTools::getInt32(data, 0);

            // Increment data pointer to skip string size value and the string itself
            data += BASYX_STRINGSIZE_SIZE + stringSize;
        }

        return StringTools::fromArray(data);
    }

    static std::string printFrame(char const* data, size_t size)
    {
        std::stringstream output;

        output << CoderTools::getInt32(data, 0) << " " << (int)data[4];
        data += 5;
        size -= 5;

        // Iterate over the array to find all strings
        while (size > 0) {
            output << " ";
            std::string str = StringTools::fromArray(data);
            data += str.length() + BASYX_STRINGSIZE_SIZE;
            size -= str.length() + BASYX_STRINGSIZE_SIZE;
            output << "\n"
                   << str;
        }
        output << std::endl;
        return output.str();
    }

    /**
* Retrieves the command from a basyx frame and writes the size of the command in commandSize
*/
    static int getCommand(char const* data, std::size_t* commandSize)
    {
        *commandSize = 1;
        return data[0];
    }
};

}
}
}
}
}

#endif /* VAB_VAB_PROVIDER_BASYX_FRAME_BASYXNATIVEFRAMEHELPER_H */
