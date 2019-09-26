/*
 * test_ConnectedDataElement  .cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "aas/backend/connected/submodelelement/ConnectedRelationshipElement.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "support/VABProxyMock.cpp"

using namespace basyx::aas::backend::connected;
using namespace basyx::vab::core::proxy;

class ConnectedRelationshipElementTest : public ::testing::Test
{
public:
  std::shared_ptr<IVABElementProxy> proxy;
  std::shared_ptr<mockups::VABProxyMock> mock;
protected:
  void SetUp() override
  {
    mock = std::make_shared<mockups::VABProxyMock>();
    proxy = mock;
  }
};

TEST_F(ConnectedRelationshipElementTest, TestConstructor)
{
  std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);

  ConnectedRelationshipElement connected_element(proxy);
  //
  //ASSERT_EQ(1, map->size());
  //ASSERT_EQ("object", map->at("object").Get<std::string>());
  //ASSERT_EQ(0, mock->overallMockCalls());
}

