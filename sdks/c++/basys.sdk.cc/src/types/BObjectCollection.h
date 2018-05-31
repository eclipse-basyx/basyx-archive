/*
 * BObjectCollection.h
 *
 *      Author: kuhn
 */

#ifndef TYPES_BOBJECTCOLLECTION_H_
#define TYPES_BOBJECTCOLLECTION_H_


/* *********************************************************************************
 * Includes
 * *********************************************************************************/

// Stc C++ includes
#include <list>

// BaSyx includes
#include "BType.h"
#include "ref/BRef.h"



/* *********************************************************************************
 * BaSyx object collection
 * *********************************************************************************/

class BObjectCollection : public BType {


	// Carried values
	protected:
		std::list<BRef<BType>> listElements;


	// Constructor
	public:
		BObjectCollection() : BType() {baSysTypeID = BASYS_COLLECTION;}


	// Public interface
	public:
		// Access members
		std::list<BRef<BType>> *elements() {return &listElements;}

		// Add element
		void add(BRef<BType> element) {listElements.push_back(element);}


};



#endif /* TYPES_BOBJECTCOLLECTION_H_ */
