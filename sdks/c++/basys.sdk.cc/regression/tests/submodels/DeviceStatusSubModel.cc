/*
 * DeviceStatusSubModel.cc
 *
 *  Created on: 27.05.2018
 *      Author: kuhn
 */



/////////////////////////////////////////////////////////////////
// Includes
#include "DeviceStatusSubModel.h"




/////////////////////////////////////////////////////////////////
// Implement example operation: Calibrate  (() -> ())
BRef<BType> DeviceStatusSM::calibrate(BRef<BType> param) {
	// This operation does not expect any parameter
	printf("Calibrating...\n");

	// No return value expected - return null object as result
	return BRef<BNullObject>(new BNullObject());
}


/////////////////////////////////////////////////////////////////
// Implement example operation: Set baseline  ((I) -> I)
BRef<BType> DeviceStatusSM::setBaseline(BRef<BType> param) {
	// Flag that indicates whether parameter list was correct
	bool parameterCheck;

	// Extracted input parameter
	int  par1;



	// Examples for accessing input parameter:
	// + Check parameter list and types of input parameter
	//   - This operation expects one integer parameter
	CHECK_PARAMETER(parameterCheck, param, INT);

	// + Copy all parameter or a part of the parameter list into C/C++ variable types
	//   - This operation expects one integer parameter
	ACCESS_PARAMETER_SAFE(parameterCheck, param, INT, par1);

	// + Access parameter individually
	//   - First parameter is integer parameter
	ACCESS_SINGLE_PARAMETER_SAFE(parameterCheck, param, 1, INT, par1);



	// Check input parameter
	if (!isParameterList(param)) { // @suppress("Invalid arguments")
		// Error message
		printf("Invalid parameter!\n");

		// Return null object as result
		return BRef<BNullObject>(new BNullObject());
	}


	// Do something...
	printf("Base line set to %i...\n", par1);


	// Return increased parameter value
	return BRef<BValue>(new BValue(++par1));
}


/////////////////////////////////////////////////////////////////
// Get raw data value  (() -> I)
BRef<BType> DeviceStatusSM::getRawData(BRef<BType> param) {
	// This operation does not expect any parameter

	// Get raw data
	int rawData = 12;

	// Do something...
	printf("Getting raw data %i...\n", rawData);

	// No return value expected - return null object as result
	return BRef<BValue>(rawData);
}

