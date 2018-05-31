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
#include "RTTIMacros.h"


/* *********************************************************************************
 * Asset administration shell
 * *********************************************************************************/
class AssetAdministrationShell : public IAssetAdministrationShell {


	// Constructor
	public:
		// Default constructor
		AssetAdministrationShell(std::string aasID, std::string aasTypeID, IElement *aasParent = 0) : IAssetAdministrationShell(aasID, aasTypeID, aasParent) {
			// Do nothing
		}



	// Member functions
	public:
		/* *******************************************************
		 * Retrieve list of sub models by type
		 * *******************************************************/
		virtual std::map<std::string, ISubModel *> getSubModelsByType() {

		}

		/* *******************************************************
		 * Retrieve list of sub models by ID
		 * *******************************************************/
		virtual std::map<std::string, ISubModel *> getSubModelsByID() {

		}



	// BaSyx RTTI table
	public:

		/**
		 * Fill elements of BaSyx RTTI tables with exported members of this class
		 */
		void basyx_fillRTTI() {
			// Fill elements of base class
			IAssetAdministrationShell::basyx_fillRTTI();
		}

};


#endif /* AAS_ASSETADMINISTRATIONSHELL_H_ */

