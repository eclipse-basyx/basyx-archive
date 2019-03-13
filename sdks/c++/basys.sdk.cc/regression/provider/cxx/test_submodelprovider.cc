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
#include "gtest/gtest.h"

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


// Test CXX provider to access a sub model
TEST(TestCXXModelProviderSubModel, testCXXProviderSMAccess) {                                             

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
	CXXModelProvider *modelProvider = new CXXModelProvider();                                                
	// - Attach sub model to provider without scope (device does not know the scope)
	modelProvider->attach(statusSubModel, "status/submodel");                                                              


	// Read sub model properties via CXXModelProvider
	BRef<BType>  valRef1a = (BRef<BType>)  modelProvider->getModelPropertyValue("status/submodel/properties/statusProperty1");  
	BRef<BValue> valRef1b = (BRef<BValue>) modelProvider->getModelPropertyValue("status/submodel/properties/statusProperty1");  
	BRef<BValue> valRef2a = (BRef<BValue>) modelProvider->getModelPropertyValue("status/submodel/properties/statusProperty2");  
	BRef<BValue> valRef3a = (BRef<BValue>) modelProvider->getModelPropertyValue("status/submodel/properties/statusProperty3");  
	// - Type check
	ASSERT_EQ(valRef1a.getRef()->getType(), BASYS_INT);                                                     
	ASSERT_EQ(valRef1b.getRef()->getType(), BASYS_INT);                                                     
	ASSERT_EQ(valRef1a->getType(), BASYS_INT);                                                              
	ASSERT_EQ(valRef1b->getType(), BASYS_INT);                                                              
	// - Value check
	ASSERT_EQ(valRef1b->getInt(), 13);                                                                      
	ASSERT_EQ(valRef2a->getCharacter(), 'a');                                                               
	ASSERT_EQ(valRef3a->getBoolean(), true);                                                                


	// Update sub model properties via CXXModelProvider
	// - Ordinary (long access) that instantiates BValue for BRef manually
	modelProvider->setModelPropertyValue("status/submodel/properties/statusProperty1", BRef<BValue>(new BValue(15)));           
	// - Short hand access
	modelProvider->setModelPropertyValue("status/submodel/properties/statusProperty2", BRef<BValue>('b'));                      


	// Read sub model properties again and check if values did change
	// - Previously read values must not change
	ASSERT_EQ(valRef1b->getInt(), 13);                                                                      
	ASSERT_EQ(valRef2a->getCharacter(), 'a');                                                               
	// - Read new values
	BRef<BValue> valRef1c = (BRef<BValue>) modelProvider->getModelPropertyValue("status/submodel/properties/statusProperty1");  
	BRef<BValue> valRef2c = (BRef<BValue>) modelProvider->getModelPropertyValue("status/submodel/properties/statusProperty2");  
	// - Value check
	ASSERT_EQ(valRef1c->getInt(), 15);                                                                      
	ASSERT_EQ(valRef2c->getCharacter(), 'b');                                                               


	// Invoke operation "calibrate" of sub model
	// - Create parameter list
	BRef<BObjectCollection> pars1 = BRef<BObjectCollection>(new BObjectCollection());
	// - Invoke operation
	BRef<BValue> res1 = modelProvider->invokeOperation("status/submodel/properties/calibrate", pars1);                          
	// - Check type of result, no result should be provided
	ASSERT_EQ(res1->getType(), BASYS_NULL);


	// Invoke operation "setBaseline" of sub model
	// - Create parameter list
	BRef<BObjectCollection> pars2 = BRef<BObjectCollection>(new BObjectCollection());
	pars2->add(BRef<BValue>(19));
	// - Invoke operation
	BRef<BValue> res2 = modelProvider->invokeOperation("status/submodel/properties/setBaseline", pars2);                        
	// - Check type of result, an integer result is expected
	//   whose value is input parameter value increased by 1
	ASSERT_EQ(res2->getType(), BASYS_INT);
	ASSERT_EQ(res2->getInt(), 20);


	// Invoke operation "getRawData" of sub model
	// - Create parameter list
	BRef<BObjectCollection> pars3 = BRef<BObjectCollection>(new BObjectCollection());
	// - Invoke operation
	BRef<BValue> res3 = modelProvider->invokeOperation("status/submodel/properties/getRawData", pars3);                         
	// - Check type of result, an integer result is expected
	//   whose value is 12
	ASSERT_EQ(res3->getType(), BASYS_INT);
	ASSERT_EQ(res3->getInt(), 12);



	// Read and change values from nested property
	BRef<BValue> valNest41 = (BRef<BValue>) modelProvider->getModelPropertyValue("status/submodel/properties/statusProperty4/nestedProperty1");  
	// - First value check
	ASSERT_EQ(valNest41->getInt(), 20);                                                                                      
	// - Change value of nested property
	modelProvider->setModelPropertyValue("status/submodel/properties/statusProperty4/nestedProperty1", BRef<BValue>(21));                        
	// - Read value again and check again
	valNest41 = (BRef<BValue>) modelProvider->getModelPropertyValue("status/submodel/properties/statusProperty4/nestedProperty1");               
	ASSERT_EQ(valNest41->getInt(), 21);                                                                                      


	// Invoke operation "selfTest" of sub model nested property
	// - Create parameter list
	BRef<BObjectCollection> pars4 = BRef<BObjectCollection>(new BObjectCollection());
	// - Invoke operation
	BRef<BValue> res4 = modelProvider->invokeOperation("status/submodel/properties/statusProperty4/selfTest", pars4);                            
	// - Check type of result, a boolean result is expected
	//   whose value is true
	ASSERT_EQ(res4->getType(), BASYS_BOOLEAN);
	ASSERT_EQ(res4->getBoolean(), true);

}
