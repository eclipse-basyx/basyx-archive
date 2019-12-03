/*
 * test_ConnectedDataElement  .cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "submodel/api/submodelelement/operation/IOperation.h"
#include "submodel/connected/submodelelement/operation/ConnectedOperation.h"
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
  std::shared_ptr<basyx::object::object_map_t> map(new basyx::object::object_map_t);

  ConnectedOperation connected_operation(proxy);

  auto types = connected_operation.getParameterTypes();

  ASSERT_EQ(submodelelement::operation::OperationPaths::INPUT, mock->getElementCallValues.at(0));
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedOperationTest, TestGetReturnTypes)
{
  std::shared_ptr<basyx::object::object_map_t> map(new basyx::object::object_map_t);

  ConnectedOperation connected_operation(proxy);

  auto types = connected_operation.getReturnTypes();

  ASSERT_EQ(submodelelement::operation::OperationPaths::OUTPUT, mock->getElementCallValues.at(0));
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedOperationTest, TestInvoke)
{
  std::shared_ptr<basyx::object::object_map_t> map(new basyx::object::object_map_t);

  ConnectedOperation connected_operation(proxy);

  basyx::object param{2};
  auto invoked = connected_operation.invoke(param);

  ASSERT_EQ("called with ", invoked.Get<std::string>());
  ASSERT_FALSE(mock->invokeCallParameter.IsNull());
  ASSERT_TRUE(mock->invokeCallParameter.InstanceOf<int>());
  ASSERT_EQ(mock->invokeCallParameter.Get<int>(), 2);
  ASSERT_EQ(1, mock->overallMockCalls());
}


