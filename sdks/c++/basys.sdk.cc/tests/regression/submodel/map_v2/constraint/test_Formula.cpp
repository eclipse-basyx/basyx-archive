#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/constraint/Formula.h>
#include "support/TestingObjects.h"

using namespace basyx::submodel;
using namespace basyx::submodel::map;

class FormulaTest : public ::testing::Test {};

TEST_F(FormulaTest, TestObjectConstructor)
{
  basyx::object object = basyx::object::make_map();

  object::object_list_t dependencies;
  dependencies.push_back(TestingObjects::map::testingReference_1().getMap());
  dependencies.push_back(TestingObjects::map::testingReference_2().getMap());
  object.insertKey(Formula::Path::Dependencies, dependencies);

  Formula formula{object};

  auto formula_dependencies = formula.getDependencies();
  ASSERT_EQ(formula_dependencies.at(0), TestingObjects::map::testingReference_1());
  ASSERT_EQ(formula_dependencies.at(1), TestingObjects::map::testingReference_2());
}

TEST_F(FormulaTest, TestObjectCopy)
{
  Formula formula;

  formula.addDependency(TestingObjects::map::testingReference_1());
  formula.addDependency(TestingObjects::map::testingReference_2());

  Formula copied{formula.getMap()};

  auto formula_dependencies = copied.getDependencies();
  ASSERT_EQ(formula_dependencies.at(0), TestingObjects::map::testingReference_1());
  ASSERT_EQ(formula_dependencies.at(1), TestingObjects::map::testingReference_2());

}