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

// BaSyx includes
#include "ref/BRef.h"
#include "json/JSONTools.h"


//

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

TEST_F(TestBaSyxJSON_Deserialize, JSONTools_deserialize_string_array)
{

	std::string stringArray[3] = {"ab", "bc", "cd"};

	BArray *bArray = new BArray(stringArray,3);
	BRef<BArray> bRefArray = BRef<BArray> (bArray);

	json serVal1 = json_tools.serialize(bRefArray, 0, "");

	std::string *result;
	BArray *bResult = new BArray(result,3);
	BRef<BArray> bRefResult = BRef<BArray>(bResult);

	bRefResult = json_tools.deserialize(serVal1, NULL , "");


	ASSERT_EQ("ab", bRefResult->getMembersString()[0]);
	ASSERT_EQ("bc", bRefResult->getMembersString()[1]);
	ASSERT_EQ("cd", bRefResult->getMembersString()[2]);

}


TEST_F(TestBaSyxJSON_Deserialize, JSONTools_deserialize_integer_array)
{

	int intArray[3] = {4, 6, 8};

	BArray *bArray = new BArray(intArray,3);
	BRef<BArray> bRefArray = BRef<BArray> (bArray);

	json serVal1 = json_tools.serialize(bRefArray, 0, "");

	int *result;
	BArray *bResult = new BArray(result,3);
	BRef<BArray> bRefResult = BRef<BArray>(bResult);

	bRefResult = json_tools.deserialize(serVal1, NULL , "");


	ASSERT_EQ(4, bRefResult->getMembersInt()[0]);
	ASSERT_EQ(6, bRefResult->getMembersInt()[1]);
	ASSERT_EQ(8, bRefResult->getMembersInt()[2]);

}

TEST_F(TestBaSyxJSON_Deserialize, JSONTools_deserialize_float_array)
{

	float floatArray[3] = {2.3f, 3.4f, 4.5f};

	BArray *bArray = new BArray(floatArray,3);
	BRef<BArray> bRefArray = BRef<BArray> (bArray);

	json serVal1 = json_tools.serialize(bRefArray, 0, "");

	float *result;
	BArray *bResult = new BArray(result,3);
	BRef<BArray> bRefResult = BRef<BArray>(bResult);

	bRefResult = json_tools.deserialize(serVal1, NULL , "");


	ASSERT_EQ(2.3f, (float)bRefResult->getMembersFloat()[0]);
	ASSERT_EQ(3.4f, (float)bRefResult->getMembersFloat()[1]);
	ASSERT_EQ(4.5f, (float)bRefResult->getMembersFloat()[2]);

}


TEST_F(TestBaSyxJSON_Deserialize, JSONTools_deserialize_double_array)
{

	double doubleArray[3] = {2.300003, 3.400004, 4.400005};

	BArray *bArray = new BArray(doubleArray,3);
	BRef<BArray> bRefArray = BRef<BArray> (bArray);

	json serVal1 = json_tools.serialize(bRefArray, 0, "");

	double *result;
	BArray *bResult = new BArray(result,3);
	BRef<BArray> bRefResult = BRef<BArray>(bResult);

	bRefResult = json_tools.deserialize(serVal1, NULL , "");


	ASSERT_EQ(2.300003, (double)bRefResult->getMembersDouble()[0]);
	ASSERT_EQ(3.400004, (double)bRefResult->getMembersDouble()[1]);
	ASSERT_EQ(4.400005, (double)bRefResult->getMembersDouble()[2]);

}


