/*
 * IAASManager.h
 *
 *      Author: kuhn
 */

#ifndef API_IAASMANAGER_H_
#define API_IAASMANAGER_H_



/* ******************************************
 * Includes
 * ******************************************/

// StdC++ includes
#include <string>

// BaSyx includes
#include <api/IAssetAdministrationShell.h>



/* ******************************************
 * AAS manager interface class
 * ******************************************/
class IAASManager {


	//////////////////////////////////////////////////////////////
	// Exported API
	public:

		/* *******************************************************
		 * Retrieve a named AAS
		 * *******************************************************/
		virtual IAssetAdministrationShell *retrieveAAS(std::string aasID) = 0;
};


#endif /* API_IAASMANAGER_H_ */
