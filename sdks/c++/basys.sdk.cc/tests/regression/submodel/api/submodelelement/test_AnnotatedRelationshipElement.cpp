#include <gtest/gtest.h>

#include <BaSyx/submodel/api_v2/submodelelement/IAnnotatedRelationshipElement.h>
#include <BaSyx/submodel/simple/submodelelement/AnnotatedRelationshipElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/AnnotatedRelationshipElement.h>

#include <BaSyx/submodel/simple/submodelelement/property/ReferenceElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/property/ReferenceElement.h>

using namespace basyx::submodel;

// Implementations to run tests for
using ImplTypes = ::testing::Types<
	std::tuple<simple::AnnotatedRelationshipElement, simple::Referable, simple::ReferenceElement>,
	std::tuple<map::AnnotatedRelationshipElement, map::Referable, map::ReferenceElement>
>;

template<class Impl>
class AnnotatedRelationshipElementTest :public ::testing::Test {
public:
  using impl_t = typename std::tuple_element<0, Impl>::type;
  using referable_t = typename std::tuple_element<1, Impl>::type;
  using referenceElement_t = typename std::tuple_element<2, Impl>::type;

protected:
	std::unique_ptr<impl_t> annotatedRelationshipElement;
protected:
	void SetUp() override
	{
	  referable_t first(std::string("first"));
	  referable_t second(std::string("second"));
		this->annotatedRelationshipElement = util::make_unique<impl_t>(first, second, "test annotatedRelationshipElement");
	}

	void TearDown() override
	{
	}
};

TYPED_TEST_CASE(AnnotatedRelationshipElementTest, ImplTypes);

TYPED_TEST(AnnotatedRelationshipElementTest, TestConstructor)
{
  ASSERT_EQ(this->annotatedRelationshipElement->getIdShort(), "test annotatedRelationshipElement");
}

TYPED_TEST(AnnotatedRelationshipElementTest, TestAddAnnotation)
{
  using referenceElement_t = typename TestFixture::referenceElement_t;

  auto annotation = util::make_unique<referenceElement_t>("test reference element");
  this->annotatedRelationshipElement->addAnnotation(std::move(annotation));

  ASSERT_EQ(this->annotatedRelationshipElement->getAnnotation().getElement(0)->getIdShort(), "test reference element");
}
