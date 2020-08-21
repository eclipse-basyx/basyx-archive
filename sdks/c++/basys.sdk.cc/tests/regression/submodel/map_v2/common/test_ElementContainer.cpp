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


TEST_F(ElementContainerTest, TestCreateDeleteAfterwards)
{
	auto prop = this->elementContainer.createElement<Property<int>>("testProperty");
	ASSERT_EQ(this->elementContainer.size(), 1);
	prop->setValue(5);

	// Delete in map
	auto && objectMap = this->elementContainer.getMap();
	objectMap.removeProperty("testProperty");
	ASSERT_EQ(this->elementContainer.size(), 0);
	auto prop_b = this->elementContainer.getElement("testProperty");
	ASSERT_EQ(prop_b, nullptr);
};