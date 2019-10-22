/*
 * test_ConnectedSubmodelElement  .cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "backend/connected/aas/submodelelement/ConnectedRelationshipElement.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "support/VABProxyMock.cpp"

using namespace basyx::aas::backend::connected;
using namespace basyx::vab::core::proxy;
using namespace basyx::aas::qualifier;
using namespace basyx::aas;

class ConnectedSubmodelElementTest : public ::testing::Test
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

TEST_F(ConnectedSubmodelElementTest, TestGetDataSpecificationReferences)
{
  auto mock_collection = std::make_shared<mockups::VABProxyMockCollection>();
  std::shared_ptr<IVABElementProxy> proxy_collection = mock_collection;

  auto data_element = ConnectedSubmodelElement(proxy_collection);

  auto category = data_element.getDataSpecificationReferences();

  ASSERT_EQ(std::string(reference::paths::DATASPECIFICATIONS), mock_collection->getElementCallValues.at(0));
  ASSERT_EQ(1, mock_collection->overallMockCalls());
}

TEST_F(ConnectedSubmodelElementTest, TestIDShort)
{
  auto data_element = ConnectedSubmodelElement(proxy);

  auto category = data_element.getIdShort();

  ASSERT_EQ(std::string(qualifier::ReferablePaths::IDSHORT), mock->getElementCallValues.at(0));
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedSubmodelElementTest, TestGetCategory)
{
  auto data_element = ConnectedSubmodelElement(proxy);

  auto category = data_element.getCategory();

  ASSERT_EQ(std::string(ReferablePaths::CATEGORY), mock->getElementCallValues.at(0));
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedSubmodelElementTest, TestGetDescription)
{
  auto data_element = ConnectedSubmodelElement(proxy);

  auto desc = data_element.getDescription();

  ASSERT_EQ(std::string(ReferablePaths::DESCRIPTION), mock->getElementCallValues.at(0));
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedSubmodelElementTest, TestGetParent)
{
  auto mock_collection = std::make_shared<mockups::VABProxyMockCollection>();
  std::shared_ptr<IVABElementProxy> proxy_collection = mock_collection;

  auto data_element = ConnectedSubmodelElement(proxy_collection);

  auto desc = data_element.getParent();

  ASSERT_EQ(std::string(reference::paths::PARENTS), mock_collection->getElementCallValues.at(0));
  ASSERT_EQ(1, mock_collection->overallMockCalls());
}

TEST_F(ConnectedSubmodelElementTest, TestGetQualifier)
{
  auto mock_collection = std::make_shared<mockups::VABProxyMockCollection>();
  std::shared_ptr<mockups::VABProxyMockCollection> proxy_collection = mock_collection;

  auto data_element = ConnectedSubmodelElement(proxy_collection);

  auto desc = data_element.getQualifier();

  ASSERT_EQ(std::string(reference::paths::QUALIFIERS), mock_collection->getElementCallValues.at(0));
  ASSERT_EQ(1, mock_collection->overallMockCalls());
}

TEST_F(ConnectedSubmodelElementTest, TestGetSemanticId)
{
  auto data_element = ConnectedSubmodelElement(proxy);

  auto desc = data_element.getSemanticId();

  ASSERT_EQ(std::string(reference::paths::SEMANTICIDS), mock->getElementCallValues.at(0));
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedSubmodelElementTest, TestGetHasKindRefernce)
{
  auto data_element = ConnectedSubmodelElement(proxy);

  auto desc = data_element.getHasKindReference();

  ASSERT_EQ(std::string(qualifier::haskind::Paths::KIND), mock->getElementCallValues.at(0));
  ASSERT_EQ(1, mock->overallMockCalls());
}



