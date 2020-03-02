/*
 * test_HasKind.cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "BaSyx/submodel/map/qualifier/HasKind.h"

using namespace basyx::submodel;

class HasKindTest : public ::testing::Test
{
protected:
  void SetUp() override
  {}
};

TEST_F(HasKindTest, TestConstructorKindNotSpecified)
{
  HasKind hasKind;
  
  auto map = hasKind.getMap().Get<basyx::object::object_map_t>();

  ASSERT_EQ(map.at(IHasKind::Path::Kind).GetStringContent(), util::to_string(Kind::NotSpecified));
}

TEST_F(HasKindTest, TestConstructorKindSpecified)
{
  HasKind hasKind(Kind::Type);

  auto map = hasKind.getMap().Get<basyx::object::object_map_t>();

  ASSERT_EQ(map.at(IHasKind::Path::Kind).GetStringContent(), util::to_string(Kind::Type));
}

TEST_F(HasKindTest, TestObjectCopyConstructor)
{
  //set kind to Kind::Instance
  HasKind hasKind(Kind::Instance);

  //build a new haskind which should be a copy
  HasKind copied(hasKind);
  auto map = copied.getMap().Get<basyx::object::object_map_t>();
  // Kind::Instance should also be set in map
  ASSERT_EQ(map.at(IHasKind::Path::Kind).GetStringContent(), util::to_string(Kind::Instance));
}

TEST_F(HasKindTest, TestInterfaceCopyConstructor)
{
  //set kind to type
  std::shared_ptr<IHasKind> other = std::make_shared<HasKind>(Kind::Type);

  //build a new haskind which should be a copy
  HasKind hasKind(*other);
  auto map = hasKind.getMap().Get<basyx::object::object_map_t>();
  // Kind::Type should be set in map
  ASSERT_EQ(map.at(IHasKind::Path::Kind).GetStringContent(), util::to_string(Kind::Type));
}

TEST_F(HasKindTest, TestGetHasKindReference)
{
  HasKind hasKind(Kind::Type);

  auto kind = hasKind.getHasKindReference();

  ASSERT_EQ(util::to_string(kind), util::to_string(Kind::Type));
}

TEST_F(HasKindTest, TestSetHasKindReference)
{
  HasKind hasKind(Kind::Instance);

  auto & map = hasKind.getMap().Get<basyx::object::object_map_t&>();
  // Kind should be Kind::Instance now
  ASSERT_EQ(map.at(IHasKind::Path::Kind).GetStringContent(), util::to_string(Kind::Instance));

  hasKind.setHasKindReference(Kind::Type);

  // Kind should be Kind::Type now
  ASSERT_EQ(map.at(IHasKind::Path::Kind).GetStringContent(), util::to_string(Kind::Type));
}
