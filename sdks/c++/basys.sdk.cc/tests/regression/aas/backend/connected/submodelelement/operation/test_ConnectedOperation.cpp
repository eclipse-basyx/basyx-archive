/*
 * test_ConnectedDataElement  .cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "aas/submodelelement/operation/IOperation.h"
#include "backend/connected/aas/submodelelement/operation/ConnectedOperation.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "support/VABProxyMock.cpp"

using namespace basyx::aas::backend::connected;
using namespace basyx::aas;
using namespace basyx::vab::core::proxy;

class ConnectedOperationTest : public ::testing::Test
{
public:
  std::shared_ptr<IVABElementProxy> proxy;
  std::shared_ptr<mockups::VABProxyMockCollection> mock;
protected:
  void SetUp() override
  {
    mock = std::make_shared<mockups::VABProxyMockCollection>();
    proxy = mock;
  }
};

TEST_F(ConnectedOperationTest, TestGetParameterTypes)
{
  std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);

  ConnectedOperation connected_operation(proxy);

  auto types = connected_operation.getParameterTypes();

  ASSERT_EQ(submodelelement::operation::OperationPaths::INPUT, mock->getElementCallValues.at(0));
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedOperationTest, TestGetReturnTypes)
{
  std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);

  ConnectedOperation connected_operation(proxy);

  auto types = connected_operation.getReturnTypes();

  ASSERT_EQ(submodelelement::operation::OperationPaths::OUTPUT, mock->getElementCallValues.at(0));
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedOperationTest, TestInvoke)
{
  std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);

  ConnectedOperation connected_operation(proxy);

  basyx::objectCollection_t collection;
  collection.push_back(basyx::any(2));

  auto invoked = connected_operation.invoke(collection);

  ASSERT_EQ("called with ", invoked.Get<std::string>());
  ASSERT_EQ(1, mock->invokeCallParameter.size());
  ASSERT_EQ(2, mock->invokeCallParameter.at(0).Get<int>());
  ASSERT_EQ(1, mock->overallMockCalls());
}


