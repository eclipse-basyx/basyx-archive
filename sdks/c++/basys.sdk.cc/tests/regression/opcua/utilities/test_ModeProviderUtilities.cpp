#include "gtest/gtest.h"
#include <BaSyx/opcua/server/Server.h>
#include <BaSyx/vab/core/util/VABPath.h>
#include <BaSyx/opcua/aas/provider/AASAggregatorProviderHelpers.h>

namespace basyx
{
	namespace tests
	{
		namespace opcua
		{
			using namespace basyx::opcua;
			using namespace basyx::opcua::aas;
			using namespace basyx::opcua::aas::metamodel;
			using namespace basyx::submodel::map;

			class OPCUAModeProviderUtilitiesTest : public ::testing::Test
			{
			};

			TEST_F(OPCUAModeProviderUtilitiesTest, aasAPI)
			{
				using namespace basyx::vab::core;
				using namespace basyx::opcua::aas;

				basyx::vab::core::VABPath pathAAS("shells/xyz/aas");
				basyx::vab::core::VABPath pathNoAAS("shells/xyz");

				ASSERT_TRUE(AASProviderApiParseHelpers::isApiShellsAASIdAAS(pathAAS));
				ASSERT_FALSE(AASProviderApiParseHelpers::isApiShellsAASId(pathAAS));

				ASSERT_FALSE(AASProviderApiParseHelpers::isApiShellsAASIdAAS(pathNoAAS));
				ASSERT_TRUE(AASProviderApiParseHelpers::isApiShellsAASId(pathNoAAS));
			}

			TEST_F(OPCUAModeProviderUtilitiesTest, submodelAPI)
			{
				using namespace basyx::vab::core;
				using namespace basyx::opcua::aas;

				basyx::vab::core::VABPath path1("shells/xyz/aas");

				basyx::vab::core::VABPath path_isAPISubmodelsIdShort("shells/xyz/aas/submodels/abc");
				basyx::vab::core::VABPath path_isAPISubmodels("shells/xyz/aas/submodels");
				basyx::vab::core::VABPath path_isAPISubmodelsSubmodel("shells/xyz/aas/submodels/abc/submodel");
				basyx::vab::core::VABPath path_isAPISubmodelValues("shells/xyz/aas/submodels/abc/submodel/values");
				basyx::vab::core::VABPath path_isAPISubmodelElements("shells/xyz/aas/submodels/abc/submodel/submodelElements");
				basyx::vab::core::VABPath path_isAPISubmodelElementsIdShort("shells/xyz/aas/submodels/abc/submodel/submodelElements/jkl");
				basyx::vab::core::VABPath path_isAPISubmodelElementsValue("shells/xyz/aas/submodels/abc/submodel/submodelElements/jkl/value");
				basyx::vab::core::VABPath path_isAPISubmodelElementsInvoke("shells/xyz/aas/submodels/abc/submodel/submodelElements/jkl/invoke");

				/* API : shells/{aasID}/aas/submodels*/

				ASSERT_TRUE(AASProviderApiParseHelpers::isAPISubmodelsIdShort(path_isAPISubmodelsIdShort));

				ASSERT_TRUE(AASProviderApiParseHelpers::isAPISubmodels(path_isAPISubmodels));

				ASSERT_TRUE(AASProviderApiParseHelpers::isAPISubmodelsSubmodel(path_isAPISubmodelsSubmodel));

				ASSERT_TRUE(AASProviderApiParseHelpers::isAPISubmodelValues(path_isAPISubmodelValues));

				ASSERT_TRUE(AASProviderApiParseHelpers::isAPISubmodelElements(path_isAPISubmodelElements));

				ASSERT_TRUE(AASProviderApiParseHelpers::isAPISubmodelElementsIdShort(path_isAPISubmodelElementsIdShort));

				ASSERT_TRUE(AASProviderApiParseHelpers::isAPISubmodelElementsValue(path_isAPISubmodelElementsValue));

				ASSERT_TRUE(AASProviderApiParseHelpers::isAPISubmodelElementsInvoke(path_isAPISubmodelElementsInvoke));
			}
		}
	}
}
