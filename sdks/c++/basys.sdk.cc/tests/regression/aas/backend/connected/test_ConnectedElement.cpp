/*
 * test_ConnectedElement  .cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "backend/connected/aas/ConnectedElement.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "support/VABProxyMock.cpp"
#include "aas/qualifier/IReferable.h"

using namespace basyx::aas::backend;
using namespace basyx::vab::core::proxy;

class ConnectedElementTest : public ::testing::Test
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

TEST_F(ConnectedElementTest, TestConstructor)
{
  std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);
  map->emplace("object", basyx::any(std::string("object")));

  ConnectedElement connected_element(proxy, map);
  
  ASSERT_EQ(1, map->size());
  ASSERT_EQ("object", map->at("object").Get<std::string>());
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedElementTest, TestSetLocalValueNotPresent)
{
   std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);
   map->emplace("object", basyx::any(std::string("object")));

  ConnectedElement connected_element(proxy, map);

  connected_element.setLocalValue("another object", 3);

  ASSERT_EQ(2, map->size());
  ASSERT_EQ(3, map->at("another object").Get<int>());
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedElementTest, TestSetLocalValueAllreadyPresent)
{
   std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);
   map->emplace("object", basyx::any(std::string("object")));

  ConnectedElement connected_element(proxy, map);

  connected_element.setLocalValue("object", 3);

  ASSERT_EQ(1, map->size());
  //With set the already set value should remain
  ASSERT_EQ("object", map->at("object").Get<std::string>());
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedElementTest, TestGetLocalValuePresent)
{
   std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);
   map->emplace("object", basyx::any(std::string("object")));

  ConnectedElement connected_element(proxy, map);

  auto object = connected_element.getLocalValue("object");

  ASSERT_EQ(1, map->size());
  ASSERT_EQ(object.Get<std::string>(), map->at("object").Get<std::string>());
  ASSERT_EQ("object", object.Get<std::string>());
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedElementTest, TestGetLocalValueNotPresent)
{
   std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);

  ConnectedElement connected_element(proxy, map);

  auto object = connected_element.getLocalValue("object");

  ASSERT_EQ(0, map->size());
  ASSERT_TRUE(object.IsNull());
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedElementTest, TestUpdateLocalValueNotPresent)
{
  std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);

  ConnectedElement connected_element(proxy, map);

  basyx::any any(3);

  connected_element.updateLocalValue("object", any);

  ASSERT_EQ(1, map->size());
  ASSERT_EQ(3, map->at("object").Get<int>());
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedElementTest, TestUpdateLocalValue)
{
  basyx::any any(3);
  
  std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);
  map->emplace("object", any);

  ConnectedElement connected_element(proxy, map);

  basyx::any other(std::string("new any"));

  connected_element.updateLocalValue("object", other);

  ASSERT_EQ(1, map->size());
  ASSERT_EQ("new any", map->at("object").Get<std::string>());
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedElementTest, TestGetID)
{
  std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);

  ConnectedElement connected_element(proxy, map);

  auto id = connected_element.getId();

  ASSERT_EQ(std::string("called with ") + basyx::aas::qualifier::ReferablePaths::IDSHORT, id);
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedElementTest, TestSetID)
{
  std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);

  ConnectedElement connected_element(proxy, map);

  connected_element.setId("identifier");

  ASSERT_EQ(1, mock->overallMockCalls());
  ASSERT_EQ(basyx::aas::qualifier::ReferablePaths::IDSHORT, mock->updateElementCallValues.at(0).first);
  ASSERT_EQ(std::string("identifier"), mock->updateElementCallValues.at(0).second.Get<std::string>());
}

