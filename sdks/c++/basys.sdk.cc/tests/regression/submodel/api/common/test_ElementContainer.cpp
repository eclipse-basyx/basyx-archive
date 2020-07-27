#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/common/ElementContainer.h>
#include <BaSyx/submodel/simple/common/ElementContainer.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/property/Property.h>
#include <BaSyx/submodel/simple/qualifier/Referable.h>

using namespace basyx::submodel;
using namespace basyx::submodel::map;
using namespace basyx::submodel::api;

using ImplTypes = ::testing::Types<map::ElementContainer<ISubmodelElement>, simple::ElementContainer<ISubmodelElement>>;

template<class Impl>
class ElementContainerTest : public ::testing::Test {
protected:
  using impl_t = Impl;

protected:
	std::unique_ptr<IElementContainer<ISubmodelElement>> elementContainer;
protected:
	void SetUp() override
	{
	  elementContainer = util::make_unique<Impl>();
	}

	void TearDown() override
	{
	}
};

TYPED_TEST_CASE(ElementContainerTest, ImplTypes);

TYPED_TEST(ElementContainerTest, TestInit)
{
	ASSERT_EQ(this->elementContainer->size(), 0);
}


TYPED_TEST(ElementContainerTest, TestAdd)
{
	auto prop = util::make_unique<Property<int>>( "testProperty" );
	prop->setValue(5);

	this->elementContainer->addElement(std::move(prop));
	ASSERT_EQ(this->elementContainer->size(), 1);

	auto submodelElement = this->elementContainer->getElement("testProperty");
	auto propB = dynamic_cast<map::Property<int>*>(submodelElement);
	ASSERT_NE(propB, nullptr);
	ASSERT_EQ(propB->getValue(), 5);
};

TYPED_TEST(ElementContainerTest, TestCreate)
{
	auto prop = this->elementContainer->template createElement<Property<int>>("testProperty");
	ASSERT_EQ(this->elementContainer->size(), 1);
	prop->setValue(5);

	auto submodelElement = this->elementContainer->getElement("testProperty");
	auto propB = dynamic_cast<map::Property<int>*>(submodelElement);
	ASSERT_NE(propB, nullptr);
	ASSERT_EQ(propB->getValue(), 5);
};

/*********************************************************
 * Map implementation tests
 *********************************************************/

class MapElementContainerTest : public ::testing::Test {
protected:
  map::ElementContainer<ISubmodelElement> elementContainer;
protected:
  void SetUp() override
  {
  }

  void TearDown() override
  {
  }
};

TEST_F(MapElementContainerTest, TestMapImplementationCreateDeleteAfterwards)
{
	auto prop = this->elementContainer.createElement<Property<int>>("testProperty");
	ASSERT_EQ(this->elementContainer.size(), 1);
	prop->setValue(5);

	// Delete in map
	auto & objectList = this->elementContainer.getMap().Get<basyx::object::object_list_t&>();
	objectList.clear();
	ASSERT_EQ(this->elementContainer.size(), 0);
	auto prop_b = this->elementContainer.getElement("testProperty");
	ASSERT_EQ(prop_b, nullptr);
};


/*********************************************************
 * Simple implementation tests
 *********************************************************/
class SimpleElementContainerTest : public ::testing::Test {
protected:
  simple::ElementContainer<IReferable> elementContainer;
protected:
  void SetUp() override
  {}

  void TearDown() override
  {}
};

TEST_F(SimpleElementContainerTest, TestSimpleImplementationCopy)
{
//  std::unique_ptr<IReferable> ref = util::make_unique<simple::Referable>("testRef");
//  ref->setCategory("testCategory");
//  this->elementContainer.addElement(std::unique_ptr<IReferable>(ref.release()));
//  ASSERT_EQ(this->elementContainer.size(), 1);
//
//  simple::ElementContainer<IReferable> elementContainer2(std::move(elementContainer));
//  auto prop2 = dynamic_cast<simple::Referable*>(elementContainer2.getElement("testRef"));
//  ASSERT_EQ(elementContainer2->size(), 1);
//  ASSERT_EQ(prop2->getCategory(), std::string("testCategory"));
};

