/*
 * test_Constraint.cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "BaSyx/submodel/map/qualifier/qualifiable/Constraint.h"
#include "BaSyx/submodel/map/modeltype/ModelType.h"

using namespace basyx::submodel;

class ConstraintTest : public ::testing::Test
{
protected:
  Constraint constraint;
  void SetUp() override
  {}

  void TearDown() override
  {
  }
};

TEST_F(ConstraintTest, TestSimpleConstructor)
{}

TEST_F(ConstraintTest, TestConstructionFromObject)
{
  auto map = basyx::object::make_map();
  map.insertKey(ModelType::Path::Name, IConstraint::Path::ModelType);
  map.insertKey("test", "test");

  constraint = Constraint(map);

  auto cmap = constraint.getMap().Get<basyx::object::object_map_t>();
  ASSERT_EQ(cmap.at("test").GetStringContent(), "test");
}


TEST_F(ConstraintTest, TestConstructionOtherConstraint)
{
  Constraint c2{constraint};

  auto map = c2.getMap().Get<basyx::object::object_map_t>();
}
