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
	        {"kind", "value"},
	        {"typeid", "character"}
	    };

	   BValue result = json_tools.deserialize(source);

	   ASSERT_EQ(result.getType(), BASYS_CHARACTER);
	   ASSERT_EQ(result.getCharacter(), 'a');
}


}
