/*
 * TestAAS.cc
 *
 * Regression test case for BaSys Reference (BRef) class
 *
 *      Author: kuhn
 */



/////////////////////////////////////////////////////////////////
// Includes

// GTest
#include "gtest/gtest.h"
#include "gtest/gtest-all.cc"

// BaSyx includes
#include "ref/BRef.h"
#include "json/JSONTools.h"




/////////////////////////////////////////////////////////////////
// Test class
class TestBaSyxJSON : public ::testing::Test {

};





/////////////////////////////////////////////////////////////////
// Test serialization of null values
TEST_F(TestBaSyxJSON, testNullValues) { // @suppress("Invalid arguments")

}


/////////////////////////////////////////////////////////////////
// Test serialization of primitive values
TEST_F(TestBaSyxJSON, testPrimitiveValues) { // @suppress("Invalid arguments")

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


