#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/qualifier/Referable.h>
#include "support/TestingObjects.h"

using namespace basyx::submodel;
using namespace basyx::submodel::map;

class ReferableTest : public ::testing::Test {
protected:
  void SetUp() override
  {
  }

  void TearDown() override
  {
  }
};

TEST_F(ReferableTest, TestObjectConstructor)
{
  basyx::object object = basyx::object::make_map();
  object.insertKey(Referable::Path::IdShort, "test id");
  object.insertKey(Referable::Path::Category, "testing category");
  object.insertKey(Referable::Path::Description, TestingObjects::testingLangString());

  Referable referable{object};

  ASSERT_EQ(referable.getIdShort(), "test id");
  ASSERT_EQ(*referable.getCategory(), "testing category");
  ASSERT_EQ(referable.getDescription(), LangStringSet::from_object(TestingObjects::testingLangString()));
}

TEST_F(ReferableTest, TestObjectCopy)
{
  Referable referable(std::string("test id"));
  referable.setCategory("testing category");
  referable.setDescription(TestingObjects::testingLangString());

  auto map = referable.getMap();
  Referable copied(map);

  ASSERT_EQ(copied.getIdShort(), "test id");
  ASSERT_EQ(*copied.getCategory(), "testing category");
  ASSERT_EQ(copied.getDescription(), LangStringSet::from_object(TestingObjects::testingLangString()));
}