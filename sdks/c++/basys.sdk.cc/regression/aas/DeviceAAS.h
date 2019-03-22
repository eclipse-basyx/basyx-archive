/*
 * DeviceStatusSubModel.h
 *
 * Regression test case for asset administratrion shell
 *
 * This test case illustrates a device that exports an asset administration shell with two sub models
 *
 *      Author: kuhn
 */


#ifndef TESTS_AAS_DEVICEAAS_H_
#define TESTS_AAS_DEVICEAAS_H_


/* ***********************************************************************
 * Includes
 * ***********************************************************************/

// BaSyx includes
#include <api/ISubModel.h>
#include <aas/AssetAdministrationShell.h>




/* ***********************************************************************
 * Class definition for device Asset Administration Shell
 *
 * - This is an Asset Administration SHell of a device
 * ***********************************************************************/
class DeviceAAS : public AssetAdministrationShell {


	// Constructor
	public:
		// Default constructor
		DeviceAAS(std::string id, std::string type) : AssetAdministrationShell(id, type) {
			// Fill RTTI table
			basyx_fillRTTI();
		}


	// RTTI information
	protected:
		// BaSyx RTTI table
		BASYX_RTTI_START(DeviceAAS, AssetAdministrationShell)
		BASYX_RTTI_END

};



#endif /* TESTS_AAS_DEVICEAAS_H_ */
