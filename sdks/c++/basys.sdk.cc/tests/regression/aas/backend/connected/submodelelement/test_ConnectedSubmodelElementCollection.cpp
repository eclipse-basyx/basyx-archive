/*
 * test_ConnectedSubmodelElement  .cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "backend/connected/aas/submodelelement/ConnectedSubmodelElementCollection.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "support/VABProxyMock.cpp"
#include "aas/submodelelement/property/IProperty.h"
#include "aas/ISubModel.h"

using namespace basyx::aas::backend::connected;
using namespace basyx::vab::core::proxy;
using namespace basyx::aas::submodelelement;
using namespace basyx::aas;

class ConnectedSubmodelElementCollectionTest : public ::testing::Test
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

TEST_F(ConnectedSubmodelElementCollectionTest, TestSetValue)
{
  ConnectedSubmodelElementCollection elements(proxy);

  basyx::objectCollection_t value;
  value.push_back('a');

  elements.setValue(value);

  ASSERT_EQ(property::PropertyPaths::VALUE, mock->updateElementCallValues.at(0).first);
  ASSERT_EQ(1, mock->updateElementCallValues.at(0).second.Get<basyx::objectCollection_t>().size());
  ASSERT_EQ('a', mock->updateElementCallValues.at(0).second.Get<basyx::objectCollection_t>().at(0).Get<char>());
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedSubmodelElementCollectionTest, TestGetValue)
{
  ConnectedSubmodelElementCollection elements(proxy);

  mock->collection.push_back(0);

  auto value = elements.getValue();

  ASSERT_EQ(property::PropertyPaths::VALUE, mock->getElementCallValues.at(0));
  ASSERT_EQ(1, value.size());
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedSubmodelElementCollectionTest, TestSetOrdered)
{
  ConnectedSubmodelElementCollection elements(proxy);
  
  elements.setOrdered(true);
  elements.setOrdered(false);

  ASSERT_EQ(SubmodelElementCollectionPaths::ORDERED, mock->updateElementCallValues.at(0).first);
  ASSERT_TRUE(mock->updateElementCallValues.at(0).second.Get<bool>());
  ASSERT_EQ(SubmodelElementCollectionPaths::ORDERED, mock->updateElementCallValues.at(1).first);
  ASSERT_FALSE(mock->updateElementCallValues.at(1).second.Get<bool>());
  ASSERT_EQ(2, mock->overallMockCalls());
}

TEST_F(ConnectedSubmodelElementCollectionTest, TestIsOrdered)
{
  auto mock_bool = std::make_shared<mockups::VABProxyMockBool>();
  std::shared_ptr<IVABElementProxy> proxy_bool = mock_bool;

  ConnectedSubmodelElementCollection elements(proxy_bool);

  auto value = elements.isOrdered();

  ASSERT_EQ(SubmodelElementCollectionPaths::ORDERED, mock_bool->getElementCallValues.at(0));
  ASSERT_TRUE(value);
  ASSERT_EQ(1, mock_bool->overallMockCalls());
}

TEST_F(ConnectedSubmodelElementCollectionTest, TestSetAllowDuplicates)
{
  ConnectedSubmodelElementCollection elements(proxy);

  elements.setAllowDuplicates(true);
  elements.setAllowDuplicates(false);

  ASSERT_EQ(SubmodelElementCollectionPaths::ALLOWDUPLICATES, mock->updateElementCallValues.at(0).first);
  ASSERT_TRUE(mock->updateElementCallValues.at(0).second.Get<bool>());
  ASSERT_EQ(SubmodelElementCollectionPaths::ALLOWDUPLICATES, mock->updateElementCallValues.at(1).first);
  ASSERT_FALSE(mock->updateElementCallValues.at(1).second.Get<bool>());
  ASSERT_EQ(2, mock->overallMockCalls());
}

TEST_F(ConnectedSubmodelElementCollectionTest, TestGetAllowDuplicates)
{
  auto mock_bool = std::make_shared<mockups::VABProxyMockBool>();
  std::shared_ptr<IVABElementProxy> proxy_bool = mock_bool;

  ConnectedSubmodelElementCollection elements(proxy_bool);

  auto value = elements.isAllowDuplicates();

  ASSERT_EQ(SubmodelElementCollectionPaths::ALLOWDUPLICATES, mock_bool->getElementCallValues.at(0));
  ASSERT_TRUE(value);
  ASSERT_EQ(1, mock_bool->overallMockCalls());
}

TEST_F(ConnectedSubmodelElementCollectionTest, TestSetElements)
{
  ConnectedSubmodelElementCollection elements(proxy);

  basyx::objectMap_t map;

  elements.setElements(map);

  ASSERT_EQ(SubmodelPaths::SUBMODELELEMENT, mock->updateElementCallValues.at(0).first);
  ASSERT_EQ(map, mock->updateElementCallValues.at(0).second.Get<basyx::objectMap_t>());
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedSubmodelElementCollectionTest, TestGetElements)
{
  auto mock_map = std::make_shared<mockups::VABProxyMockMap>();
  std::shared_ptr<IVABElementProxy> proxy_map = mock_map;

  mock_map->map["pos"] = basyx::any();

  ConnectedSubmodelElementCollection elements(proxy_map);

  auto value = elements.getElements();

  ASSERT_EQ(SubmodelPaths::SUBMODELELEMENT, mock_map->getElementCallValues.at(0));
  ASSERT_EQ(1, value.size());
  ASSERT_EQ(1, mock_map->overallMockCalls());
}

