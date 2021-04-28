#include <gtest/gtest.h>

#include <BaSyx/submodel/api_v2/submodelelement/IEntity.h>
#include <BaSyx/submodel/simple/submodelelement/Entity.h>
#include <BaSyx/submodel/map_v2/submodelelement/Entity.h>

#include <BaSyx/submodel/simple/submodelelement/property/ReferenceElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/property/ReferenceElement.h>


using namespace basyx::submodel;

// Implementations to run tests for
using ImplTypes = ::testing::Types<
  std::tuple<simple::Entity, simple::ReferenceElement, simple::Reference>,
  std::tuple<map::Entity, map::ReferenceElement, map::Reference>
>;

template<class Impl>
class EntityTest :public ::testing::Test {
protected:
  using impl_t = typename std::tuple_element<0, Impl>::type;
  using referenceElement_t = typename std::tuple_element<1, Impl>::type;
  using reference_t = typename std::tuple_element<2, Impl>::type;

  std::unique_ptr<api::IEntity> entity;

protected:
  void SetUp() override
  {
    this->entity = util::make_unique<impl_t>(EntityType::CoManagedEntity, "id");
  }

  void TearDown() override
  {
  }
};

TYPED_TEST_CASE(EntityTest, ImplTypes);

TYPED_TEST(EntityTest, TestConstructor)
{
  ASSERT_EQ(this->entity->getEntityType(), EntityType::CoManagedEntity);
  ASSERT_EQ(this->entity->getAssetRef(), nullptr);
  ASSERT_EQ(this->entity->getStatement().size(), 0);
}

TYPED_TEST(EntityTest, TestStatement)
{
  using impl_t = typename TestFixture::impl_t;
  using referenceElement_t = typename TestFixture::referenceElement_t;

  impl_t test_entity(EntityType::CoManagedEntity, "test");

  auto re = util::make_unique<referenceElement_t>("ReferenceElement");

  test_entity.addStatement(std::move(re));

  ASSERT_EQ(test_entity.getStatement().size(), 1);
  ASSERT_EQ(test_entity.getStatement().getElement(0)->getIdShort(), "ReferenceElement");
}

TYPED_TEST(EntityTest, TestAssetRef)
{
  using impl_t = typename TestFixture::impl_t;
  using reference_t = typename TestFixture::reference_t;

  simple::Key key{KeyElements::Entity, true, KeyType::URI, "value"};
  reference_t assetRef(key);

  impl_t test_entity(EntityType::CoManagedEntity, "test");

  test_entity.setAssetRef(assetRef);

  ASSERT_EQ(test_entity.getAssetRef()->getKeys().at(0), key);
}