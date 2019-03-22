/*
 * BObject.h
 *
 *      Author: kuhn
 */

#ifndef TYPES_BSTRING_H_
#define TYPES_BSTRING_H_


/* *********************************************************************************
 * Includes
 * *********************************************************************************/

// BaSyx
#include "BaSysTypes.h"
#include "BType.h"

// Std C++ library
#include <string>




/* *********************************************************************************
 * BaSyx value object
 * *********************************************************************************/

class BString : public BType {

	// Carried value
	protected:
		// Store string value
		std::string stringValue;


	// Constructor
	public:
		BString(const std::string & value) 
			: BType(BASYS_STRING)
			, stringValue{ value }
		{};


	// Public member functions
	public:
		// Get string value
		std::string getString() {return stringValue;}

		// Set string value
		void setString(const std::string & newVal) {stringValue = newVal;}
};


#endif /* TYPES_BSTRING_H_ */
