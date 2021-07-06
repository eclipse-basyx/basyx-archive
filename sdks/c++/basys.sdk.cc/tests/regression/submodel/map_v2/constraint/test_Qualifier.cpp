#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/constraint/Qualifier.h>
#include "support/TestingObjects.h"

using namespace basyx::submodel;
using namespace basyx::submodel::map;

class QualifierTest : public ::testing::Test {
};

TEST_F(QualifierTest, TestObjectConstructor)
{
  auto object = ModelType<ModelTypes::Qualifier>().getMap();
  object.insertKey(Qualifier::Path::QualifierType, "Qualifier type");
  object.insertKey(Qualifier::Path::ValueType, "Value type");
  object.insertKey(Qualifier::Path::ValueDataType, "Value data type");
  object.insertKey(Qualifier::Path::ValueId, TestingObjects::map::testingReference_1().getMap());
  object.insertKey(Qualifier::Path::SemanticId, TestingObjects::map::testingReference_2().getMap());

  Qualifier qualifier{object};

  ASSERT_EQ("Qualifier type", qualifier.getQualifierType());
  ASSERT_EQ("Value type", qualifier.getValueType());
  ASSERT_EQ("Value data type", *qualifier.getValueDataType());
  ASSERT_EQ(TestingObjects::map::testingReference_1(), *qualifier.getValueId());
  ASSERT_EQ(TestingObjects::map::testingReference_2(), *qualifier.getSemanticId());
}

TEST_F(QualifierTest, TestObjectCopy)
{
  Qualifier qualifier
  {
    "Qualifier type",
    "Value type",
    "Value data type",
    TestingObjects::map::testingReference_1()
  };
  qualifier.setSemanticId(util::make_unique<Reference>(TestingObjects::map::testingReference_2()));

  auto map = qualifier.getMap();
  Qualifier copied{map};

  ASSERT_EQ("Qualifier type", qualifier.getQualifierType());
  ASSERT_EQ("Value type", qualifier.getValueType());
  ASSERT_EQ("Value data type", *qualifier.getValueDataType());
  ASSERT_EQ(TestingObjects::map::testingReference_1(), *qualifier.getValueId());
  ASSERT_EQ(TestingObjects::map::testingReference_2(), *qualifier.getSemanticId());
}