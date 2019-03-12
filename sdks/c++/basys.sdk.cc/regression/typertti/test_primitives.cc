/*
 * test_primitives.cc
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
#include "types/BaSysTypes.h"
#include "types/BValue.h"



/* ************************************************
 * Test class
 * ************************************************/
class TestRTTIBaSyxPrimitives : public ::testing::Test {

};



/* ************************************************
 * Test cases
 * ************************************************/

// Test values and RTTI
TEST_F(TestRTTIBaSyxPrimitives, testValues) { // @suppress("Invalid arguments")
	// Assign values
	BValue int1(12);
	BValue int2(13);
	BValue float1(12.3f);
	BValue double1(12.3);
	BValue char1('a');
	BValue bool1(true);

	// Check RTTI
	ASSERT_EQ(int1.getType(), BASYS_INT);
	ASSERT_EQ(int2.getType(), BASYS_INT);
	ASSERT_EQ(float1.getType(), BASYS_FLOAT);
	ASSERT_EQ(double1.getType(), BASYS_DOUBLE);
	ASSERT_EQ(char1.getType(), BASYS_CHARACTER);
	ASSERT_EQ(bool1.getType(), BASYS_BOOLEAN);

	// Check values
	ASSERT_EQ(int1.getInt(), 12);
	ASSERT_EQ(int2.getInt(), 13);
	ASSERT_EQ(float1.getFloat(), 12.3f);
	ASSERT_EQ(double1.getDouble(), 12.3);
	ASSERT_EQ(char1.getCharacter(), 'a');
	ASSERT_EQ(bool1.getBoolean(), true);

	// Re-assign values
	int1.setInt(17);
	int2.setBoolean(true);

	// Test RTTI again
	ASSERT_EQ(int1.getType(), BASYS_INT);
	ASSERT_EQ(int2.getType(), BASYS_BOOLEAN);

	// Test values again
	ASSERT_EQ(int1.getInt(), 17);
	ASSERT_EQ(int2.getBoolean(), true);
}



// Test meta data
TEST_F(TestRTTIBaSyxPrimitives, testMetaData) { // @suppress("Invalid arguments")
	// Assign values
	BValue int1(12);
	BValue int2(13);
	BValue float1(12.3f);
	BValue double1(12.3);
	BValue char1('a');
	BValue bool1(true);


	// Test meta data
	ASSERT_EQ(int1.isNull(),  false);
	ASSERT_EQ(int1.isValue(), true);
	ASSERT_EQ(int1.isArray(), false);
	ASSERT_EQ(int1.isMap(), false);
	ASSERT_EQ(int1.isCollection(), false);
	ASSERT_EQ(int1.isIElement(), false);
	ASSERT_EQ(int1.isObject(), false);
}

