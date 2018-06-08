/*
 * DeviceStatusSubModel.h
 *
 * Regression test case for sub model
 *
 * This test case illustrates a device that exports a sub model with live data and services.
 * It does not know the AAS that it is contributing to.
 *
 *  Created on: 20.05.2018
 *      Author: kuhn
 */


#ifndef TESTS_SUBMODELS_DEVICESTATUSSUBMODEL_H_
#define TESTS_SUBMODELS_DEVICESTATUSSUBMODEL_H_


/* ***********************************************************************
 * Includes
 * ***********************************************************************/

// BaSyx includes
#include <api/ISubModel.h>
#include <aas/SubModel.h>




/* ***********************************************************************
 * Class definition for sub model
 *
 * - This is a sub model that a device exports
 * ***********************************************************************/
class DeviceStatusSM : public SubModel {


	// Constructor
	public:
		// Default constructor
		DeviceStatusSM(std::string id, std::string type) : SubModel(id, type) {
			// Fill RTTI table
			basyx_fillRTTI();
		}


	// Properties - those are filled with live status data
	public:
		int  statusProperty1;
		char statusProperty2;
		bool statusProperty3;


	// Exported operations of sub model
	public:
		// Example operation: Calibrate  (() -> ())
		BRef<BType> calibrate(BRef<BType> param);

		// Example operation: Set baseline  ((I) -> I)
		BRef<BType> setBaseline(BRef<BType> param);

		// Example operation: Get raw data value  (() -> I)
		BRef<BType> getRawData(BRef<BType> param);


	// RTTI information
	protected:
		// BaSyx RTTI table
		BASYX_RTTI_START(DeviceStatusSM, SubModel)
			// Add property elements from this class
			RTTI_PROPERTY(statusProperty1, BASYS_INT)
			RTTI_PROPERTY(statusProperty2, BASYS_CHARACTER)   // FIXME: URI auf statusProperty zusätzlich zu Variablenname
			RTTI_PROPERTY(statusProperty3, BASYS_BOOLEAN)

			// Add operations for this class
			RTTI_OPERATION2(calibrate,   DeviceStatusSM::calibrate)           // @suppress("Invalid arguments")
			RTTI_OPERATION2(setBaseline, DeviceStatusSM::setBaseline)         // @suppress("Invalid arguments")
			RTTI_OPERATION2(getRawData,  DeviceStatusSM::getRawData)          // @suppress("Invalid arguments")
		BASYX_RTTI_END

};



#endif /* TESTS_SUBMODELS_DEVICESTATUSSUBMODEL_H_ */
