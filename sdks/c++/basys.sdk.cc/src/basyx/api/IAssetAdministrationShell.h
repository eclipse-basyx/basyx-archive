/*
 * IAssetAdministrationShell.h
 *
 *      Author: kuhn
 */

#ifndef API_IASSETADMINISTRATIONSHELL_H_
#define API_IASSETADMINISTRATIONSHELL_H_


/* *********************************************************************************
 * Includes
 * *********************************************************************************/

// StdC++ includes
#include <map>
#include <string>

// BaSyx includes
#include "ISubModel.h"
#include "IElement.h"
#include "types/BObjectMap.h"


/* *********************************************************************************
 * Asset administration shell interface
 * *********************************************************************************/

class IAssetAdministrationShell : public IElement {


	/* *********************************************************************************
	 * IAssetAdministrationShell constructor
	 * *********************************************************************************/
	public:

		// Constructor
		IAssetAdministrationShell(std::string aasID, std::string aasTypeID, IElement *aasParent = nullptr) : IElement(aasID, aasTypeID, aasParent) {
			// Do nothing
		}



	//////////////////////////////////////////////////////////////
	// Exported API
	public:

		/* *******************************************************
		 * Retrieve list of sub models by type
		 * *******************************************************/
		virtual BObjectMap::object_map_t * getSubModelsByType() = 0;

		/* *******************************************************
		 * Retrieve list of sub models by ID
		 * *******************************************************/
		virtual BObjectMap::object_map_t * getSubModelsByID() = 0;
};


#endif /* API_IASSETADMINISTRATIONSHELL_H_ */
