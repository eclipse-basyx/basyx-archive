/*
 * BObjectMap.h
 *
 *      Author: kuhn
 */

#ifndef TYPES_BOBJECTMAP_H_
#define TYPES_BOBJECTMAP_H_


/* *********************************************************************************
 * Includes
 * *********************************************************************************/

// Stc C++ includes
#include <map>

// BaSyx includes
#include "BType.h"
#include "ref/BRef.h"



/* *********************************************************************************
 * BaSyx object map
 * *********************************************************************************/

class BObjectMap : public BType {


	// Carried values
	protected:
		std::map<std::string, BRef<BType>> mapElements;


	// Constructor
	public:
		BObjectMap() : BType() {baSysTypeID = BASYS_MAP;}


	// Public interface
	public:
		// Access members
		std::map<std::string, BRef<BType>> *elements() {return &mapElements;}

		// Get collection size
		int size() {return mapElements.size();}

};



#endif /* TYPES_BOBJECTMAP_H_ */
