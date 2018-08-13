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
#include "regression/support/gtest/gtest.h"

// BaSyx
#include "basysid/BaSysID.h"

/* ************************************************
 * Test cases
 * ************************************************/

// Test retrieving of unscoped submodel IDs
TEST(TestBaSyxID, testUnscopedSubModels) {

	// Build example paths
	std::string smID = BaSysID::buildSMID("SM1");

	// Checks
	ASSERT_EQ(smID, "SM1/submodel");
	ASSERT_EQ(BaSysID::getAASID(smID), "");
	ASSERT_EQ(BaSysID::getSubmodelID(smID), "SM1");
	ASSERT_EQ(BaSysID::getPath(smID), "");
}

// Test retrieving of scoped submodel IDs
TEST(TestBaSyxID, testScopedSubModels) {

	// Build example paths
	std::string smID = BaSysID::buildSMID("s1.scope/SM1");

	// Checks
	ASSERT_EQ(smID, "s1.scope/SM1/submodel");
	ASSERT_EQ(BaSysID::getSubmodelID(smID), "SM1");
	ASSERT_EQ(BaSysID::getPath(smID), "");
}

// Test retrieving element ID and qualified element ID of scoped Asset Administration Shell IDs
TEST(TestBaSyxID, testGetElementID) {

	// Build example paths
	std::string aasID1 = BaSysID::buildAASID("AAS1");
	std::string aasID2 = BaSysID::buildAASID("s1.scope/AAS1");

	// Checks
	ASSERT_EQ(aasID1, "AAS1/aas");
	ASSERT_EQ(aasID2, "s1.scope/AAS1/aas");
}

// Test retrieving element ID and qualified element ID of scoped sub model IDs
TEST(TestBaSyxID, testGetElementID_SM) {

	// Build example paths
	std::string smID1 = BaSysID::buildSMID("smid");
	std::string smID2 = BaSysID::buildSMID("s1.scope/smid");

	// Checks
	ASSERT_EQ(smID1, "smid/submodel");
	ASSERT_EQ(smID2, "s1.scope/smid/submodel");
}

TEST(TestBaSyxID, testGetScope) {
	std::string aas = "s1.scope/AAS1/aas/submodels/SM1/properties";
	std::vector<std::string> scope = BaSysID::getScope(aas);
	ASSERT_EQ(scope.size(), (size_t ) 2);
	ASSERT_EQ(scope[0], "s1");
	ASSERT_EQ(scope[1], "scope");
}

TEST(TestBaSyxID, testGetScopeNoScope) {
	std::string aas = "AAS1/aas/submodels/SM1/properties";
	std::vector<std::string> scope = BaSysID::getScope(aas);
	ASSERT_EQ(scope.size(), (size_t ) 0);
}

TEST(TestBaSyxID, testGetAddress) {
	std::string aas = "s1.scope/AAS1/aas/submodels/SM1/properties";
	std::string address = BaSysID::getAddress(aas);
	ASSERT_EQ(address, "s1.scope/AAS1/aas/submodels/SM1");
}

TEST(TestBaSyxID, testGetAddressNoScope) {
	std::string aas = "AAS1/aas/submodels/SM1/properties";
	std::string address = BaSysID::getAddress(aas);
	ASSERT_EQ(address, "AAS1/aas/submodels/SM1");
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
