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
 * Nested sub model
 * ***********************************************************************/
class NestedStatusSubModel : public IElement {

	// Constructor
	public:
		// Default constructor
		NestedStatusSubModel() : IElement("", "", 0) {
			// Fill RTTI table
			basyx_fillRTTI();
		}


	// Properties - those are filled with live status data
	public:
		int  nestedProperty1;
		char nestedProperty2;
		bool nestedProperty3;


	// Exported operations of nested element
	public:
		// Example operation: selfTest  (() -> (B))
		BRef<BType> selfTest(BRef<BType> param);


	// RTTI information
	protected:
		// BaSyx RTTI table
		BASYX_RTTI_START(NestedStatusSubModel, IElement)
			// Add property elements from this class
			RTTI_PROPERTY(nestedProperty1, BASYS_INT)
			RTTI_PROPERTY(nestedProperty2, BASYS_CHARACTER)   // FIXME: URI auf statusProperty zusätzlich zu Variablenname
			RTTI_PROPERTY(nestedProperty3, BASYS_BOOLEAN)

			// Add operations for this class
			RTTI_OPERATION2(selfTest,   NestedStatusSubModel::selfTest)           // @suppress("Invalid arguments")
		BASYX_RTTI_END
};



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
		int                  statusProperty1;
		char                 statusProperty2;
		bool                 statusProperty3;
		NestedStatusSubModel statusProperty4;


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
			RTTI_PROPERTY(statusProperty4, BASYS_IELEMENT)

			// Add operations for this class
			RTTI_OPERATION2(calibrate,   DeviceStatusSM::calibrate)           // @suppress("Invalid arguments")
			RTTI_OPERATION2(setBaseline, DeviceStatusSM::setBaseline)         // @suppress("Invalid arguments")
			RTTI_OPERATION2(getRawData,  DeviceStatusSM::getRawData)          // @suppress("Invalid arguments")
		BASYX_RTTI_END

};



#endif /* TESTS_SUBMODELS_DEVICESTATUSSUBMODEL_H_ */
