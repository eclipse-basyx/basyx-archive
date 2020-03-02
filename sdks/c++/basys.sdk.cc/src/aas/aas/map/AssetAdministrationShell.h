/*
 * AssetAdministrationShell.h
 *
 *      Author: kuhn
 */

#ifndef AAS_ASSETADMINISTRATIONSHELL_H_
#define AAS_ASSETADMINISTRATIONSHELL_H_

// Standard includes
#include <stdio.h>
#include <stdlib.h>

// StdC++ includes
#include <map>
#include <string>

// BaSyx includes
#include <api/IAssetAdministrationShell.h>
#include <types/BObjectMap.h>
#include "RTTIMacros.h"


/* *********************************************************************************
 * Asset administration shell
 * *********************************************************************************/
class AssetAdministrationShell : public IAssetAdministrationShell {  // @suppress("Class has a virtual method and non-virtual destructor")


	// Constructor
	public:
		// Default constructor
		AssetAdministrationShell(std::string aasID, std::string aasTypeID, IElement *aasParent = 0) : IAssetAdministrationShell(aasID, aasTypeID, aasParent) {
			// Do nothing
		}



	// Asset Administration Shell members
	protected:
		// Store sub models by ID
		BObjectMap subModelsByID;

		// Store sub models by type
		BObjectMap subModelsByType;



	// Member functions
	public:
		/* *******************************************************
		 * Retrieve list of sub models by type
		 * *******************************************************/
		virtual BObjectMap::object_map_t * getSubModelsByType() override{
			return subModelsByType.elements();
		}

		/* *******************************************************
		 * Retrieve list of sub models by ID
		 * *******************************************************/
		virtual BObjectMap::object_map_t * getSubModelsByID() override {
			return subModelsByID.elements();
		}

		/* *******************************************************
		 * Add a sub model to this Asset Administration Shell
		 * *******************************************************/
		virtual void addSubModel(std::string id, std::string type, ISubModel *submodel) {
			// Add sub model to maps
			subModelsByID.elements()->insert(std::make_pair(id, BRef<BType>(submodel, false)));
			subModelsByType.elements()->insert(std::make_pair(type, BRef<BType>(submodel, false)));
		}



	// BaSyx RTTI table
	public:
		// BaSyx RTTI table
		BASYX_RTTI_START(AssetAdministrationShell, IAssetAdministrationShell)
			// Add property elements from this class
			RTTI_PROPERTY(subModelsByID,   BASYS_MAP)
			RTTI_PROPERTY(subModelsByType, BASYS_MAP)
		BASYX_RTTI_END
};

#endif


#endif /* AAS_ASSETADMINISTRATIONSHELL_H_ */

