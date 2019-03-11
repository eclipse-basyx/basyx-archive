/*
 * ISubModel.h
 *
 *      Author: kuhn
 */

#ifndef API_ISUBMODEL_H_
#define API_ISUBMODEL_H_


/* *********************************************************************************
 * Includes
 * *********************************************************************************/

// StdC++ includes
#include <map>
#include <string>

// BaSyx includes
#include "IProperty.h"
#include "IElement.h"
#include "parameter/BParameter.h"



/* *********************************************************************************
 * Sub model interface class
 * *********************************************************************************/

class ISubModel : public IElement {


	/* *********************************************************************************
	 * ISubModel constructor
	 * *********************************************************************************/
	public:
		// Constructor
		ISubModel(std::string aasID, std::string aasTypeID, IElement *aasParent = 0) : IElement(aasID, aasTypeID, aasParent) {
			// Do nothing
		}


	//////////////////////////////////////////////////////////////
	// Exported API
	public:

		/* *******************************************************
		 * Retrieve list of sub model properties
		 * *******************************************************/
		virtual std::map<std::string, BRef<BType>> getProperties() = 0;

};



#endif /* API_ISUBMODEL_H_ */
