/*
 * test_Formula.cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "BaSyx/submodel/map/qualifier/qualifiable/Formula.h"
#include "BaSyx/submodel/map/modeltype/ModelType.h"
#include "BaSyx/submodel/map/reference/Reference.h"

using namespace basyx::submodel;

class FormulaTest : public ::testing::Test
{
protected:
  Formula formula;
  void SetUp() override
  {}

  void TearDown() override
  {
    auto map = formula.getMap().Get<basyx::object::object_map_t>();
    ASSERT_EQ(map.at(ModelType::Path::Name).GetStringContent(), std::string(IFormula::Path::Modeltype));
    ASSERT_NO_THROW(map.at(IFormula::Path::Dependson).Get<basyx::object::object_list_t>());
  }
};

TEST_F(FormulaTest, TestSimpleConstructor)
{}

TEST_F(FormulaTest, TestDependsOnConstructor)
{
  basyx::specificCollection_t<IReference> depends_on;
  depends_on.push_back(std::make_shared<Reference>());

  formula = Formula{depends_on};

  auto map = formula.getMap().Get<basyx::object::object_map_t>();
  ASSERT_EQ(map.at(IFormula::Path::Dependson).Get<basyx::object::object_list_t>().size(), 1);
}

TEST_F(FormulaTest, TestGetDependsOnEmpty)
{
  auto depends_on = formula.getDependsOn();
  ASSERT_EQ(depends_on.size(), 0);
}

TEST_F(FormulaTest, TestSetDependsOn)
{
  basyx::specificCollection_t<IReference> depends_on;
  depends_on.push_back(std::make_shared<Reference>());

  formula.setDependsOn(depends_on);

  auto map = formula.getMap().Get<basyx::object::object_map_t>();
  ASSERT_EQ(map.at(IFormula::Path::Dependson).Get<basyx::object::object_list_t>().size(), 1);
}

