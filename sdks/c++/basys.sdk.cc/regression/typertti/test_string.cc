/*
 * test_string.cc
 *
 *  Created on: 05.05.2018
 *      Author: kuhn
 */




/* ************************************************
 * Includes
 * ************************************************/

// GTest
#include "gtest/gtest.h"

// BaSyx
#include "types/BaSysTypes.h"
#include "types/BString.h"



/* ************************************************
 * Test class
 * ************************************************/
class TestRTTIBaSyxString : public ::testing::Test {

};



/* ************************************************
 * Test cases
 * ************************************************/

// Test values and RTTI
TEST_F(TestRTTIBaSyxString, testValues) { // @suppress("Invalid arguments")
	// Assign values
	BString string1("12");

	// Check RTTI
	ASSERT_EQ(string1.getType(), BASYS_STRING);

	// Check values
	ASSERT_EQ(string1.getString(), "12");    // @suppress("Invalid arguments")

	// Re-assign values
	string1.setString("abc");                // @suppress("Invalid arguments")

	// Test RTTI again
	ASSERT_EQ(string1.getType(), BASYS_STRING);

	// Test values again
	ASSERT_EQ(string1.getString(), "abc");   // @suppress("Invalid arguments")
}



// Test meta data
TEST_F(TestRTTIBaSyxString, testMetaData) { // @suppress("Invalid arguments")
	// Assign values
	BString string1("12");


	// Test meta data
	ASSERT_EQ(string1.isNull(),  false);
	ASSERT_EQ(string1.isValue(), true);
	ASSERT_EQ(string1.isArray(), false);
	ASSERT_EQ(string1.isMap(), false);
	ASSERT_EQ(string1.isCollection(), false);
	ASSERT_EQ(string1.isIElement(), false);
	ASSERT_EQ(string1.isObject(), false);
}
