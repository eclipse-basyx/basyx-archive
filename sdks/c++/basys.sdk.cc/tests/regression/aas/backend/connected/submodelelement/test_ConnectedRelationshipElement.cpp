/*
 * test_ConnectedRelationshipElement  .cpp
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

TEST_F(ConnectedRelationshipElementTest, TestGetFirst)
{
  std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);

  ConnectedRelationshipElement connected_element(proxy);

  auto first = connected_element.getFirst();

  ASSERT_EQ(std::string(submodelelement::RelationshipElementPath::FIRST), mock->getElementCallValues.at(0));
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedRelationshipElementTest, TestSetFirst)
{
  std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);

  ConnectedRelationshipElement connected_element(proxy);

  basyx::any first("first");

  connected_element.setFirst(first);

  ASSERT_EQ(std::string(submodelelement::RelationshipElementPath::FIRST), mock->updateElementCallValues.at(0).first);
  ASSERT_EQ("first", mock->updateElementCallValues.at(0).second.Get<std::string>());
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedRelationshipElementTest, TestGetSecond)
{
  std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);

  ConnectedRelationshipElement connected_element(proxy);

  auto second = connected_element.getSecond();

  ASSERT_EQ(std::string(submodelelement::RelationshipElementPath::SECOND), mock->getElementCallValues.at(0));
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedRelationshipElementTest, TestSetSecond)
{
  std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);

  ConnectedRelationshipElement connected_element(proxy);

  basyx::any second("second");

  connected_element.setSecond(second);

  ASSERT_EQ(std::string(submodelelement::RelationshipElementPath::SECOND), mock->updateElementCallValues.at(0).first);
  ASSERT_EQ("second", mock->updateElementCallValues.at(0).second.Get<std::string>());
  ASSERT_EQ(1, mock->overallMockCalls());
}

