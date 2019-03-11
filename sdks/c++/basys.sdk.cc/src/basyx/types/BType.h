/*
 * BObject.h
 *
 *      Author: kuhn
 */

#ifndef TYPES_BTYPE_H_
#define TYPES_BTYPE_H_


/* *********************************************************************************
 * Includes
 * *********************************************************************************/

// BaSyx
#include "BaSysTypes.h"

// C includes
#include <stdio.h>

// StdC++ includes
#include <map>




/* *********************************************************************************
 * BaSyx object
 * *********************************************************************************/

class BType {

	// Runtime type information
	protected:
		int baSysTypeID;


	// Constructor
	protected:
		BType() {baSysTypeID = BASYS_NULL;}


	// Destructor
	public:
		~BType() { /* Do nothing */ }


	// Public member functions
	public:
		// Get type
		int getType() {return baSysTypeID;}


		// Check if this is a null type
		bool isNull() {if (baSysTypeID==0) return true; return false;}

		// Check if this is a value type
		bool isValue() {if ((baSysTypeID>0) && (baSysTypeID<10)) return true; return false;}

		// Check if this is an array type
		bool isArray() {if ((baSysTypeID>=10) && (baSysTypeID<=19)) return true; return false;}

		// Check if this is a map type
		bool isMap() {if (baSysTypeID==BASYS_MAP) return true; return false;}

		// Check if this is a collection type
		bool isCollection() {if (baSysTypeID==BASYS_COLLECTION) return true; return false;}

		// Check if this is an IElement type
		bool isIElement() {if (baSysTypeID==BASYS_IELEMENT) return true; return false;}

		// Check if this is an Object type
		bool isObject() {if (baSysTypeID==BASYS_OBJECT) return true; return false;}
};


#endif /* TYPES_BTYPE_H_ */
