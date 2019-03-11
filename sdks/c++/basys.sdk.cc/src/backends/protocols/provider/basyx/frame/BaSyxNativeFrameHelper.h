/*
 * BaSyxNativeGetFrame.h
 *
 *  Created on: 08.08.2018
 *      Author: schnicke
 */

#ifndef BACKENDS_PROTOCOLS_BASYX_FRAME_BASYXNATIVEFRAMEHELPER_H_
#define BACKENDS_PROTOCOLS_BASYX_FRAME_BASYXNATIVEFRAMEHELPER_H_

#include <string>
#include <iostream>
#include "backends/protocols/provider/basyx/StringTools.h"
#include "backends/protocols/basyx/BaSyx.h"

class BaSyxNativeFrameHelper {
public:
	/**
	 * Retrieves a string from an array
	 * The assumed array format is:
	 * 	repeated:
	 * 		4 byte string size
	 * 		N byte string data
	 */
	static std::string getString(char const* data, std::size_t num) {
		for (std::size_t i = 0; i < num; i++) {
			std::size_t stringSize = CoderTools::getInt32(data, 0);

			// Increment data pointer to skip string size value and the string itself
			data += BASYX_STRINGSIZE_SIZE + stringSize;
		}

		return StringTools::fromArray(data);
	}
	
	static void printFrame(char const* data, size_t size) {
		std::cout << CoderTools::getInt32(data, 0) << " " << (int) data[4];
		data += 5;
		size -= 5;
		
		// Iterate over the array to find all strings
		while (size > 0) {
			std::cout << " ";
			std::string str = StringTools::fromArray(data);
			data += str.length() + BASYX_STRINGSIZE_SIZE;
			size -= str.length() + BASYX_STRINGSIZE_SIZE;
			std::cout << str;
		}
		std::cout << std::endl;
	}
	
	/**
	 * Retrieves the command from a basyx frame and writes the size of the command in commandSize
	 */
	static int getCommand(char const* data, std::size_t* commandSize) {
		*commandSize = 1;
		return data[0];
	}
};

#endif /* BACKENDS_PROTOCOLS_BASYX_FRAME_BASYXNATIVEFRAMEHELPER_H_ */
