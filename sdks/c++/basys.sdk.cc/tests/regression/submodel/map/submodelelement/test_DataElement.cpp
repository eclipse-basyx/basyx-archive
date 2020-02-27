/*
 * test_DataElement.cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "BaSyx/submodel/map/submodelelement/DataElement.h"

#include "support/AdditionalAssertions.hpp"


using namespace basyx::submodel;

class DataElementTest : public ::testing::Test
{
protected:
  DataElement data_element;

  void SetUp() override
  {}

  void TearDown() override
  {
    auto map = data_element.getMap();
    basyx::assertions::AssertIsModelType(map, IDataElement::Path::ModelType);
  }
};

TEST_F(DataElementTest, TestSimpleConstructor)
{}

TEST_F(DataElementTest, TestConstructFromObject)
{
  basyx::object obj = ModelType(IDataElement::Path::ModelType).getMap();
  obj.insertKey("testKey", "testValue");

  data_element = DataElement(obj);

  //tear down checks if ModelType is set
  basyx::assertions::AssertMapContainsValue<std::string>(data_element.getMap(), "testKey", "testValue");
}


