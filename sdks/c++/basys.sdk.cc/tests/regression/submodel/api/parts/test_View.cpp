#include <gtest/gtest.h>
#include <tuple>

#include <BaSyx/submodel/map_v2/parts/View.h>
#include <BaSyx/submodel/simple/parts/View.h>

#include <BaSyx/util/util.h>

using namespace basyx::submodel;

using ImplTypes = ::testing::Types
<
  std::tuple<map::View, map::Referable>,
  std::tuple<simple::View, simple::Referable>
>;

template<class Impl>
class ViewTest :public ::testing::Test {
protected:
  using impl_t = typename std::tuple_element<0, Impl>::type;
  using impl_ref_t = typename std::tuple_element<1, Impl>::type;

  std::unique_ptr<impl_t> view;
  std::unique_ptr<impl_ref_t> referable;
protected:
	void SetUp() override
	{
	    view = util::make_unique<impl_t>("TestView");
        referable = util::make_unique<impl_ref_t>("TestReferable");
	}

	void TearDown() override
	{}
};

TYPED_TEST_CASE(ViewTest, ImplTypes);

TYPED_TEST(ViewTest, TestConstructor)
{
  ASSERT_EQ(this->view->getIdShort(), std::string("TestView"));
}

TYPED_TEST(ViewTest, TestContainedElements)
{
  this->view->addContainedElement(std::move(this->referable));

  auto & contained_elements = this->view->getContainedElements();

  ASSERT_NE(contained_elements.getElement("TestReferable"), nullptr);
}

TYPED_TEST(ViewTest, TestSemanticId)
{
  std::unique_ptr<api::IReference> ref = util::make_unique<simple::Reference>();

  simple::Key key(KeyElements::Submodel, false, KeyType::URI, "test");
  ref->addKey(key);

  this->view->setSemanticId(*ref);

  ASSERT_EQ(this->view->getSemanticId().getKeys().at(0).getValue(), std::string("test"));
}