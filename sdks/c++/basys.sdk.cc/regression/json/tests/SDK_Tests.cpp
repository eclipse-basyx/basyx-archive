/*
 * JSONTests.cpp
 *
 *  Created on: Mar 17, 2018
 *      Author: cioroaic
 */

#include "gtest/gtest.h"
#include "json/JSONTools.h"


namespace {
class JSOnTest:public::testing::Test
{
protected:
	JSONTools json_tools;
};

TEST_F(JSOnTest, JSONTools_test_deserialize)
{
	   json source =
	    {
	        {"value", "a"},
	        {"kind", ""},
	        {"typeid", ""}
	    };

	   ASSERT_EQ("a", json_tools.deserializePrimitive(source));

}

TEST_F(JSOnTest, JSONTools_serialize_int)
{
	   json source =
	    {
	        {"value", ""},
	        {"kind", ""},
	        {"typeid", ""}
	    };

	   json target =
	   	    {
	   	        {"value", 1},
	   	        {"kind", "value"},
	   	        {"typeid", "int"}
	   	    };


	   ASSERT_EQ(target, json_tools.serializePrimitive(target,1,"int"));

}

TEST_F(JSOnTest, JSONTools_serialize_float)
{
	   json source =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };

	   json target =
	   	    {
	   	        {"value", 1.0},
	   	        {"kind", "value"},
	   	        {"typeid", "float"}
	   	    };

	   ASSERT_EQ(target,json_tools.serializePrimitive(source, 1.0, "float"));

}

TEST_F(JSOnTest, JSONTools_serialize_double)
{


	   json source =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };

	   json target =
	   	    {
	   	        {"value", 2.00032},
	   	        {"kind", "value"},
	   	        {"typeid", "double"}
	   	    };
	   ASSERT_EQ(target,json_tools.serializePrimitive(source, 2.00032, "double"));

}


TEST_F(JSOnTest, JSONTools_serialize_string)
{


	   json source1 =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };

	   json target1 =
	   	    {
	   	        {"value", "abcdefg"},
	   	        {"kind", "value"},
	   	        {"typeid", "string"}
	   	    };
	   //a cast to std::string is necessary,otherwise a boolean value is serialized
	   ASSERT_EQ(target1,json_tools.serializePrimitive(source1, (std::string) "abcdefg", "string"));

}


TEST_F(JSOnTest, JSONTools_serialize_boolean)
{


	   json source =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };

	   json target =
	   	    {
	   	        {"value", true},
	   	        {"kind", "value"},
	   	        {"typeid", "boolean"}
	   	    };
	   ASSERT_EQ(target,json_tools.serializePrimitive(source, true, "boolean"));

}

TEST_F(JSOnTest, JSONTools_serialize_character)
{


	   json source =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };

	   json target =
	   	    {
	   	        {"value", 'a'},
	   	        {"kind", "value"},
	   	        {"typeid", "character"}
	   	    };
	   ASSERT_EQ(target,json_tools.serializePrimitive(source, 'a', "character"));

}


TEST_F(JSOnTest, JSONTools_serialize_NULL_int)
{
	   json source =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };

	   json serObjRepo;
	   ASSERT_TRUE(json_tools.serializeNull(source, std::numeric_limits<int>::quiet_NaN(), serObjRepo));

}

TEST_F(JSOnTest, JSONTools_serialize_NULL_float)
{
	   json source =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };

	   json serObjRepo;
	   ASSERT_FALSE(json_tools.serializeNull(source, 3.2f, serObjRepo)); // cannot compare two NaN for equality, willget false.

}


TEST_F(JSOnTest, JSONTools_serialize_NULL_double)
{
	   json source =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };
	   json serObjRepo;
	   ASSERT_FALSE(json_tools.serializeNull(source, 3.14159265358979, serObjRepo));

}

TEST_F(JSOnTest, JSONTools_serialize_NULL_string_)
{
	   json source =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };
	   json serObjRepo;
	   ASSERT_FALSE(json_tools.serializeNull(source, (std::string)"abc", serObjRepo));
}

TEST_F(JSOnTest, JSONTools_serialize_NULL_string)
{
	   json source =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };
	   json serObjRepo;
	   ASSERT_TRUE(json_tools.serializeNull(source, (std::string)"", serObjRepo));
}

TEST_F(JSOnTest, JSONTools_serialize_NULL_bool)
{
	   json source =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };
	   json serObjRepo;
	   ASSERT_FALSE(json_tools.serializeNull(source, true, serObjRepo));
}

TEST_F(JSOnTest, JSONTools_serialize_NULL_character)
{
	   json source =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };
	   json serObjRepo;
	   ASSERT_FALSE(json_tools.serializeNull(source, 'c', serObjRepo));
}

TEST_F(JSOnTest, JSONTools_serialize_NULL_character_)
{
	   json source =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };
	   json serObjRepo;
	   ASSERT_TRUE(json_tools.serializeNull(source, '\0', serObjRepo));
}

TEST_F(JSOnTest, JSONTools_deserialize_NULL)
{
	   json source =
	   {
			{"value", ""},
			{"kind", "null"},
			{"typeid", ""}
	   };
	   ASSERT_FALSE(json_tools.deserializeNull(source, '\0'));
}

TEST_F(JSOnTest, JSONTools_serializeArray)
{
	   json source =
	   {
			{"value", ""},
			{"kind", "null"},
			{"typeid", ""}
	   };

	   int array[5];
	   array[0] = 10;
	   array[1] = 11;
	   array[2] = 12;
	   array[3] = 13;
	   array[4] = 14;

	   ASSERT_TRUE(json_tools.deserializeNull(source, '\0'));
}
}
