/*
 * test_null.cc
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
#include "types/BaSysTypes.h"
#include "types/BNullObject.h"



/* ************************************************
 * Test class
 * ************************************************/
class TestRTTIBaSyxNull : public ::testing::Test {

};



/* ************************************************
 * Test cases
 * ************************************************/

// Test values and RTTI
TEST_F(TestRTTIBaSyxNull, testValues) { // @suppress("Invalid arguments")
	// Assign values
	BNullObject  nullValue;

	// Check RTTI
	ASSERT_EQ(nullValue.getType(), BASYS_NULL);
}



// Test meta data
TEST_F(TestRTTIBaSyxNull, testMetaData) { // @suppress("Invalid arguments")
	// Assign values
	BNullObject nullValue;

	// Test meta data
	ASSERT_EQ(nullValue.isNull(),  true);
	ASSERT_EQ(nullValue.isValue(), false);
	ASSERT_EQ(nullValue.isArray(), false);
	ASSERT_EQ(nullValue.isMap(), false);
	ASSERT_EQ(nullValue.isCollection(), false);
	ASSERT_EQ(nullValue.isIElement(), false);
	ASSERT_EQ(nullValue.isObject(), false);
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
