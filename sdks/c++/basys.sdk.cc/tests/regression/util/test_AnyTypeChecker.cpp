/*
 * anyTypeChecker.h
 *
 *      Author: wendel
 */

#include "gtest/gtest.h"

#include "basyx/anyTypeChecker.h"

#include <basyx/serialization/json.h>

class TestAnyTypeChecker : public ::testing::Test
{
};

TEST_F(TestAnyTypeChecker, AnyIsString)
{
  basyx::any string = std::string("this is a string");

  ASSERT_EQ(basyx::getPropertyTypeInfo(string), basyx::PropertyTypeInfo::STRING);
}

TEST_F(TestAnyTypeChecker, AnyIsInt)
{
  basyx::any integer = 2;

  ASSERT_EQ(basyx::getPropertyTypeInfo(integer), basyx::PropertyTypeInfo::INT);
}

TEST_F(TestAnyTypeChecker, AnyIsCollection)
{
  basyx::any collection = basyx::objectCollection_t();

  ASSERT_EQ(basyx::getPropertyTypeInfo(collection), basyx::PropertyTypeInfo::COLLECTION);
}

TEST_F(TestAnyTypeChecker, AnyIsMap)
{
  basyx::any map = basyx::objectMap_t();

  ASSERT_EQ(basyx::getPropertyTypeInfo(map), basyx::PropertyTypeInfo::MAP);
}

TEST_F(TestAnyTypeChecker, AnyIsBool)
{
  basyx::any boolean = true;

  ASSERT_EQ(basyx::getPropertyTypeInfo(boolean), basyx::PropertyTypeInfo::BOOL);
}

TEST_F(TestAnyTypeChecker, AnyIsDouble)
{
  basyx::any anyDouble = (double) 0.0;

  ASSERT_EQ(basyx::getPropertyTypeInfo(anyDouble), basyx::PropertyTypeInfo::DOUBLE);
}

TEST_F(TestAnyTypeChecker, AnyIsFloat)
{
  basyx::any anyFloat = 0.0F;

  ASSERT_EQ(basyx::getPropertyTypeInfo(anyFloat), basyx::PropertyTypeInfo::FLOAT);
}

TEST_F(TestAnyTypeChecker, AnyIsNull)
{
  basyx::any anyNull = nullptr;

  ASSERT_EQ(basyx::getPropertyTypeInfo(anyNull), basyx::PropertyTypeInfo::PROPNULL);
}

TEST_F(TestAnyTypeChecker, AnyIsNoneType)
{
  basyx::any notKnown = uint16_t();

  ASSERT_EQ(basyx::getPropertyTypeInfo(notKnown), basyx::PropertyTypeInfo::NONETYPE);
}
