/*
 * test_devicestatussm.cc
 *
 * Test accessing a sub model that does not know about its device
 *
 *      Author: kuhn
 */




/* ************************************************
 * Includes
 * ************************************************/

// GTest
#include "gtest/gtest.h"
#include "gtest/gtest-all.cc"

// BaSyx
#include "basysid/BaSysID.h"

// Test specific
#include "DeviceStatusSubModel.h"




/* ************************************************
 * Test class
 * ************************************************/
class TestDeviceStatusSM : public ::testing::Test {

};




/* ************************************************
 * Test cases
 * ************************************************/

// Test path components
TEST_F(TestDeviceStatusSM, testStatusSMAccess) { // @suppress("Invalid arguments")

	// Instantiate sub model "status"
	DeviceStatusSM *statusSubModel = new DeviceStatusSM("status", "ExampleStatusSM");
	// - Set property values
	statusSubModel->statusProperty1 = 13;
	statusSubModel->statusProperty2 = 'a';
	statusSubModel->statusProperty3 = true;
	statusSubModel->statusProperty4.nestedProperty1 = 20;
	statusSubModel->statusProperty4.nestedProperty2 = 'b';
	statusSubModel->statusProperty4.nestedProperty3 = false;


	// Test RTTI values for sub model properties
	ASSERT_EQ(statusSubModel->rtti_propertyType["statusProperty1"], BASYS_INT);       // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(statusSubModel->rtti_propertyType["statusProperty2"], BASYS_CHAR);      // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(statusSubModel->rtti_propertyType["statusProperty3"], BASYS_BOOLEAN);   // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(statusSubModel->rtti_propertyType["statusProperty4"], BASYS_IELEMENT);  // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")


	// Test variable value access for sub model properties
	// - Get pointer to member
	void *mbr1 = statusSubModel->rtti_propertyValue["statusProperty1"];               // @suppress("Field cannot be resolved")
	void *mbr2 = statusSubModel->rtti_propertyValue["statusProperty2"];               // @suppress("Field cannot be resolved")
	void *mbr3 = statusSubModel->rtti_propertyValue["statusProperty3"];               // @suppress("Field cannot be resolved")
	// - Read variable values via element pointer
	ASSERT_EQ(*((int  *) mbr1), 13);                                                  // @suppress("Invalid arguments")
	ASSERT_EQ(*((char *) mbr2), 'a');                                                 // @suppress("Invalid arguments")
	ASSERT_EQ(*((bool *) mbr3), true);                                                // @suppress("Invalid arguments")
	// - Change variable values via element pointer
	*((int *) mbr1) = 15;
	// - Read variable values again via element pointer
	ASSERT_EQ(*((int  *) mbr1), 15);                                                  // @suppress("Invalid arguments")


	// Test invocation of operation "setBaseLine"
	// - Parameter list for invocation
	BRef<BObjectCollection> pars1 = BRef<BObjectCollection>(new BObjectCollection());
	pars1->add(BRef<BValue>(19));
	// - Invoke function without parameter and return value
	BRef<BValue> res1 = statusSubModel->_basyx_handle("setBaseline", pars1);          // @suppress("Invalid arguments")
	// - Check result
	ASSERT_EQ(res1->getType(), BASYS_INT);                                            // @suppress("Invalid arguments")
	ASSERT_EQ(res1->getInt(), 20);                                                    // @suppress("Invalid arguments")


	// Test access to nested property
	void *mbr4 = statusSubModel->rtti_propertyValue["statusProperty4"];               // @suppress("Field cannot be resolved")
	NestedStatusSubModel *nestedSM = (NestedStatusSubModel *) mbr4;
	// - Get pointer to member
	void *nmbr1 = nestedSM->rtti_propertyValue["nestedProperty1"];                    // @suppress("Field cannot be resolved")
	void *nmbr2 = nestedSM->rtti_propertyValue["nestedProperty2"];                    // @suppress("Field cannot be resolved")
	void *nmbr3 = nestedSM->rtti_propertyValue["nestedProperty3"];                    // @suppress("Field cannot be resolved")
	// - Read variable values via element pointer
	ASSERT_EQ(*((int  *) nmbr1), 20);                                                 // @suppress("Invalid arguments")
	ASSERT_EQ(*((char *) nmbr2), 'b');                                                // @suppress("Invalid arguments")
	ASSERT_EQ(*((bool *) nmbr3), false);                                              // @suppress("Invalid arguments")
	// - Change variable values via element pointer
	*((int *) nmbr1) = 21;
	// - Read variable values again via element pointer
	ASSERT_EQ(*((int  *) nmbr1), 21);                                                 // @suppress("Invalid arguments")
}





/* ************************************************
 * Run test suite
 * ************************************************/
int main(int argc, char **argv) {
	// Init gtest framework
	::testing::InitGoogleTest(&argc, argv);

	// Run all tests
	return RUN_ALL_TESTS();
}
