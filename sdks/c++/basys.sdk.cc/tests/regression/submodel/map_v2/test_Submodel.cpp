#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/SubModel.h>
#include <BaSyx/submodel/map_v2/aas/Asset.h>
#include <BaSyx/submodel/map_v2/aas/AssetAdministrationShell.h>


using namespace basyx::submodel;
using namespace basyx::submodel::map;
using namespace basyx::submodel::api;

class MapSubmodelTest: public ::testing::Test{
protected:
	std::unique_ptr<map::SubModel> subModel;
protected:
	void SetUp() override
	{
	}

	void TearDown() override
	{
	}
};

TEST_F(MapSubmodelTest, TestSubmodelReferences)
{
	// Create asset
	map::Asset asset{
		"asset", /* idShort */
		simple::Identifier::Custom("asset") /* identifier */ };

	// Create the asset administration shell and assign asset
	map::AssetAdministrationShell aas{
		"aas", /* idShort */
		simple::Identifier::Custom("aas"), /* identifier */
		asset };

	aas.getSubmodels().createElement<map::SubModel>("submodel", simple::Identifier::Custom("submodel"));

	auto map = aas.getMap();

	auto submodelKeys = map.getProperty("submodels");
	ASSERT_EQ(submodelKeys.GetValueType(), basyx::type::valueType::Object);
	ASSERT_EQ(submodelKeys.GetObjectType(), basyx::type::objectType::List);
	ASSERT_EQ(submodelKeys.size(), 1);

	ASSERT_EQ(aas.getSubmodels().size(), 1);
	ASSERT_TRUE(aas.getSubmodels().getElement("submodel") != nullptr);
}
