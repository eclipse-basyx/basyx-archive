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
// Test classes


/**
 * Test value serialization
 */
class TestBaSyxJSON_Serialize : public ::testing::Test {

	protected:
		JSONTools json_tools;
};






/////////////////////////////////////////////////////////////////
// Test cases



/**
 * Test serialization of null values
 */
TEST_F(TestBaSyxJSON_Serialize, test_serialize_null) { // @suppress("Invalid arguments")

	// Define expected result
	json expectedResult =
	{
			{"kind", "null"}
	};

	// Check if expected result matches outcome of serialization
	ASSERT_EQ(expectedResult, json_tools.serialize(BRef<BNullObject>(new BNullObject()), 0, ""));

}



/**
 * Test serialization of primitive integer value
 */
TEST_F(TestBaSyxJSON_Serialize, test_serialize_primitive_int) { // @suppress("Invalid arguments")

	// Define expected result
	json expectedResult =
	{
			{"value", 1},
			{"kind", "value"},
			{"typeid", "int"}
	};

	// Check if expected result matches outcome of serialization
	ASSERT_EQ(expectedResult, json_tools.serialize(1, 0, ""));
}



/**
 * Test serialization of primitive float value
 */
TEST_F(TestBaSyxJSON_Serialize, test_serialize_primitive_float) { // @suppress("Invalid arguments")

	// Define expected result
	json expectedResult =
		   	 {
		   	     {"value", 13.14f},
		   	     {"kind", "value"},
		   	     {"typeid", "float"}
		   	 };

	// Check if expected result matches outcome of serialization
	ASSERT_EQ(expectedResult, json_tools.serialize(13.14f, 0, ""));
}



/**
 * Test serialization of primitive double value
 */
TEST_F(TestBaSyxJSON_Serialize, test_serialize_primitive_double) { // @suppress("Invalid arguments")

	// Define expected result
	json expectedResult =
		   	 {
		   	     {"value", 13.30003},
		   	     {"kind", "value"},
		   	     {"typeid", "double"}
		   	 };

	// Check if expected result matches outcome of serialization
	ASSERT_EQ(expectedResult, json_tools.serialize(13.30003, 0, ""));
}



/**
 * Test serialization of primitive character value
 */
TEST_F(TestBaSyxJSON_Serialize, test_serialize_primitive_char) { // @suppress("Invalid arguments")

	// Define expected result
	json expectedResult =
		   	 {
		   	     {"value", 'a'},
		   	     {"kind", "value"},
		   	     {"typeid", "character"}
		   	 };

	// Check if expected result matches outcome of serialization
	ASSERT_EQ(expectedResult, json_tools.serialize('a', 0, ""));
}



/**
 * Test serialization of primitive string value
 */
TEST_F(TestBaSyxJSON_Serialize, test_serialize_primitive_string) { // @suppress("Invalid arguments")

	// Define expected result
	json expectedResult =
		   	 {
		   	     {"value", "abcdefg"},
		   	     {"kind", "value"},
		   	     {"typeid", "string"}
		   	 };

	// Check if expected result matches outcome of serialization
	ASSERT_EQ(expectedResult, json_tools.serialize("abcdefg", 0, ""));
}



/**
 * Test serialization of primitive boolean value
 */
TEST_F(TestBaSyxJSON_Serialize, test_serialize_primitive_boolean) { // @suppress("Invalid arguments")

	// Define expected result
	json expectedResult =
		   	 {
		   	     {"value", true},
		   	     {"kind", "value"},
		   	     {"typeid", "boolean"}
		   	 };


	// Check if expected result matches outcome of serialization
	ASSERT_EQ(expectedResult, json_tools.serialize(true, 0, ""));
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


