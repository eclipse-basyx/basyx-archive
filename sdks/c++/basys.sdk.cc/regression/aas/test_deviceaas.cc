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
#include "regression/support/gtest/gtest.h"

// BaSyx
#include "basysid/BaSysID.h"

// Test specific
#include "DeviceAAS.h"
#include "../submodels/DeviceStatusSubModel.h"




/* ************************************************
 * Test class
 * ************************************************/
class TestDeviceAAS : public ::testing::Test {

};




/* ************************************************
 * Test cases
 * ************************************************/

// Test path components
TEST_F(TestDeviceAAS, testStatusSMAccess) { // @suppress("Invalid arguments")

	// Instantiate sub model "status"
	DeviceAAS *deviceAAS = new DeviceAAS("device-89817-aas", "aas");
	// - Add sub model "Status"
	DeviceStatusSM *deviceStatusSM = new DeviceStatusSM("device-89817-status", "SampleDeviceStatus");
	deviceAAS->addSubModel("device-89817-status", "SampleDeviceStatus", deviceStatusSM);               // @suppress("Invalid arguments")


	// - Set property values
	deviceStatusSM->statusProperty1 = 13;
	deviceStatusSM->statusProperty2 = 'a';
	deviceStatusSM->statusProperty3 = true;


	// Test RTTI values for sub model property types and values
	ASSERT_EQ(deviceStatusSM->rtti_propertyType["statusProperty1"], BASYS_INT);          // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(deviceStatusSM->rtti_propertyType["statusProperty2"], BASYS_CHAR);         // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(deviceStatusSM->rtti_propertyType["statusProperty3"], BASYS_BOOLEAN);      // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")


	// Test variable value access for sub model properties
	// - Read variable values via RTTI table
	ASSERT_EQ(*((int  *) deviceStatusSM->rtti_propertyValue["statusProperty1"]), 13);    // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(*((char *) deviceStatusSM->rtti_propertyValue["statusProperty2"]), 'a');   // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(*((bool *) deviceStatusSM->rtti_propertyValue["statusProperty3"]), true);  // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")


	// Validate AAS sub model table size
	ASSERT_EQ(deviceAAS->getSubModelsByType().size(), (std::size_t) 1);                  // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(deviceAAS->getSubModelsByID().size(), (std::size_t) 1);                    // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")


	// Get sub model by its type
	DeviceStatusSM *deviceStatusSMAlt1 = (DeviceStatusSM *) (deviceAAS->getSubModelsByType()["SampleDeviceStatus"]).getRef();
	// - Test RTTI values for sub model property types and values
	ASSERT_EQ(deviceStatusSMAlt1->rtti_propertyType["statusProperty1"], BASYS_INT);          // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(deviceStatusSMAlt1->rtti_propertyType["statusProperty2"], BASYS_CHAR);         // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(deviceStatusSMAlt1->rtti_propertyType["statusProperty3"], BASYS_BOOLEAN);      // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(*((int  *) deviceStatusSMAlt1->rtti_propertyValue["statusProperty1"]), 13);    // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(*((char *) deviceStatusSMAlt1->rtti_propertyValue["statusProperty2"]), 'a');   // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(*((bool *) deviceStatusSMAlt1->rtti_propertyValue["statusProperty3"]), true);  // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")


	// Query AAS sub models by ID "device-89817-status"
	DeviceStatusSM *deviceStatusSMAlt2 = (DeviceStatusSM *) (deviceAAS->getSubModelsByID()["device-89817-status"]).getRef();
	// - Test RTTI values for sub model property types and values
	ASSERT_EQ(deviceStatusSMAlt2->rtti_propertyType["statusProperty1"], BASYS_INT);          // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(deviceStatusSMAlt2->rtti_propertyType["statusProperty2"], BASYS_CHAR);         // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(deviceStatusSMAlt2->rtti_propertyType["statusProperty3"], BASYS_BOOLEAN);      // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(*((int  *) deviceStatusSMAlt2->rtti_propertyValue["statusProperty1"]), 13);    // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(*((char *) deviceStatusSMAlt2->rtti_propertyValue["statusProperty2"]), 'a');   // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(*((bool *) deviceStatusSMAlt2->rtti_propertyValue["statusProperty3"]), true);  // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")


	// Validate that we are really accessing the correct sub model pointers
	ASSERT_EQ(deviceStatusSM, deviceStatusSMAlt1);                                           // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
	ASSERT_EQ(deviceStatusSM, deviceStatusSMAlt2);                                           // @suppress("Invalid arguments") // @suppress("Field cannot be resolved")
}
