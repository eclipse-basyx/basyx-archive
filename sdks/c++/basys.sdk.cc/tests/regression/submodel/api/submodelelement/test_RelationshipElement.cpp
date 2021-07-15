#include <gtest/gtest.h>

#include <BaSyx/submodel/api_v2/submodelelement/IRelationshipElement.h>
#include <BaSyx/submodel/simple/submodelelement/RelationshipElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/RelationshipElement.h>
#include <BaSyx/submodel/simple/reference/Key.h>

using namespace basyx::submodel;

// Implementations to run tests for
using ImplTypes = ::testing::Types<
	std::tuple<simple::RelationshipElement, simple::Reference>,
	std::tuple<map::RelationshipElement, map::Reference>
>;

template<class Impl>
class RelationshipElementTest :public ::testing::Test {
public:
  using impl_t = typename std::tuple_element<0, Impl>::type;
  using reference_t = typename std::tuple_element<1, Impl>::type;

protected:
	std::unique_ptr<api::IRelationshipElement> relationshipElement;
  std::unique_ptr<simple::Key> firstKey;
  std::unique_ptr<simple::Key> secondKey;

protected:
	void SetUp() override
	{
	  this->firstKey = util::make_unique<simple::Key>(basyx::submodel::KeyElements::SubmodelElement, true, KeyType::IdShort, "first value");
	  this->secondKey = util::make_unique<simple::Key>(KeyElements::RelationshipElement, false, KeyType::IdShort, "first value");

    reference_t first(*firstKey);
    reference_t second(*secondKey);

    this->relationshipElement = util::make_unique<impl_t>(first, second, "test relationshipElement");
	}

	void TearDown() override
	{
	}
};

TYPED_TEST_CASE(RelationshipElementTest, ImplTypes);

TYPED_TEST(RelationshipElementTest, TestConstructor)
{
  ASSERT_EQ(this->relationshipElement->getIdShort(), "test relationshipElement");
	ASSERT_EQ(this->relationshipElement->getFirst().getKeys().at(0), *this->firstKey);
  ASSERT_EQ(this->relationshipElement->getSecond().getKeys().at(0), *this->secondKey);
}
