#include "gtest/gtest.h"
#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/aas/metamodel/AASMetamodel.h>
#include <BaSyx/opcua/aas/api_v2/ApiSubmodel.h>
#include <tests/regression/opcua/OPCUATestSetup.h>

namespace basyx
{
    namespace tests
    {
        namespace opcua_sm_api
        {
            using namespace basyx::opcua;
            using namespace basyx::submodel;
            using namespace aas::metamodel;

            class ApiSubmodel_test : public ::testing::Test
            {

            public:
                std::unique_ptr<opcua::OPCUATestSetup> m_opcuaSetup;
                const int serverPort = 6005;
            public:

                virtual void SetUp()
                {
                    m_opcuaSetup = util::make_unique<opcua::OPCUATestSetup>(serverPort);
                }
                
                virtual void TearDown()
                {
                    m_opcuaSetup->getServer().abort();
                }

                /* Build a Submodel  */
                std::unique_ptr<map::SubModel> SM()
                {
                    auto sm = util::make_unique<map::SubModel>("foo_sm", simple::Identifier::Custom("foo_sm_ident"));
                    /* Set Administration attributes */
                    sm->setAdministrativeInformation(basyx::submodel::map::AdministrativeInformation("1", "2"));
                    
                    // Add some properties
                    auto prop_i32 = util::make_unique<map::Property<int>>("prop_i32");
                    prop_i32->setValue(23);
                    prop_i32->setCategory("VARAIBLE");

                    auto prop_str = util::make_unique<map::Property<std::string>>("prop_str");
                    prop_str->setValue("string property");
                    prop_str->setCategory("CONSTANT");
                    
                    sm->submodelElements().addElement(std::move(prop_i32));
                    sm->submodelElements().addElement(std::move(prop_str));

                    return std::move(sm);
                }
            };

            TEST_F(ApiSubmodel_test, api)
            {
                using namespace basyx::opcua::aas;

                auto sm = SM();

                ApiSubmodel<Client> api(m_opcuaSetup->getClient(), NodeId::numeric(UA_NS0ID_OBJECTSFOLDER));

                ASSERT_EQ(UA_STATUSCODE_GOOD, api.createSubmodel(*sm.get()));
                auto smNew = api.getSubmodel("foo_sm");
                ASSERT_NE(smNew, nullptr);
                ASSERT_NE(smNew->getAdministrativeInformation().getRevision(), nullptr);
                ASSERT_EQ(*smNew->getAdministrativeInformation().getRevision(), "2");
                ASSERT_NE(smNew->getAdministrativeInformation().getVersion(), nullptr);
                ASSERT_EQ(*smNew->getAdministrativeInformation().getVersion(), "1");

                auto prop = api.getAllProperties("foo_sm");

                for(const auto& prop : api.getAllProperties("foo_sm"))
                {
                    if(prop->getIdShort() == "prop_i32")
                    {
                        ASSERT_EQ(prop->getValueType(), "xsd:int");
                        ASSERT_EQ(dynamic_cast<map::Property<int>*>(prop.get())->getValue(), 23);
                    }
                    if(prop->getIdShort() == "prop_str")
                    {
                        ASSERT_EQ(prop->getValueType(), "xsd:string");
                        ASSERT_EQ(dynamic_cast<map::Property<std::string>*>(prop.get())->getValue(), "string property");
                    }
                }

                ASSERT_EQ(UA_STATUSCODE_GOOD, api.deleteSubmodel("foo_sm"));

            }
        }
    }
}