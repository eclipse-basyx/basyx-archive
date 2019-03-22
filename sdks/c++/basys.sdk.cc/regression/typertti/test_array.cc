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

// BaSyx
#include "types/BaSysTypes.h"
#include "types/BArray.h"



/* ************************************************
 * Test class
 * ************************************************/
class TestRTTIBaSyxArray : public ::testing::Test {

};



/* ************************************************
 * Test cases
 * ************************************************/

// Test values and RTTI
TEST_F(TestRTTIBaSyxArray, testValues) { // @suppress("Invalid arguments")
	// Assign values
	int    a[] = {1,2,3};
	float  b[] = {3.0f, 4.0f, 4.2f};
	double c[] = {3.0, 4.0, 4.2};
	bool   d[] = {true, false};
	char   e[] = {'a', 'b', 'c'};

	BArray intArray1(a, 3);
	BArray floatArray1(b, 3);
	BArray doubleArray1(c, 3);
	BArray boolArray1(d, 2);
	BArray charArray1(e, 3);

	// Check RTTI
	ASSERT_EQ(intArray1.getType(), BASYS_INTARRAY);
	ASSERT_EQ(floatArray1.getType(), BASYS_FLOATARRAY);
	ASSERT_EQ(doubleArray1.getType(), BASYS_DOUBLEARRAY);
	ASSERT_EQ(boolArray1.getType(), BASYS_BOOLEANARRAY);
	ASSERT_EQ(charArray1.getType(), BASYS_CHARACTERARRAY);

	ASSERT_EQ(intArray1.getBaseType(), BASYS_INT);
	ASSERT_EQ(floatArray1.getBaseType(), BASYS_FLOAT);
	ASSERT_EQ(doubleArray1.getBaseType(), BASYS_DOUBLE);
	ASSERT_EQ(boolArray1.getBaseType(), BASYS_BOOLEAN);
	ASSERT_EQ(charArray1.getBaseType(), BASYS_CHARACTER);

	// Check Array size
	ASSERT_EQ(intArray1.getArraySize(), 3);
	ASSERT_EQ(floatArray1.getArraySize(), 3);
	ASSERT_EQ(doubleArray1.getArraySize(), 3);
	ASSERT_EQ(boolArray1.getArraySize(), 2);
	ASSERT_EQ(charArray1.getArraySize(), 3);

	// Check Array members
	ASSERT_EQ(intArray1.getMembersInt()[0], 1);
	ASSERT_EQ(intArray1.getMembersInt()[1], 2);
	ASSERT_EQ(intArray1.getMembersInt()[2], 3);
	ASSERT_EQ(floatArray1.getMembersFloat()[0], 3.0f);
	ASSERT_EQ(floatArray1.getMembersFloat()[1], 4.0f);
	ASSERT_EQ(floatArray1.getMembersFloat()[2], 4.2f);
	ASSERT_EQ(doubleArray1.getMembersDouble()[0], 3.0);
	ASSERT_EQ(doubleArray1.getMembersDouble()[1], 4.0);
	ASSERT_EQ(doubleArray1.getMembersDouble()[2], 4.2);
	ASSERT_EQ(boolArray1.getMembersBool()[0], true);
	ASSERT_EQ(boolArray1.getMembersBool()[1], false);
	ASSERT_EQ(charArray1.getMembersChar()[0], 'a');
	ASSERT_EQ(charArray1.getMembersChar()[1], 'b');
	ASSERT_EQ(charArray1.getMembersChar()[2], 'c');
}



// Test meta data
TEST_F(TestRTTIBaSyxArray, testMetaData) { // @suppress("Invalid arguments")
	// Assign values
	int    a[] = {1,2,3};
	BArray intArray1(a, 3);

	// Test meta data
	ASSERT_EQ(intArray1.isNull(),  false);
	ASSERT_EQ(intArray1.isValue(), false);
	ASSERT_EQ(intArray1.isArray(), true);
	ASSERT_EQ(intArray1.isMap(), false);
	ASSERT_EQ(intArray1.isCollection(), false);
	ASSERT_EQ(intArray1.isIElement(), false);
	ASSERT_EQ(intArray1.isObject(), false);
}

