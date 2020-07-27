#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/common/ElementContainer.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/property/Property.h>

using namespace basyx::submodel;
using namespace basyx::submodel::map;
using namespace basyx::submodel::api;

class ElementContainerTest : public ::testing::Test {
protected:
	ElementContainer<ISubmodelElement> elementContainer;
protected:
	void SetUp() override
	{
	}

	void TearDown() override
	{
	}
};

TEST_F(ElementContainerTest, TestInit)
{
	ASSERT_EQ(this->elementContainer.size(), 0);
}


TEST_F(ElementContainerTest, TestAdd)
{
	auto prop = util::make_unique<Property<int>>( "testProperty" );
	prop->setValue(5);

	this->elementContainer.addElement(std::move(prop));
	ASSERT_EQ(this->elementContainer.size(), 1);

	auto submodelElement = this->elementContainer.getElement("testProperty");
	auto propB = dynamic_cast<map::Property<int>*>(submodelElement);
	ASSERT_NE(propB, nullptr);
	ASSERT_EQ(propB->getValue(), 5);
};

TEST_F(ElementContainerTest, TestCreate)
{
	auto prop = this->elementContainer.createElement<Property<int>>("testProperty");
	ASSERT_EQ(this->elementContainer.size(), 1);
	prop->setValue(5);

	auto submodelElement = this->elementContainer.getElement("testProperty");
	auto propB = dynamic_cast<map::Property<int>*>(submodelElement);
	ASSERT_NE(propB, nullptr);
	ASSERT_EQ(propB->getValue(), 5);
};

TEST_F(ElementContainerTest, TestCreateDeleteAfterwards)
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

