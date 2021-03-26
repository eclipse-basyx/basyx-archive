#include <gtest/gtest.h>

#include <BaSyx/submodel/api_v2/aas/IAssetAdministrationShell.h>
#include <BaSyx/submodel/map_v2/aas/AssetAdministrationShell.h>
#include <BaSyx/submodel/simple/aas/AssetAdministrationShell.h>

#include <BaSyx/submodel/map_v2/SubModel.h>

#include <BaSyx/util/util.h>

using namespace basyx::submodel;

using ImplTypes = ::testing::Types<
    std::tuple<map::AssetAdministrationShell, map::Asset>,
    std::tuple<simple::AssetAdministrationShell, simple::Asset>>;

template<class Impl>
class AssetAdministrationShellTest :public ::testing::Test {
protected:
  using impl_t = typename std::tuple_element<0, Impl>::type;
  using asset_t = typename std::tuple_element<1, Impl>::type;

	std::unique_ptr<api::IAssetAdministrationShell> aas;
protected:
	void SetUp() override
	{
		aas = util::make_unique<impl_t>("testAas",
			simple::Identifier::Custom("testAas"), 
			asset_t{ "testAsset", simple::Identifier::Custom("testAsset") }
			);
	}

	void TearDown() override
	{	}
};

TYPED_TEST_CASE(AssetAdministrationShellTest, ImplTypes);

TYPED_TEST(AssetAdministrationShellTest, TestModelType)
{
	ASSERT_EQ(this->aas->GetModelType(), basyx::submodel::ModelTypes::AssetAdministrationShell);
};

TYPED_TEST(AssetAdministrationShellTest, TestAddSubmodel)
{
	auto submodel = this->aas->getSubmodels().template createElement<basyx::submodel::map::SubModel>("testSubmodel", simple::Identifier::Custom("testSubmodel"));

	ASSERT_EQ(this->aas->getSubmodels().size(), 1);

	return;
};
