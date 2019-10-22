/*
 * test_ConnectedReferenceElement.cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "backend/connected/aas/submodelelement/property/ConnectedReferenceElement.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "support/VABProxyMock.cpp" 
#include "basyx/anyTypeChecker.h"

using namespace basyx::aas::submodelelement::property;
using namespace basyx::vab::core::proxy;
using namespace basyx::aas::backend::connected;

class ConnectedReferenceElementTest : public ::testing::Test
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

TEST_F(ConnectedReferenceElementTest, TestGetPropertyType)
{
  auto element = ConnectedReferenceElement(proxy);

  auto value = element.getValue();
  
  ASSERT_EQ(std::string(PropertyPaths::VALUE), mock->getElementCallValues.at(0));
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedReferenceElementTest, TestSetPropertyType)
{
  auto element = ConnectedReferenceElement(proxy);

  basyx::any value("value");

  element.setValue(value);

  ASSERT_EQ(std::string(PropertyPaths::VALUE), mock->updateElementCallValues.at(0).first);
  ASSERT_EQ("value", mock->updateElementCallValues.at(0).second.Get<std::string>());
  ASSERT_EQ(1, mock->overallMockCalls());
}
