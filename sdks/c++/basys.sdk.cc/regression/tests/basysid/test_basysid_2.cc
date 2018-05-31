/*
 * test_array.cc
 *
 *  Created on: 05.05.2018
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



/* ************************************************
 * Test class
 * ************************************************/
class TestBaSyxID : public ::testing::Test {

};



/* ************************************************
 * Test cases
 * ************************************************/

// Test retrieving of unscoped submodel IDs
TEST_F(TestBaSyxID, testUnscopedSubModels) { // @suppress("Invalid arguments")

	// Build example paths
	std::string smID  = BaSysID::buildSMID("submodel");                                        // @suppress("Invalid arguments")

	// Checks
	ASSERT_EQ(smID,                                 "submodel");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getAASID(smID),              "");                                       // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedAASID(smID),     "");                                       // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getSubmodelID(smID),         "submodel");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getPath(smID),               "");                                       // @suppress("Invalid arguments")
}




// Test retrieving of scoped submodel IDs
TEST_F(TestBaSyxID, testScopedSubModels) { // @suppress("Invalid arguments")

	// Build example paths
	std::string smID  = BaSysID::buildSMID("submodel.aas.scope");                              // @suppress("Invalid arguments")

	// Checks
	ASSERT_EQ(smID,                                 "submodel.aas.scope");                     // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getAASID(smID),              "aas");                                    // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedAASID(smID),     "aas.scope");                              // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getSubmodelID(smID),         "submodel");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getPath(smID),               "");                                       // @suppress("Invalid arguments")
}



// Test retrieving element ID and qualified element ID of scoped Asset Administration Shell IDs
TEST_F(TestBaSyxID, testGetElementID) { // @suppress("Invalid arguments")

	// Build example paths
	std::string aasID1  = BaSysID::buildAASID("AAS1");                                         // @suppress("Invalid arguments")
	std::string aasID2  = BaSysID::buildAASID("AAS1/path");                                    // @suppress("Invalid arguments")
	std::string aasID3  = BaSysID::buildAASID("AAS1.scope");                                   // @suppress("Invalid arguments")
	std::string aasID4  = BaSysID::buildAASID("AAS1.scope/path");                              // @suppress("Invalid arguments")
	std::string aasID5  = BaSysID::buildAASID("aas.AAS1");                                     // @suppress("Invalid arguments")
	std::string aasID6  = BaSysID::buildAASID("aas.AAS1/path");                                // @suppress("Invalid arguments")
	std::string aasID7  = BaSysID::buildAASID("aas.AAS1.scope");                               // @suppress("Invalid arguments")
	std::string aasID8  = BaSysID::buildAASID("aas.AAS1.scope/path");                          // @suppress("Invalid arguments")

	// Checks
	ASSERT_EQ(aasID1,                               "aas.AAS1");                               // @suppress("Invalid arguments")
	ASSERT_EQ(aasID2,                               "aas.AAS1/path");                          // @suppress("Invalid arguments")
	ASSERT_EQ(aasID3,                               "aas.AAS1.scope");                         // @suppress("Invalid arguments")
	ASSERT_EQ(aasID4,                               "aas.AAS1.scope/path");                    // @suppress("Invalid arguments")
	ASSERT_EQ(aasID5,                               "aas.AAS1");                               // @suppress("Invalid arguments")
	ASSERT_EQ(aasID6,                               "aas.AAS1/path");                          // @suppress("Invalid arguments")
	ASSERT_EQ(aasID7,                               "aas.AAS1.scope");                         // @suppress("Invalid arguments")
	ASSERT_EQ(aasID8,                               "aas.AAS1.scope/path");                    // @suppress("Invalid arguments")

	ASSERT_EQ(BaSysID::getElementID(aasID1),        "AAS1");                                   // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getElementID(aasID2),        "AAS1");                                   // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getElementID(aasID3),        "AAS1");                                   // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getElementID(aasID4),        "AAS1");                                   // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getElementID(aasID5),        "AAS1");                                   // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getElementID(aasID6),        "AAS1");                                   // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getElementID(aasID7),        "AAS1");                                   // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getElementID(aasID8),        "AAS1");                                   // @suppress("Invalid arguments")

	ASSERT_EQ(BaSysID::getQualifiedElementID(aasID1), "AAS1");                                 // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedElementID(aasID2), "AAS1");                                 // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedElementID(aasID3), "AAS1.scope");                           // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedElementID(aasID4), "AAS1.scope");                           // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedElementID(aasID5), "AAS1");                                 // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedElementID(aasID6), "AAS1");                                 // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedElementID(aasID7), "AAS1.scope");                           // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedElementID(aasID8), "AAS1.scope");                           // @suppress("Invalid arguments")
}



// Test retrieving element ID and qualified element ID of scoped sub model IDs
TEST_F(TestBaSyxID, testGetElementID_SM) { // @suppress("Invalid arguments")

	// Build example paths
	std::string smID1  = BaSysID::buildSMID("smid");                                           // @suppress("Invalid arguments")
	std::string smID2  = BaSysID::buildSMID("smid/path");                                      // @suppress("Invalid arguments")
	std::string smID3  = BaSysID::buildSMID("smid.AAS1");                                      // @suppress("Invalid arguments")
	std::string smID4  = BaSysID::buildSMID("smid.AAS1/path");                                 // @suppress("Invalid arguments")
	std::string smID5  = BaSysID::buildSMID("smid.AAS1.scope");                                // @suppress("Invalid arguments")
	std::string smID6  = BaSysID::buildSMID("smid.AAS1.scope/path");                           // @suppress("Invalid arguments")

	// Checks
	ASSERT_EQ(smID1,                                "smid");                                   // @suppress("Invalid arguments")
	ASSERT_EQ(smID2,                                "smid/path");                              // @suppress("Invalid arguments")
	ASSERT_EQ(smID3,                                "smid.AAS1");                              // @suppress("Invalid arguments")
	ASSERT_EQ(smID4,                                "smid.AAS1/path");                         // @suppress("Invalid arguments")
	ASSERT_EQ(smID5,                                "smid.AAS1.scope");                        // @suppress("Invalid arguments")
	ASSERT_EQ(smID6,                                "smid.AAS1.scope/path");                   // @suppress("Invalid arguments")

	ASSERT_EQ(BaSysID::getElementID(smID1),         "smid");                                   // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getElementID(smID2),         "smid");                                   // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getElementID(smID3),         "smid");                                   // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getElementID(smID4),         "smid");                                   // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getElementID(smID5),         "smid");                                   // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getElementID(smID6),         "smid");                                   // @suppress("Invalid arguments")

	ASSERT_EQ(BaSysID::getQualifiedElementID(smID1), "smid");                                  // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedElementID(smID2), "smid");                                  // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedElementID(smID3), "smid.AAS1");                             // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedElementID(smID4), "smid.AAS1");                             // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedElementID(smID5), "smid.AAS1.scope");                       // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedElementID(smID6), "smid.AAS1.scope");                       // @suppress("Invalid arguments")
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
