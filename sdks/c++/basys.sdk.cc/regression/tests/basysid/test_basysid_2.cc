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



//* (7) <submodel>/submodel/..						--> <submodel>
//* (8) <scope>/<submodel>/submodel/..				--> <submodel>


// Test retrieving element ID of scoped Asset Administration Shell IDs
TEST(TestBaSyxID, testGetElementID) {

	// Build example paths
	std::string aasID = BaSysID::buildAASID("AAS1");
	std::string aasIDScoped = BaSysID::buildAASID("s1.scope/AAS1");

	std::string aasIDSubmodels = "AAS1/aas/submodels";
	std::string aasIDSubmodelsScoped = "s1.scope/AAS1/aas/submodels";
	
	std::string submodelIDSubmodels = "AAS1/aas/submodels/SM1";
	std::string submodelIDSubmodelsScoped = "s1.scope/AAS1/aas/submodels/SM1";
	
	std::string submodelID = "SM1/submodel/P1";
	std::string submodelIDScoped = "s1.scope/SM1/submodel/P1";
	
	// Checks
	ASSERT_EQ(aasID, "AAS1/aas");
	ASSERT_EQ(aasIDScoped, "s1.scope/AAS1/aas");
	
	ASSERT_EQ(BaSysID::getElementID(aasID), "AAS1");
	ASSERT_EQ(BaSysID::getElementID(aasIDScoped), "AAS1");
	
	ASSERT_EQ(BaSysID::getElementID(aasIDSubmodels), "AAS1");
	ASSERT_EQ(BaSysID::getElementID(aasIDSubmodelsScoped), "AAS1");
	
	ASSERT_EQ(BaSysID::getElementID(submodelIDSubmodels), "SM1");
	ASSERT_EQ(BaSysID::getElementID(submodelIDSubmodelsScoped), "SM1");
	
	ASSERT_EQ(BaSysID::getElementID(submodelID), "SM1");
	ASSERT_EQ(BaSysID::getElementID(submodelIDScoped), "SM1");
}

// Test retrieving element qualified ID of scoped Asset Administration Shell IDs
TEST(TestBaSyxID, testGetElementQualifiedID) {

	// Build example paths
	std::string aasID = BaSysID::buildAASID("AAS1");
	std::string aasIDScoped = BaSysID::buildAASID("s1.scope/AAS1");

	std::string aasIDSubmodels = "AAS1/aas/submodels";
	std::string aasIDSubmodelsScoped = "s1.scope/AAS1/aas/submodels";
	
	std::string submodelIDSubmodels = "AAS1/aas/submodels/SM1";
	std::string submodelIDSubmodelsScoped = "s1.scope/AAS1/aas/submodels/SM1";
	
	std::string submodelID = "SM1/submodel/P1";
	std::string submodelIDScoped = "s1.scope/SM1/submodel/P1";
	
	// Checks
	ASSERT_EQ(aasID, "AAS1/aas");
	ASSERT_EQ(aasIDScoped, "s1.scope/AAS1/aas");
	
	ASSERT_EQ(BaSysID::getQualifiedElementID(aasID), "AAS1");
	ASSERT_EQ(BaSysID::getQualifiedElementID(aasIDScoped), "s1.scope/AAS1");
	
	ASSERT_EQ(BaSysID::getQualifiedElementID(aasIDSubmodels), "AAS1");
	ASSERT_EQ(BaSysID::getQualifiedElementID(aasIDSubmodelsScoped), "s1.scope/AAS1");
	
	ASSERT_EQ(BaSysID::getQualifiedElementID(submodelIDSubmodels), "SM1");
	ASSERT_EQ(BaSysID::getQualifiedElementID(submodelIDSubmodelsScoped), "s1.scope/SM1");
	
	ASSERT_EQ(BaSysID::getQualifiedElementID(submodelID), "SM1");
	ASSERT_EQ(BaSysID::getQualifiedElementID(submodelIDScoped), "s1.scope/SM1");
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
