#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/qualifier/HasDataSpecification.h>
#include "support/TestingObjects.h"

using namespace basyx::submodel;
using namespace basyx::submodel::map;

class HasDataSpecificationTest : public ::testing::Test {
protected:
  void SetUp() override
  {
  }

  void TearDown() override
  {
  }
};

TEST_F(HasDataSpecificationTest, TestObjectConstructor)
{
  basyx::object object = basyx::object::make_map();

  basyx::object list = basyx::object::make_list<basyx::object>();
  list.insert(TestingObjects::map::testingReference_1().getMap());
  list.insert(TestingObjects::map::testingReference_2().getMap());

  object.insertKey(HasDataSpecification::Path::DataSpecification, list);

  HasDataSpecification hasDataSpecification(object);

  auto & dataSpecRefs = hasDataSpecification.getDataSpecificationReference();
  ASSERT_EQ(dataSpecRefs.size(), 2);
  ASSERT_EQ(dataSpecRefs.at(0), TestingObjects::map::testingReference_1());
  ASSERT_EQ(dataSpecRefs.at(1), TestingObjects::map::testingReference_2());
}

TEST_F(HasDataSpecificationTest, TestObjectCopy)
{
  HasDataSpecification hasDataSpecification;
  hasDataSpecification.addDataSpecification(TestingObjects::map::testingReference_1());
  hasDataSpecification.addDataSpecification(TestingObjects::map::testingReference_2());

    hasDataSpecification.getDataSpecificationReference();

  auto map = hasDataSpecification.getMap();

  HasDataSpecification copied(map);

  auto & dataSpecRefs = copied.getDataSpecificationReference();
  ASSERT_EQ(dataSpecRefs.size(), 2);
  ASSERT_EQ(dataSpecRefs.at(0), TestingObjects::map::testingReference_1());
  ASSERT_EQ(dataSpecRefs.at(1), TestingObjects::map::testingReference_2());
}