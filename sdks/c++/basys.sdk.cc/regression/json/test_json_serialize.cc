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
#include "regression/support/gtest/gtest.h"

// BaSyx includes
#include "ref/BRef.h"
#include "json/JSONTools.h"


//

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


TEST_F(TestBaSyxJSON_Serialize, test_serialize_array_int) { // @suppress("Invalid arguments")

	int integerArray[6] = {2,4,6,8,9,10};

	BArray *bArray = new BArray(integerArray,6);
	BRef<BArray> bRefArray = BRef<BArray> (bArray);

	json serVal1 = json_tools.serialize(bRefArray, 0, "");

	ASSERT_EQ("int", serVal1["type"]);
	ASSERT_EQ(6, serVal1["size"]);
	ASSERT_EQ("array", serVal1["kind"]);

	ASSERT_EQ(2, (json_tools.getJSONObject(serVal1,"0"))["value"]);
	ASSERT_EQ(4, (json_tools.getJSONObject(serVal1,"1"))["value"]);
	ASSERT_EQ(6, (json_tools.getJSONObject(serVal1,"2"))["value"]);
	ASSERT_EQ(8, (json_tools.getJSONObject(serVal1,"3"))["value"]);
	ASSERT_EQ(9, (json_tools.getJSONObject(serVal1,"4"))["value"]);
	ASSERT_EQ(10, (json_tools.getJSONObject(serVal1,"5"))["value"]);

	ASSERT_EQ("int", (json_tools.getJSONObject(serVal1,"0"))["typeid"]);
	ASSERT_EQ("int", (json_tools.getJSONObject(serVal1,"1"))["typeid"]);
	ASSERT_EQ("int", (json_tools.getJSONObject(serVal1,"2"))["typeid"]);
	ASSERT_EQ("int", (json_tools.getJSONObject(serVal1,"3"))["typeid"]);
	ASSERT_EQ("int", (json_tools.getJSONObject(serVal1,"4"))["typeid"]);
	ASSERT_EQ("int", (json_tools.getJSONObject(serVal1,"5"))["typeid"]);

	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"0"))["kind"]);
	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"1"))["kind"]);
	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"2"))["kind"]);
	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"3"))["kind"]);
	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"4"))["kind"]);
	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"5"))["kind"]);
}



TEST_F(TestBaSyxJSON_Serialize, test_serialize_array_string) { // @suppress("Invalid arguments")

	std::string stringArray[3] = {"ab", "bc", "cd"};

	BArray *bArray = new BArray(stringArray,3);
	BRef<BArray> bRefArray = BRef<BArray> (bArray);

	json serVal1 = json_tools.serialize(bRefArray, 0, "");

	ASSERT_EQ("string", serVal1["type"]);
	ASSERT_EQ(3, serVal1["size"]);
	ASSERT_EQ("array", serVal1["kind"]);


	ASSERT_EQ("ab", (json_tools.getJSONObject(serVal1,"0"))["value"]);
	ASSERT_EQ("bc", (json_tools.getJSONObject(serVal1,"1"))["value"]);
	ASSERT_EQ("cd", (json_tools.getJSONObject(serVal1,"2"))["value"]);


	ASSERT_EQ("string", (json_tools.getJSONObject(serVal1,"0"))["typeid"]);
	ASSERT_EQ("string", (json_tools.getJSONObject(serVal1,"1"))["typeid"]);
	ASSERT_EQ("string", (json_tools.getJSONObject(serVal1,"2"))["typeid"]);

	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"0"))["kind"]);
	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"1"))["kind"]);
	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"2"))["kind"]);


}

//
TEST_F(TestBaSyxJSON_Serialize, test_serialize_array_char) { // @suppress("Invalid arguments")

	char charArray[3] = {'a', 'b', 'c'};

	BArray *bArray = new BArray(charArray,3);
	BRef<BArray> bRefArray = BRef<BArray> (bArray);

	json serVal1 = json_tools.serialize(bRefArray, 0, "");

	ASSERT_EQ("character", serVal1["type"]);
	ASSERT_EQ(3, serVal1["size"]);
	ASSERT_EQ("array", serVal1["kind"]);

	ASSERT_EQ('a', (json_tools.getJSONObject(serVal1,"0"))["value"]);
	ASSERT_EQ('b', (json_tools.getJSONObject(serVal1,"1"))["value"]);
	ASSERT_EQ('c', (json_tools.getJSONObject(serVal1,"2"))["value"]);


	ASSERT_EQ("character", (json_tools.getJSONObject(serVal1,"0"))["typeid"]);
	ASSERT_EQ("character", (json_tools.getJSONObject(serVal1,"1"))["typeid"]);
	ASSERT_EQ("character", (json_tools.getJSONObject(serVal1,"2"))["typeid"]);

	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"0"))["kind"]);
	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"1"))["kind"]);
	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"2"))["kind"]);

}

TEST_F(TestBaSyxJSON_Serialize, test_serialize_array_boolean) { // @suppress("Invalid arguments")

	bool boolArray[3] = {true, false, false};

	BArray *bArray = new BArray(boolArray,3);
	BRef<BArray> bRefArray = BRef<BArray> (bArray);

	json serVal1 = json_tools.serialize(bRefArray, 0, "");

	ASSERT_EQ("boolean", serVal1["type"]);
	ASSERT_EQ(3, serVal1["size"]);
	ASSERT_EQ("array", serVal1["kind"]);


	ASSERT_TRUE(true==(json_tools.getJSONObject(serVal1,"0"))["value"]);
	ASSERT_TRUE(false==(json_tools.getJSONObject(serVal1,"1"))["value"]);
	ASSERT_TRUE(false==(json_tools.getJSONObject(serVal1,"2"))["value"]);

	ASSERT_EQ("boolean", (json_tools.getJSONObject(serVal1,"0"))["typeid"]);
	ASSERT_EQ("boolean", (json_tools.getJSONObject(serVal1,"1"))["typeid"]);
	ASSERT_EQ("boolean", (json_tools.getJSONObject(serVal1,"2"))["typeid"]);

	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"0"))["kind"]);
	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"1"))["kind"]);
	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"2"))["kind"]);

}


TEST_F(TestBaSyxJSON_Serialize, test_serialize_array_float) { // @suppress("Invalid arguments")

	float floatArray[3] = {2.3f, 3.4f, 5.6f};

	BArray *bArray = new BArray(floatArray,3);
	BRef<BArray> bRefArray = BRef<BArray> (bArray);

	json serVal1 = json_tools.serialize(bRefArray, 0, "");

	ASSERT_EQ("float", serVal1["type"]);
	ASSERT_EQ(3, serVal1["size"]);
	ASSERT_EQ("array", serVal1["kind"]);

	ASSERT_EQ(2.3f, (float)(json_tools.getJSONObject(serVal1,"0"))["value"]);
	ASSERT_EQ(3.4f, (float)(json_tools.getJSONObject(serVal1,"1"))["value"]);
	ASSERT_EQ(5.6f, (float)(json_tools.getJSONObject(serVal1,"2"))["value"]);


	ASSERT_EQ("float", (json_tools.getJSONObject(serVal1,"0"))["typeid"]);
	ASSERT_EQ("float", (json_tools.getJSONObject(serVal1,"1"))["typeid"]);
	ASSERT_EQ("float", (json_tools.getJSONObject(serVal1,"2"))["typeid"]);

	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"0"))["kind"]);
	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"1"))["kind"]);
	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"2"))["kind"]);

}


TEST_F(TestBaSyxJSON_Serialize, test_serialize_array_double) { // @suppress("Invalid arguments")

	double doubleArray[3] = {2.30003, 3.400004, 5.600000007};

	BArray *bArray = new BArray(doubleArray,3);
	BRef<BArray> bRefArray = BRef<BArray> (bArray);

	json serVal1 = json_tools.serialize(bRefArray, 0, "");

	ASSERT_EQ("double", serVal1["type"]);
	ASSERT_EQ(3, serVal1["size"]);
	ASSERT_EQ("array", serVal1["kind"]);

	ASSERT_EQ(2.30003, (double)(json_tools.getJSONObject(serVal1,"0"))["value"]);
	ASSERT_EQ(3.400004, (double)(json_tools.getJSONObject(serVal1,"1"))["value"]);
	ASSERT_EQ(5.600000007, (double)(json_tools.getJSONObject(serVal1,"2"))["value"]);


	ASSERT_EQ("double", (json_tools.getJSONObject(serVal1,"0"))["typeid"]);
	ASSERT_EQ("double", (json_tools.getJSONObject(serVal1,"1"))["typeid"]);
	ASSERT_EQ("double", (json_tools.getJSONObject(serVal1,"2"))["typeid"]);

	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"0"))["kind"]);
	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"1"))["kind"]);
	ASSERT_EQ("value", (json_tools.getJSONObject(serVal1,"2"))["kind"]);

}

