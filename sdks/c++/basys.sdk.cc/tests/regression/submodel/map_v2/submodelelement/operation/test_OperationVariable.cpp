#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/submodelelement/operation/OperationVariable.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElementFactory.h>
#include "support/TestingObjects.h"

using namespace basyx::submodel;
using namespace basyx::submodel::map;

class OperationVariableTest : public ::testing::Test {};

TEST_F(OperationVariableTest, TestObjectConstructor)
{
  auto object = TestingObjects::object::testingSubmodelElement();
  object.insertKey(OperationVariable::Path::Value, TestingObjects::map::testingFile().getMap());

  // build from object
  OperationVariable ov{object};

  ASSERT_TRUE(TestingObjects::object::testingSubmodelElement(ov));
  ASSERT_TRUE(TestingObjects::map::testingFile(ov.getValue()));
}

TEST_F(OperationVariableTest, TestCopiedObject)
{
  OperationVariable ov {"test id", util::make_unique<File>(TestingObjects::map::testingFile())};

  OperationVariable copied {ov.getMap()};

  ASSERT_EQ(copied.getIdShort(), "test id");

  ASSERT_TRUE(TestingObjects::map::testingFile(copied.getValue()));
}

TEST_F(OperationVariableTest, TestSubmodelElementFactory)
{
  auto object = TestingObjects::object::testingSubmodelElement();
  object.insertKey(OperationVariable::Path::Value, TestingObjects::map::testingFile().getMap());

  object.insert(ModelType<ModelTypes::OperationVariable>().getMap());

  // build from object
  auto se = SubmodelElementFactory::Create(object);
  OperationVariable & ov = dynamic_cast<OperationVariable&>(*se);

  ASSERT_TRUE(TestingObjects::object::testingSubmodelElement(ov));
  ASSERT_TRUE(TestingObjects::map::testingFile(ov.getValue()));
}