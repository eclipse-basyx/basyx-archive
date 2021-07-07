#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/qualifier/Qualifiable.h>
#include "support/TestingObjects.h"

using namespace basyx::submodel;
using namespace basyx::submodel::map;

class QualifiableTest : public ::testing::Test {};

TEST_F(QualifiableTest, TestObjectConstructor)
{
  basyx::object object = basyx::object::make_map();

  basyx::object::object_list_t qualifier_list;
  qualifier_list.push_back(TestingObjects::map::testingQualifier().getMap());
  qualifier_list.push_back(TestingObjects::map::testingFormula().getMap());
  qualifier_list.push_back(TestingObjects::map::testingQualifier(2).getMap());

  object.insertKey(Qualifiable::Path::Qualifier, qualifier_list);

  Qualifiable qualifiable{object};

  auto formulas = qualifiable.getFormulas();
  ASSERT_TRUE(TestingObjects::map::testingFormula(formulas.at(0)));

  auto qualifiers = qualifiable.getQualifiers();
  ASSERT_TRUE(TestingObjects::map::testingQualifier(qualifiers.at(0)));
  ASSERT_TRUE(TestingObjects::map::testingQualifier(qualifiers.at(1), 2));
}

TEST_F(QualifiableTest, TestObjectCopy)
{
  Qualifiable qualifiable;
  qualifiable.addQualifier(TestingObjects::map::testingQualifier(2));
  qualifiable.addFormula(TestingObjects::map::testingFormula());
  qualifiable.addQualifier(TestingObjects::map::testingQualifier());


  auto map = qualifiable.getMap();
  Qualifiable copied(map);

  auto formulas = copied.getFormulas();
  ASSERT_TRUE(TestingObjects::map::testingFormula(formulas.at(0)));

  auto qualifiers = copied.getQualifiers();
  ASSERT_TRUE(TestingObjects::map::testingQualifier(qualifiers.at(0), 2));
  ASSERT_TRUE(TestingObjects::map::testingQualifier(qualifiers.at(1)));
}