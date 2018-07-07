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
 * Test value deserialization
 */
class TestBaSyxJSON_Deserialize : public ::testing::Test {

	protected:
		JSONTools json_tools;
};





/////////////////////////////////////////////////////////////////
// Test serialization of primitive values
TEST_F(TestBaSyxJSON_Deserialize, test_deserializePrimitiveValues_int) { // @suppress("Invalid arguments")

	// Example value
	json source =
	{
			{"value", 4},
			{"kind", "value"},
			{"typeid", "int"}
	};

	// Deserialize value
	BRef<BValue> result = json_tools.deserialize(source, 0, "");

	// Check result
	ASSERT_EQ(result->getType(), BASYS_INT);
	ASSERT_EQ(result->getInt(), 4);
}


TEST_F(TestBaSyxJSON_Deserialize, test_deserializePrimitiveValues_float) { // @suppress("Invalid arguments")

	// Example value
	json source =
	{
			{"value", 2.0f},
			{"kind", "value"},
			{"typeid", "float"}
	};

	// Deserialize value
	BRef<BValue> result = json_tools.deserialize(source, 0, "");

	// Check result
	ASSERT_EQ(result->getType(), BASYS_FLOAT);
	ASSERT_EQ(result->getFloat(), 2.0f);
}


TEST_F(TestBaSyxJSON_Deserialize, test_deserializePrimitiveValues_bool) { // @suppress("Invalid arguments")

	// Example value
	json source =
	{
			{"value", true},
			{"kind", "value"},
			{"typeid", "boolean"}
	};

	// Deserialize value
	BRef<BValue> result = json_tools.deserialize(source, 0, "");

	// Check result
	ASSERT_EQ(result->getType(), BASYS_BOOLEAN);
	ASSERT_EQ(result->getBoolean(), true);
}


TEST_F(TestBaSyxJSON_Deserialize, testPrimitiveValues_char) { // @suppress("Invalid arguments")

	// Example value
	json source =
	{
			{"value", "a"},
			{"kind", "value"},
			{"typeid", "character"}
	};

	// Deserialize value
	BRef<BValue> result = json_tools.deserialize(source, 0, "");

	// Check result
	ASSERT_EQ(result->getType(), BASYS_CHARACTER);
	ASSERT_EQ(result->getCharacter(), 'a');
}


TEST_F(TestBaSyxJSON_Deserialize, testPrimitiveValues_string) { // @suppress("Invalid arguments")

	// Example value
	json source =
	{
			{"value", "abcdefgh"},
			{"kind", "value"},
			{"typeid", "string"}
	};

	// Deserialize value
	BRef<BString> result = json_tools.deserialize(source, 0, "");

	// Check result
	ASSERT_EQ(result->getType(), BASYS_STRING);
	ASSERT_EQ(result->getString(), "abcdefgh");
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


