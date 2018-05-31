/*
 * ISingleProperty.h
 *
 *  Created on: 29.04.2018
 *      Author: kuhn
 */

#ifndef API_ISINGLEPROPERTY_H_
#define API_ISINGLEPROPERTY_H_



/* *********************************************************************************
 * Includes
 * *********************************************************************************/
#include "../types/BType.h"
#include "IProperty.h"




/* *********************************************************************************
 * Single property interface
 * *********************************************************************************/

class ISingleProperty : public IProperty {

	//////////////////////////////////////////////////////////////
	// Exported API
	public:

		/* *******************************************************
		 * Get property value
		 * *******************************************************/
		virtual BType get() = 0;

};



#endif /* API_ISINGLEPROPERTY_H_ */
