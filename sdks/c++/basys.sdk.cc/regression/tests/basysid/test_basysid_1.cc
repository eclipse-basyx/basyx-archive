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

// Test building of AAS IDs
TEST_F(TestBaSyxID, testBuildAASID) { // @suppress("Invalid arguments")

	// Build example paths
	std::string qualAASID1 = BaSysID::buildAASID("Stub1AAS.scopepart.scopepart.topscope");     // @suppress("Invalid arguments")
	std::string qualAASID2 = BaSysID::buildAASID("aas.Stub1AAS.scopepart.scopepart.topscope"); // @suppress("Invalid arguments")
	std::string aasID1     = BaSysID::buildAASID("Stub1AAS");                                  // @suppress("Invalid arguments")
	std::string aasID2     = BaSysID::buildAASID("aas.Stub1AAS");                              // @suppress("Invalid arguments")


	// Checks
	ASSERT_EQ(qualAASID1, "aas.Stub1AAS.scopepart.scopepart.topscope");   // @suppress("Invalid arguments")
	ASSERT_EQ(qualAASID2, "aas.Stub1AAS.scopepart.scopepart.topscope");   // @suppress("Invalid arguments")
	ASSERT_EQ(aasID1, "aas.Stub1AAS");                                    // @suppress("Invalid arguments")
	ASSERT_EQ(aasID2, "aas.Stub1AAS");                                    // @suppress("Invalid arguments")
}


// Test building of submodel IDs
TEST_F(TestBaSyxID, testBuildSubmodelID) { // @suppress("Invalid arguments")

	// Build example paths
	std::string smID1 = BaSysID::buildSMID("statusSM");                    // @suppress("Invalid arguments")
	std::string smID2 = BaSysID::buildSMID("statusSM.Stub1AAS");           // @suppress("Invalid arguments")
	std::string smID3 = BaSysID::buildSMID("statusSM.Stub1AAS.scope");     // @suppress("Invalid arguments")


	// Checks
	ASSERT_EQ(smID1, "statusSM");                                          // @suppress("Invalid arguments")
	ASSERT_EQ(smID2, "statusSM.Stub1AAS");                                 // @suppress("Invalid arguments")
	ASSERT_EQ(smID3, "statusSM.Stub1AAS.scope");                           // @suppress("Invalid arguments")
}


// Test building of paths
TEST_F(TestBaSyxID, testBuildPath) { // @suppress("Invalid arguments")

	// Build example paths
	std::string qualSMID   = BaSysID::buildPath("Stub1AAS", "statusSM");                         // @suppress("Invalid arguments")
	std::string qualSMID2  = BaSysID::buildPath("Stub1AAS.topscope", "statusSM");                // @suppress("Invalid arguments")

	std::string qualSMIDa  = BaSysID::buildPath("Stub1AAS", "");                                 // @suppress("Invalid arguments")
	std::string qualSMIDb  = BaSysID::buildPath("Stub1AAS.topscope", "");                        // @suppress("Invalid arguments")
	std::string qualSMIDc  = BaSysID::buildPath("aas.Stub1AAS", "");                             // @suppress("Invalid arguments")
	std::string qualSMIDd  = BaSysID::buildPath("aas.Stub1AAS.topscope", "");                    // @suppress("Invalid arguments")

	std::string props1[]   = {"property3", "propertyA"};
	std::string props2[]   = {"property1"};
	std::string qualProp   = BaSysID::buildPath(props1, 2, "Stub1AAS.topscope", "statusSM");     // @suppress("Invalid arguments")
	std::string qualPropa  = BaSysID::buildPath(props1, 2, "aas.Stub1AAS.topscope", "statusSM"); // @suppress("Invalid arguments")
	std::string qualProp1  = BaSysID::buildPath(props2, 1, "Stub1AAS", "statusSM");              // @suppress("Invalid arguments")
	std::string qualProp1a = BaSysID::buildPath(props2, 1, "aas.Stub1AAS", "statusSM");          // @suppress("Invalid arguments")
	std::string qualProp2  = BaSysID::buildPath(props1, 2, "Stub1AAS", "statusSM");              // @suppress("Invalid arguments")
	std::string qualProp3  = BaSysID::buildPath(props2, 1, "statusSM");                          // @suppress("Invalid arguments")
	std::string qualProp4  = BaSysID::buildPath(props1, 2, "statusSM");                          // @suppress("Invalid arguments")


	// Checks
	ASSERT_EQ(qualSMID,   "statusSM.Stub1AAS");                              // @suppress("Invalid arguments")
	ASSERT_EQ(qualSMID2,  "statusSM.Stub1AAS.topscope");                     // @suppress("Invalid arguments")
	ASSERT_EQ(qualSMIDa,  "aas.Stub1AAS");                                   // @suppress("Invalid arguments")
	ASSERT_EQ(qualSMIDb,  "aas.Stub1AAS.topscope");                          // @suppress("Invalid arguments")
	ASSERT_EQ(qualSMIDc,  "aas.Stub1AAS");                                   // @suppress("Invalid arguments")
	ASSERT_EQ(qualSMIDd,  "aas.Stub1AAS.topscope");                          // @suppress("Invalid arguments")
	ASSERT_EQ(qualProp,   "statusSM.Stub1AAS.topscope/property3/propertyA"); // @suppress("Invalid arguments")
	ASSERT_EQ(qualPropa,  "statusSM.Stub1AAS.topscope/property3/propertyA"); // @suppress("Invalid arguments")
	ASSERT_EQ(qualProp1,  "statusSM.Stub1AAS/property1");                    // @suppress("Invalid arguments")
	ASSERT_EQ(qualProp1a, "statusSM.Stub1AAS/property1");                    // @suppress("Invalid arguments")
	ASSERT_EQ(qualProp2,  "statusSM.Stub1AAS/property3/propertyA");          // @suppress("Invalid arguments")
	ASSERT_EQ(qualProp3,  "statusSM/property1");                             // @suppress("Invalid arguments")
	ASSERT_EQ(qualProp4,  "statusSM/property3/propertyA");                   // @suppress("Invalid arguments")
}


// Test path components
TEST_F(TestBaSyxID, testPathComponents) { // @suppress("Invalid arguments")

	// Build example paths
	std::string qualAASID1 = BaSysID::buildAASID("Stub1AAS.scopepart.scopepart.topscope");         // @suppress("Invalid arguments")
	std::string aasID1     = BaSysID::buildAASID("Stub1AAS");                                      // @suppress("Invalid arguments")
	std::string smID       = BaSysID::buildSMID("statusSM");                                       // @suppress("Invalid arguments")
	std::string qualSMID   = BaSysID::buildPath("Stub1AAS", "statusSM");                           // @suppress("Invalid arguments")
	std::string qualSMID2  = BaSysID::buildPath("Stub1AAS.topscope", "statusSM");                  // @suppress("Invalid arguments")

	std::string props1[]   = {"property3", "propertyA"};
	std::string props2[]   = {"property1"};
	std::string qualProp   = BaSysID::buildPath(props1, 2, "Stub1AAS.topscope", "statusSM");       // @suppress("Invalid arguments")
	std::string qualProp1  = BaSysID::buildPath(props2, 1, "Stub1AAS", "statusSM");                // @suppress("Invalid arguments")
	std::string qualProp2  = BaSysID::buildPath(props1, 2, "Stub1AAS", "statusSM");                // @suppress("Invalid arguments")
	std::string qualProp3  = BaSysID::buildPath(props2, 1, "statusSM");                            // @suppress("Invalid arguments")
	std::string qualProp4  = BaSysID::buildPath(props1, 2, "statusSM");                            // @suppress("Invalid arguments")


	// Check path components
	ASSERT_EQ(BaSysID::getAASID(qualAASID1),            "Stub1AAS");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedAASID(qualAASID1),   "Stub1AAS.scopepart.scopepart.topscope");  // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getSubmodelID(qualAASID1),       "");                                       // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getPath(qualAASID1),             "");                                       // @suppress("Invalid arguments")

	ASSERT_EQ(BaSysID::getAASID(aasID1),                "Stub1AAS");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedAASID(aasID1),       "Stub1AAS");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getSubmodelID(aasID1),           "");                                       // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getPath(aasID1),                 "");                                       // @suppress("Invalid arguments")

	ASSERT_EQ(BaSysID::getAASID(smID),                  "");                                       // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedAASID(smID),         "");                                       // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getSubmodelID(smID),             "statusSM");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getPath(smID),                   "");                                       // @suppress("Invalid arguments")

	ASSERT_EQ(BaSysID::getAASID(qualSMID),              "Stub1AAS");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedAASID(qualSMID),     "Stub1AAS");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getSubmodelID(qualSMID),         "statusSM");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getPath(qualSMID),               "");                                       // @suppress("Invalid arguments")

	ASSERT_EQ(BaSysID::getAASID(qualSMID2),             "Stub1AAS");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedAASID(qualSMID2),    "Stub1AAS.topscope");                      // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getSubmodelID(qualSMID2),        "statusSM");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getPath(qualSMID2),              "");                                       // @suppress("Invalid arguments")

	ASSERT_EQ(BaSysID::getAASID(qualProp),              "Stub1AAS");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedAASID(qualProp),     "Stub1AAS.topscope");                      // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getSubmodelID(qualProp),         "statusSM");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getPath(qualProp),               "property3/propertyA");                    // @suppress("Invalid arguments")

	ASSERT_EQ(BaSysID::getAASID(qualProp1),             "Stub1AAS");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedAASID(qualProp1),    "Stub1AAS");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getSubmodelID(qualProp1),        "statusSM");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getPath(qualProp1),              "property1");                              // @suppress("Invalid arguments")

	ASSERT_EQ(BaSysID::getAASID(qualProp2),             "Stub1AAS");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedAASID(qualProp2),    "Stub1AAS");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getSubmodelID(qualProp2),        "statusSM");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getPath(qualProp2),              "property3/propertyA");                    // @suppress("Invalid arguments")

	ASSERT_EQ(BaSysID::getAASID(qualProp3),             "");                                       // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedAASID(qualProp3),    "");                                       // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getSubmodelID(qualProp3),        "statusSM");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getPath(qualProp3),              "property1");                              // @suppress("Invalid arguments")

	ASSERT_EQ(BaSysID::getAASID(qualProp4),             "");                                       // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getQualifiedAASID(qualProp4),    "");                                       // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getSubmodelID(qualProp4),        "statusSM");                               // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getPath(qualProp4),              "property3/propertyA");                    // @suppress("Invalid arguments")
}


// Test path components
TEST_F(TestBaSyxID, testPathIdentifier) { // @suppress("Invalid arguments")

	// Test object identifier extraction
	ASSERT_EQ(BaSysID::getIdentifier("aas.aasid"),               "aasid");                // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getIdentifier("smID.aasid"),              "smID");                 // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getIdentifier("aas.aasid.qualified.id"),  "aasid");                // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getIdentifier("smID.aasid.qualified.id"), "smID");                 // @suppress("Invalid arguments")

	// Test path element extraction for AAS
	ASSERT_EQ(BaSysID::getIdentifier("aas.aasid/patha"),      "patha");                   // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getIdentifier("aas.aasid/patha/b"),    "b");                       // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getIdentifier("aas.aasid/patha/b/c"),  "c");                       // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getIdentifier("aas.aasid/patha/b/c/"), "c");                       // @suppress("Invalid arguments")

	// Test path element extraction for sub models
	ASSERT_EQ(BaSysID::getIdentifier("sma.aasid/patha"),      "patha");                   // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getIdentifier("sma.aasid/patha/b"),    "b");                       // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getIdentifier("sma.aasid/patha/b/c"),  "c");                       // @suppress("Invalid arguments")
	ASSERT_EQ(BaSysID::getIdentifier("sma.aasid/patha/b/c/"), "c");                       // @suppress("Invalid arguments")
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
