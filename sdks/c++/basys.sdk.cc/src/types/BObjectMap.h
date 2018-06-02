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


	// Access members
	public:
		std::map<std::string, BRef<BType>> *elements() {return &mapElements;}

};



#endif /* TYPES_BOBJECTMAP_H_ */
