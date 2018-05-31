/*
 * BaSyxNativeAASManager.h
 *
 *      Author: kuhn
 */

#ifndef BASYX_BASYXNATIVEAASMANAGER_H_
#define BASYX_BASYXNATIVEAASMANAGER_H_


/* ******************************************
 * Includes
 * ******************************************/

// BaSyx includes
#include "api/IAASManager.h"



/* ******************************************
 * AAS manager interface class
 * ******************************************/
class BaSyxNativeAASManager : public IAASManager {


	//////////////////////////////////////////////////////////////
	// Exported API
	public:

		/* *******************************************************
		 * Retrieve a named AAS
		 * *******************************************************/
		virtual IAssetAdministrationShell *retrieveAAS(std::string aasID);
};



#endif /* BASYX_BASYXNATIVEAASMANAGER_H_ */
