#include <gtest/gtest.h>

#include "BaSyx/submodel/map_v2/dataspecification/DataSpecification.h"

using namespace basyx::submodel::map;
using namespace basyx::submodel;

class IdentifierTest : public ::testing::Test
{
protected:
  void SetUp() override
  {}
};

TEST_F(IdentifierTest, TestEqualityOperator_same)
{
  simple::Identifier id1(IdentifierType::IRDI, "id");
  simple::Identifier id2(IdentifierType::IRDI, "id");

  ASSERT_TRUE(id1 == id2);
}


