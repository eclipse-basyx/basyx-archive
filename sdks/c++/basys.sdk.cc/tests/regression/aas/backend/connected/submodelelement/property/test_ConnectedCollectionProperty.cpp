/*
 * test_ConnectedMapProperty.cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "backend/connected/aas/submodelelement/property/ConnectedCollectionProperty.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "support/VABProxyMock.cpp"
#include "aas/submodelelement/property/IProperty.h"

using namespace basyx::aas::backend::connected;
using namespace basyx::vab::core::proxy;
using namespace basyx::aas::submodelelement::property;


class ConnectedCollectionPropertyTest : public ::testing::Test
{
public:
  std::shared_ptr<IVABElementProxy> proxy;
  std::shared_ptr<mockups::VABProxyMockMap> mock;
protected:
  void SetUp() override
  {
    mock = std::make_shared<mockups::VABProxyMockMap>();
    proxy = mock;
  }
};


TEST_F(ConnectedCollectionPropertyTest, TestSet)
{
  ConnectedCollectionProperty property(proxy);

  basyx::objectCollection_t collection;
  collection.push_back(123);

  property.set(collection);
  
  ASSERT_EQ(PropertyPaths::VALUE, mock->updateElementCallValues.at(0).first);
  ASSERT_EQ(123, mock->updateElementCallValues.at(0).second.Get<basyx::objectCollection_t>().at(0).Get<int>());
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedCollectionPropertyTest, TestAdd)
{
  ConnectedCollectionProperty property(proxy);

  basyx::any value(2);

  property.add(value);

  ASSERT_EQ(PropertyPaths::VALUE, mock->createElementCallValues.at(0).first);
  ASSERT_EQ(2, mock->createElementCallValues.at(0).second.Get<int>());
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedCollectionPropertyTest, TestRemove)
{
  ConnectedCollectionProperty property(proxy);

  basyx::any value(2);

  property.remove(value);

  ASSERT_EQ(PropertyPaths::VALUE, mock->deleteElementCallValues.at(0).first);
  ASSERT_EQ(2, mock->deleteElementCallValues.at(0).second.Get<int>());
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedCollectionPropertyTest, TestGetElementsAndCount)
{
  ConnectedCollectionProperty property(proxy);

  basyx::objectCollection_t collection;
  collection.push_back(2);
  collection.push_back("str");

  mock->map[PropertyPaths::VALUE] = collection;

  auto elements = property.getElements();

  ASSERT_EQ(PropertyPaths::VALUE, mock->getElementCallValues.at(0));
  ASSERT_EQ("str", collection.at(1).Get<std::string>());
  ASSERT_EQ(2, property.getElementCount());
  ASSERT_EQ(2, mock->overallMockCalls());
}

