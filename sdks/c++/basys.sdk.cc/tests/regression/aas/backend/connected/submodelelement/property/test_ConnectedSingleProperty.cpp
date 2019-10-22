/*
 * test_ConnectedSingleProperty.cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "backend/connected/aas/submodelelement/property/ConnectedSingleProperty.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "support/VABProxyMock.cpp" 
#include "basyx/anyTypeChecker.h"

using namespace basyx::aas::submodelelement::property;
using namespace basyx::vab::core::proxy;
using namespace basyx::aas::backend::connected;

class ConnectedSinglePropertyTest : public ::testing::Test
{
public:
  std::shared_ptr<IVABElementProxy> proxy;
  std::shared_ptr<mockups::VABProxyMockMap> mock;
protected:
  void SetUp() override
  {
    mock = std::make_shared<mockups::VABProxyMockMap>();
    proxy = mock;

    mock->map[PropertyPaths::VALUETYPE] = basyx::any("str");
    mock->map[PropertyPaths::VALUE] = basyx::any("another str");
  }
};

TEST_F(ConnectedSinglePropertyTest, TestGet)
{
  ConnectedSingleProperty property(proxy);
  
  auto val = property.get();
  
  ASSERT_EQ(std::string("another str"), val.Get<std::string>());
  ASSERT_EQ(PropertyPaths::VALUE, mock->getElementCallValues.at(0));
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedSinglePropertyTest, TestSet)
{
  ConnectedSingleProperty property(proxy);

  basyx::any val(2);

  property.set(val);

  ASSERT_EQ(PropertyPaths::VALUE, mock->updateElementCallValues.at(0).first);
  ASSERT_EQ(2, mock->updateElementCallValues.at(0).second.Get<int>());
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedSinglePropertyTest, TestGetValueType)
{
  ConnectedSingleProperty property(proxy);

  auto val = property.getValueType();

  ASSERT_EQ("str", val);
  ASSERT_EQ("", mock->getElementCallValues.at(0));
  ASSERT_EQ(1, mock->overallMockCalls());
}

