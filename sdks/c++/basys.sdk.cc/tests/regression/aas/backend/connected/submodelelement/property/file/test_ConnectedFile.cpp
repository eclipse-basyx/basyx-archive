/*
 * test_ConnectedProperty.cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "backend/connected/aas/submodelelement/property/file/ConnectedFile.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "aas/submodelelement/property/IProperty.h"
#include "aas/submodelelement/property/blob/IBlob.h"
#include "support/VABProxyMock.cpp" 
#include "basyx/anyTypeChecker.h"

using namespace basyx::vab::core::proxy;
using namespace basyx::aas::backend::connected;
using namespace basyx::aas::submodelelement::property;
using namespace basyx::aas;

class ConnectedFileTest : public ::testing::Test
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

TEST_F(ConnectedFileTest, TestSetValue)
{
  ConnectedFile file(proxy);

  file.setValue("value");

  ASSERT_EQ("value", mock->updateElementCallValues.at(0).second.Get<std::string>());
  ASSERT_EQ(PropertyPaths::VALUE, mock->updateElementCallValues.at(0).first);
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedFileTest, TestGetValue)
{
  ConnectedFile file(proxy);

  auto val = file.getValue();

  ASSERT_EQ(std::string("called with ") + PropertyPaths::VALUE, val);
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedFileTest, TestSetMimeType)
{
  ConnectedFile file(proxy);

  file.setMimeType("value");

  ASSERT_EQ("value", mock->updateElementCallValues.at(0).second.Get<std::string>());
  ASSERT_EQ(submodelelement::BlobPath::MIMETYPE, mock->updateElementCallValues.at(0).first);
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedFileTest, TestGetMimeType)
{
  ConnectedFile file(proxy);

  auto val = file.getMimeType();

  ASSERT_EQ(std::string("called with ") + submodelelement::BlobPath::MIMETYPE, val);
  ASSERT_EQ(1, mock->overallMockCalls());
}


