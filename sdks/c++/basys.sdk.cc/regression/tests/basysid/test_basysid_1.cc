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

// Test building of AAS IDs
TEST(TestBaSyxID, testBuildAASID) {

	// Build example paths
	std::string qualAASID1 = BaSysID::buildAASID(
			"Stub1AAS.scopepart.scopepart.topscope");
	std::string qualAASID2 = BaSysID::buildAASID(
			"aas.Stub1AAS.scopepart.scopepart.topscope");
	std::string aasID1 = BaSysID::buildAASID("Stub1AAS");
	std::string aasID2 = BaSysID::buildAASID("aas.Stub1AAS");

	// Checks
	ASSERT_EQ(qualAASID1, "Stub1AAS.scopepart.scopepart.topscope/aas");
	ASSERT_EQ(qualAASID2, "aas.Stub1AAS.scopepart.scopepart.topscope/aas");
	ASSERT_EQ(aasID1, "Stub1AAS/aas");
	ASSERT_EQ(aasID2, "aas.Stub1AAS/aas");
}

// Test building of submodel IDs
TEST(TestBaSyxID, testBuildSubmodelID) {

	// Build example paths
	std::string smID1 = BaSysID::buildSMID("statusSM");
	std::string smID2 = BaSysID::buildSMID("statusSM.Stub1AAS");
	std::string smID3 = BaSysID::buildSMID("statusSM.Stub1AAS.scope");

	// Checks
	ASSERT_EQ(smID1, "statusSM/submodel");
	ASSERT_EQ(smID2, "statusSM.Stub1AAS/submodel");
	ASSERT_EQ(smID3, "statusSM.Stub1AAS.scope/submodel");
}

// Test building of paths
TEST(TestBaSyxID, testBuildPath) {

	// Build example paths
	std::string qualSMID = BaSysID::buildPath("Stub1AAS", "statusSM");

	std::string scope[] = { "s1", "scope" };
	std::string qualSMID2 = BaSysID::buildPath(scope, 2, "Stub1AAS",
			"statusSM");

	std::string qualSMIDa = BaSysID::buildPath("Stub1AAS", "");
	std::string qualSMIDb = BaSysID::buildPath(scope, 2, "SM1");



	// Checks
	ASSERT_EQ(qualSMID, "Stub1AAS/aas/submodels/statusSM");
	ASSERT_EQ(qualSMID2, "s1.scope/Stub1AAS/aas/submodels/statusSM");
	ASSERT_EQ(qualSMIDa, "Stub1AAS/aas");
	ASSERT_EQ(qualSMIDb, "s1.scope/SM1/submodel");
}

// Test path components
TEST(TestBaSyxID, testPathComponents) {

	// Build example paths
	std::string qualAASID1 = BaSysID::buildAASID(
			"scopepart.scopepart.topscope/Stub1AAS");
	std::string qualAASID2 = BaSysID::buildAASID(
			"scopepart.scopepart.topscope/Stub2AAS")
			+ "/submodels/SM1";

	std::string qualAASID3 = BaSysID::buildAASID(
			"scopepart.scopepart.topscope/Stub3AAS")
			+ "/submodels/SM1/properties";

	std::string aasID1 = BaSysID::buildAASID("Stub1AAS");
	std::string smID = BaSysID::buildSMID("statusSM");
	std::string qualSMID = BaSysID::buildPath("Stub1AAS", "statusSM");
	std::string qualSMID2 = BaSysID::buildPath("topscope/Stub1AAS", "statusSM");

	// Check path components
	ASSERT_EQ(BaSysID::getAASID(qualAASID1), "Stub1AAS");
	ASSERT_EQ(BaSysID::getSubmodelID(qualAASID1), "");
	ASSERT_EQ(BaSysID::getPath(qualAASID1), "");

	ASSERT_EQ(BaSysID::getAASID(qualAASID2), "Stub2AAS");
	ASSERT_EQ(BaSysID::getSubmodelID(qualAASID2), "SM1");
	ASSERT_EQ(BaSysID::getPath(qualAASID2), "");

	ASSERT_EQ(BaSysID::getAASID(qualAASID3), "Stub3AAS");
	ASSERT_EQ(BaSysID::getSubmodelID(qualAASID3), "SM1");
	ASSERT_EQ(BaSysID::getPath(qualAASID3), "properties");

	ASSERT_EQ(BaSysID::getAASID(aasID1), "Stub1AAS");
	ASSERT_EQ(BaSysID::getSubmodelID(aasID1), "");
	ASSERT_EQ(BaSysID::getPath(aasID1), "");

	ASSERT_EQ(BaSysID::getAASID(smID), "");
	ASSERT_EQ(BaSysID::getSubmodelID(smID), "statusSM");
	ASSERT_EQ(BaSysID::getPath(smID), "");

	ASSERT_EQ(BaSysID::getAASID(qualSMID), "Stub1AAS");
	ASSERT_EQ(BaSysID::getSubmodelID(qualSMID), "statusSM");
	ASSERT_EQ(BaSysID::getPath(qualSMID), "");

	ASSERT_EQ(BaSysID::getAASID(qualSMID2), "Stub1AAS");
	ASSERT_EQ(BaSysID::getSubmodelID(qualSMID2), "statusSM");
	ASSERT_EQ(BaSysID::getPath(qualSMID2), "");
}

// Test paths elements
TEST(TestBaSyxID, testPathElements) {

	// Create path with trailing '/' and extract elements
	std::string path = BaSysID::getPath(
			"s1.scope/aasid/aas/submodels/SM1/properties/prop1");
	std::vector<std::string> pathElements = BaSysID::splitPropertyPath(path);

	// Check path elements
	// - Used iterator
	std::vector<std::string>::const_iterator iterator = pathElements.begin();

	// - Check list elements
	ASSERT_EQ(*(iterator++), "prop1");
	ASSERT_EQ(iterator, pathElements.end());
}
