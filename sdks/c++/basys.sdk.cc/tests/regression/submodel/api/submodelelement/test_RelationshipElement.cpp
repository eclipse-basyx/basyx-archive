#include <gtest/gtest.h>

#include <BaSyx/submodel/api_v2/submodelelement/IRelationshipElement.h>
#include <BaSyx/submodel/simple/submodelelement/RelationshipElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/RelationshipElement.h>

using namespace basyx::submodel;

// Implementations to run tests for
using ImplTypes = ::testing::Types<
	std::tuple<simple::RelationshipElement, simple::Referable>,
	std::tuple<map::RelationshipElement, map::Referable>
>;

template<class Impl>
class RelationshipElementTest :public ::testing::Test {
public:
  using impl_t = typename std::tuple_element<0, Impl>::type;
  using referable_t = typename std::tuple_element<1, Impl>::type;
protected:
	std::unique_ptr<api::IRelationshipElement> relationshipElement;
protected:
	void SetUp() override
	{
	  referable_t first("first");
	  referable_t second("second");
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
	ASSERT_EQ(this->relationshipElement->getFirst().getIdShort(), "first");
  ASSERT_EQ(this->relationshipElement->getSecond().getIdShort(), "second");
}
