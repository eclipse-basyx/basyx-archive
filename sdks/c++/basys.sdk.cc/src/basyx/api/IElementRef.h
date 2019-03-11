/*
 * IElementRef.h
 *
 * Specialized IElement that is a reference to an IElement stored somewhere else
 *
 *  Created on: 28.05.2018
 *      Author: kuhn
 */

#ifndef API_IELEMENTREF_H_
#define API_IELEMENTREF_H_



// BaSyx includes
#include <api/IElement.h>


/* *********************************************************************************
 * IElement class - Base class for reflexive BaSys elements
 * *********************************************************************************/
class IElementRef : public IElement {

	// Constructors
	public:
		// Constructor
		IElementRef(std::string elementId, std::string elementTypeID, IElement *elementParent = 0) : IElement(elementId, elementTypeID) {
			// Do nothing
		}


	// Exported interface



	// RTTI information
	protected:
		// BaSyx RTTI table
		BASYX_RTTI_START(IElementRef, IElement)
		BASYX_RTTI_END
};



#endif /* API_IELEMENTREF_H_ */
