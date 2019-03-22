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
#include <unordered_map>

// BaSyx includes
#include "BType.h"
#include "ref/BRef.h"



/* *********************************************************************************
 * BaSyx object map
 * *********************************************************************************/

class BObjectMap : public BType {
public:
	using object_map_t = std::unordered_map<std::string, BRef<BType>>;

	// Carried values
	protected:
		object_map_t mapElements;


	// Constructor
	public:
		BObjectMap() : BType(BASYS_MAP) {}


	// Public interface
	public:
		// Access members
		object_map_t * elements() {return &mapElements;}

		// Get collection size
		std::size_t size() {return mapElements.size();}
};



#endif /* TYPES_BOBJECTMAP_H_ */
