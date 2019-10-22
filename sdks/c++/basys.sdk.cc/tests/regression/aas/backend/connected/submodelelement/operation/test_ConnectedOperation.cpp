/*
 * test_ConnectedDataElement  .cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "backend/connected/aas/submodelelement/ConnectedRelationshipElement.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "support/VABProxyMock.cpp"

using namespace basyx::aas::backend::connected;
using namespace basyx::aas;
using namespace basyx::vab::core::proxy;

class ConnectedOperationTest : public ::testing::Test
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

TEST_F(ConnectedOperationTest, TestGetFirst)
{
  std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);

  ConnectedRelationshipElement connected_element(proxy);

  auto first = connected_element.getFirst();

  ASSERT_EQ(std::string(submodelelement::RelationshipElementPath::FIRST), mock->getElementCallValues.at(0));
  ASSERT_EQ(1, mock->overallMockCalls());
}


