/*
 * BObject.h
 *
 *      Author: kuhn
 */

#ifndef TYPES_BNULLOBJECT_H_
#define TYPES_BNULLOBJECT_H_


/* *********************************************************************************
 * Includes
 * *********************************************************************************/

// BaSyx
#include "BaSysTypes.h"
#include "BType.h"



/* *********************************************************************************
 * BaSyx null object
 * *********************************************************************************/

class BNullObject : public BType {


	// Constructor
	public:
		BNullObject() : BType() {baSysTypeID = BASYS_NULL;}

};


#endif /* TYPES_BNULLOBJECT_H_ */
