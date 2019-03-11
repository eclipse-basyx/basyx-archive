/*
 * StringTools.h
 *
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#ifndef BACKENDS_PROTOCOLS_BASYX_STRINGTOOLS_H_
#define BACKENDS_PROTOCOLS_BASYX_STRINGTOOLS_H_

#include <string>
#include "CoderTools.h"

class StringTools {
public:
	/**
	 * Assumes the following layout:
	 * 4 byte size
	 * $size byte string content, not \0 terminated
	 */
	static std::string fromArray(char const* array) {
		size_t size = CoderTools::getInt32(array, 0);
		array += 4;
		return std::string(array, size);
	}

	/**
	 * Copies the content of str into target buffer without \0 termination
	 * Assumes that target is big enough to carry the content of str
	 */
	static std::size_t toArray(std::string const& str, char* target) {
		CoderTools::setInt32(target, 0, str.length());
		target += 4;
		std::memcpy(target, str.c_str(), str.length());
		return str.length() + 4;
	}
};

#endif /* BACKENDS_PROTOCOLS_BASYX_STRINGTOOLS_H_ */
