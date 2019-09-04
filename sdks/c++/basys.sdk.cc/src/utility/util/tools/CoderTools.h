/*
 * CoderTools.h
 *
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#ifndef UTILITY_UTIL_TOOLS_CODERTOOLS_H
#define UTILITY_UTIL_TOOLS_CODERTOOLS_H


#include <stdlib.h>
#include <stdint.h>

/**
 * Partial port of CoderTools.java initially written by Thomas Kuhn
 */
class CoderTools {

public:
	/**
	 * Get a uint32 from a byte array with offset, MSB is first bit
	 */
	static uint32_t getInt32(char const*  data, std::size_t offset) {
		uint32_t result = 0;

		result += data[offset+3] & 0xFF; result = result << 8;
		result += data[offset+2] & 0xFF; result = result << 8;
		result += data[offset+1] & 0xFF; result = result << 8;
		result += data[offset+0] & 0xFF;
		return result;
	}

	/**
	 * Copy an uint32 to a byte array at given offset, MSB is first bit
	 */
	static char* setInt32(char* data, std::size_t offset, uint32_t value) {
		data[offset+3] = (char) ((value >> 24) & 0xFF);
		data[offset+2] = (char) ((value >> 16) & 0xFF);
		data[offset+1] = (char) ((value >>  8) & 0xFF);
		data[offset+0] = (char) (value         & 0xFF);
		return data;
	}
};


#endif /* UTILITY_UTIL_TOOLS_CODERTOOLS_H */
