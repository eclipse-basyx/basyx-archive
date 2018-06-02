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


	// Test RTTI values for sub model properties
	ASSERT_EQ(statusSubModel->rtti_propertyType["statusProperty1"], BASYS_INT);       // @suppress("Invalid arguments")
	ASSERT_EQ(statusSubModel->rtti_propertyType["statusProperty2"], BASYS_CHAR);      // @suppress("Invalid arguments")
	ASSERT_EQ(statusSubModel->rtti_propertyType["statusProperty3"], BASYS_BOOLEAN);   // @suppress("Invalid arguments")


	// Test variable value access for sub model properties
	// - Get pointer to member
	void *mbr1 = statusSubModel->rtti_propertyValue["statusProperty1"];
	void *mbr2 = statusSubModel->rtti_propertyValue["statusProperty2"];
	void *mbr3 = statusSubModel->rtti_propertyValue["statusProperty3"];
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
