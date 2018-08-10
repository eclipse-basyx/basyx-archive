/*
 * TestAAS.cc
 *
 * Regression test case for sub model
 *
 * This test case illustrates a device that exports a sub model with live data and services.
 * It does not know the AAS that it is contributing to.
 *
 *      Author: kuhn
 */



/////////////////////////////////////////////////////////////////
// Includes

// GTest
#include "regression/support/gtest/gtest.h"

// StdC++ includes
#include <list>

// BaSyx includes
#include "aas/RTTIMacros.h"
#include "aas/SubModel.h"
#include "backends/provider/cxx/CXXModelProvider.h"
#include "types/BValue.h"
#include "types/BObjectCollection.h"
#include "types/BaSysTypes.h"
#include "parameter/BParameter.h"

#include "../../submodels/DeviceStatusSubModel.h"




/* ************************************************
 * Test class
 * ************************************************/
class TestCXXModelProviderSubModel : public ::testing::Test {

};




// Test CXX provider to access a sub model
TEST_F(TestCXXModelProviderSubModel, testCXXProviderSMAccess) {                                              // @suppress("Invalid arguments")

	// Instantiate sub model "status"
	DeviceStatusSM *statusSubModel = new DeviceStatusSM("status", "ExampleStatusSM");
	// - Set property values
	statusSubModel->statusProperty1 = 13;
	statusSubModel->statusProperty2 = 'a';
	statusSubModel->statusProperty3 = true;
	// - Set property values of nested properties
	statusSubModel->statusProperty4.nestedProperty1 = 20;
	statusSubModel->statusProperty4.nestedProperty2 = 'b';
	statusSubModel->statusProperty4.nestedProperty3 = false;


	// Instantiate IElement provider
	CXXModelProvider *modelProvider = new CXXModelProvider();                                                // @suppress("Abstract class cannot be instantiated")
	// - Attach sub model to provider without scope (device does not know the scope)
	modelProvider->attach(statusSubModel, "");                                                               // @suppress("Invalid arguments")


	// Read sub model properties via CXXModelProvider
	BRef<BType>  valRef1a = (BRef<BType>)  modelProvider->getModelPropertyValue("status/statusProperty1");   // @suppress("Invalid arguments")
	BRef<BValue> valRef1b = (BRef<BValue>) modelProvider->getModelPropertyValue("status/statusProperty1");   // @suppress("Invalid arguments")
	BRef<BValue> valRef2a = (BRef<BValue>) modelProvider->getModelPropertyValue("status/statusProperty2");   // @suppress("Invalid arguments")
	BRef<BValue> valRef3a = (BRef<BValue>) modelProvider->getModelPropertyValue("status/statusProperty3");   // @suppress("Invalid arguments")
	// - Type check
	ASSERT_EQ(valRef1a.getRef()->getType(), BASYS_INT);                                                      // @suppress("Invalid arguments")
	ASSERT_EQ(valRef1b.getRef()->getType(), BASYS_INT);                                                      // @suppress("Invalid arguments")
	ASSERT_EQ(valRef1a->getType(), BASYS_INT);                                                               // @suppress("Invalid arguments")
	ASSERT_EQ(valRef1b->getType(), BASYS_INT);                                                               // @suppress("Invalid arguments")
	// - Value check
	ASSERT_EQ(valRef1b->getInt(), 13);                                                                       // @suppress("Invalid arguments")
	ASSERT_EQ(valRef2a->getCharacter(), 'a');                                                                // @suppress("Invalid arguments")
	ASSERT_EQ(valRef3a->getBoolean(), true);                                                                 // @suppress("Invalid arguments")


	// Update sub model properties via CXXModelProvider
	// - Ordinary (long access) that instantiates BValue for BRef manually
	modelProvider->setModelPropertyValue("status/statusProperty1", BRef<BValue>(new BValue(15)));            // @suppress("Invalid arguments")
	// - Short hand access
	modelProvider->setModelPropertyValue("status/statusProperty2", BRef<BValue>('b'));                       // @suppress("Invalid arguments")


	// Read sub model properties again and check if values did change
	// - Previously read values must not change
	ASSERT_EQ(valRef1b->getInt(), 13);                                                                       // @suppress("Invalid arguments")
	ASSERT_EQ(valRef2a->getCharacter(), 'a');                                                                // @suppress("Invalid arguments")
	// - Read new values
	BRef<BValue> valRef1c = (BRef<BValue>) modelProvider->getModelPropertyValue("status/statusProperty1");   // @suppress("Invalid arguments")
	BRef<BValue> valRef2c = (BRef<BValue>) modelProvider->getModelPropertyValue("status/statusProperty2");   // @suppress("Invalid arguments")
	// - Value check
	ASSERT_EQ(valRef1c->getInt(), 15);                                                                       // @suppress("Invalid arguments")
	ASSERT_EQ(valRef2c->getCharacter(), 'b');                                                                // @suppress("Invalid arguments")


	// Invoke operation "calibrate" of sub model
	// - Create parameter list
	BRef<BObjectCollection> pars1 = BRef<BObjectCollection>(new BObjectCollection());
	// - Invoke operation
	BRef<BValue> res1 = modelProvider->invokeOperation("status/calibrate", pars1);                           // @suppress("Invalid arguments")
	// - Check type of result, no result should be provided
	ASSERT_EQ(res1->getType(), BASYS_NULL);


	// Invoke operation "setBaseline" of sub model
	// - Create parameter list
	BRef<BObjectCollection> pars2 = BRef<BObjectCollection>(new BObjectCollection());
	pars2->add(BRef<BValue>(19));
	// - Invoke operation
	BRef<BValue> res2 = modelProvider->invokeOperation("status/setBaseline", pars2);                         // @suppress("Invalid arguments")
	// - Check type of result, an integer result is expected
	//   whose value is input parameter value increased by 1
	ASSERT_EQ(res2->getType(), BASYS_INT);
	ASSERT_EQ(res2->getInt(), 20);


	// Invoke operation "getRawData" of sub model
	// - Create parameter list
	BRef<BObjectCollection> pars3 = BRef<BObjectCollection>(new BObjectCollection());
	// - Invoke operation
	BRef<BValue> res3 = modelProvider->invokeOperation("status/getRawData", pars3);                          // @suppress("Invalid arguments")
	// - Check type of result, an integer result is expected
	//   whose value is 12
	ASSERT_EQ(res3->getType(), BASYS_INT);
	ASSERT_EQ(res3->getInt(), 12);



	// Read and change values from nested property
	BRef<BValue> valNest41 = (BRef<BValue>) modelProvider->getModelPropertyValue("status/statusProperty4/nestedProperty1");   // @suppress("Invalid arguments")
	// - First value check
	ASSERT_EQ(valNest41->getInt(), 20);                                                                                       // @suppress("Invalid arguments")
	// - Change value of nested property
	modelProvider->setModelPropertyValue("status/statusProperty4/nestedProperty1", BRef<BValue>(21));                         // @suppress("Invalid arguments")
	// - Read value again and check again
	valNest41 = (BRef<BValue>) modelProvider->getModelPropertyValue("status/statusProperty4/nestedProperty1");                // @suppress("Invalid arguments")
	ASSERT_EQ(valNest41->getInt(), 21);                                                                                       // @suppress("Invalid arguments")


	// Invoke operation "selfTest" of sub model nested property
	// - Create parameter list
	BRef<BObjectCollection> pars4 = BRef<BObjectCollection>(new BObjectCollection());
	// - Invoke operation
	BRef<BValue> res4 = modelProvider->invokeOperation("status/statusProperty4/selfTest", pars4);                             // @suppress("Invalid arguments")
	// - Check type of result, a boolean result is expected
	//   whose value is true
	ASSERT_EQ(res4->getType(), BASYS_BOOLEAN);
	ASSERT_EQ(res4->getBoolean(), true);

}
