/*
 * test_ConnectedProperty.cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "backend/connected/aas/submodelelement/property/blob/ConnectedBlob.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "aas/submodelelement/property/IProperty.h"
#include "aas/submodelelement/property/blob/IBlob.h"
#include "support/VABProxyMock.cpp" 
#include "basyx/anyTypeChecker.h"

using namespace basyx::vab::core::proxy;
using namespace basyx::aas::backend::connected;
using namespace basyx::aas::submodelelement::property;
using namespace basyx::aas;

class ConnectedBlobTest : public ::testing::Test
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

TEST_F(ConnectedBlobTest, TestSetValue)
{
  ConnectedBlob blob(proxy);

  basyx::byte_array value = {'a','b','c'};

  blob.setValue(value);

  ASSERT_EQ(value, mock->updateElementCallValues.at(0).second.Get<basyx::byte_array>());
  ASSERT_EQ(PropertyPaths::VALUE, mock->updateElementCallValues.at(0).first);
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedBlobTest, TestGetValue)
{
  std::shared_ptr<IVABElementProxy> proxy_byte_array;
  std::shared_ptr<mockups::VABProxyMockByteArray> mock_byte_array;

  mock_byte_array = std::make_shared<mockups::VABProxyMockByteArray>();
  proxy_byte_array = mock_byte_array;

  ConnectedBlob blob(proxy_byte_array);

  auto val = blob.getValue();

  ASSERT_EQ(basyx::byte_array(), val);
  ASSERT_EQ(1, mock_byte_array->overallMockCalls());
}

TEST_F(ConnectedBlobTest, TestSetMimeType)
{
  ConnectedBlob blob(proxy);

  blob.setMimeType("value");

  ASSERT_EQ("value", mock->updateElementCallValues.at(0).second.Get<std::string>());
  ASSERT_EQ(submodelelement::BlobPath::MIMETYPE, mock->updateElementCallValues.at(0).first);
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedBlobTest, TestGetMimeType)
{
  ConnectedBlob blob(proxy);

  auto val = blob.getMimeType();

  ASSERT_EQ(std::string("called with ") + submodelelement::BlobPath::MIMETYPE, val);
  ASSERT_EQ(1, mock->overallMockCalls());
}


