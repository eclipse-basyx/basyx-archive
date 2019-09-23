/*
 * test_ConnectedDataElement  .cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "aas/backend/connected/ConnectedElement.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "support/VABProxyMock.cpp"

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
  basyx::objectMap_t map;
  map.emplace("object", basyx::any("object"));

  ConnectedElement connected_element(proxy, map);
  
  ASSERT_EQ(1, map.size());
  ASSERT_EQ("object", map.at("object").Get<std::string>());
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedElementTest, TestSetLocalValueNotPresent)
{
  basyx::objectMap_t map;
  map.emplace("object", basyx::any("object"));

  ConnectedElement connected_element(proxy, map);

  connected_element.setLocalValue("another object", 3);

  ASSERT_EQ(2, map.size());
  ASSERT_EQ(3, map.at("another object").Get<int>());
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedElementTest, TestSetLocalValueAllreadyPresent)
{
  basyx::objectMap_t map;
  map.emplace("object", basyx::any("object"));

  ConnectedElement connected_element(proxy, map);

  connected_element.setLocalValue("object", 3);

  ASSERT_EQ(1, map.size());
  ASSERT_EQ(3, map.at("object").Get<int>());
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedElementTest, TestGetLocalValuePresent)
{
  basyx::objectMap_t map;
  map.emplace("object", basyx::any("object"));

  ConnectedElement connected_element(proxy, map);

  auto object = connected_element.getLocalValue("object");

  ASSERT_EQ(1, map.size());
  ASSERT_EQ(object, map.at("object"));
  ASSERT_EQ("object", object.Get<std::string>());
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedElementTest, TestGetLocalValueNotPresent)
{
  basyx::objectMap_t map;

  ConnectedElement connected_element(proxy, map);

  auto object = connected_element.getLocalValue("object");

  ASSERT_EQ(0, map.size());
  ASSERT_EQ(object, nullptr);
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedElementTest, TestUpdateLocalValueNotPresent)
{
  basyx::objectMap_t map;

  ConnectedElement connected_element(proxy, map);

  basyx::any any(3);

  connected_element.updateLocalValue("object", any);

  ASSERT_EQ(1, map.size());
  ASSERT_EQ(3, map.at(0).Get<int>());
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedElementTest, TestUpdateLocalValue)
{
  basyx::any any(3);

  basyx::objectMap_t map;
  map.emplace("object", any);

  ConnectedElement connected_element(proxy, map);

  basyx::any other("new any");

  connected_element.updateLocalValue("object", other);

  ASSERT_EQ(1, map.size());
  ASSERT_EQ("new any", map.at(0).Get<std::string>());
  ASSERT_EQ(0, mock->overallMockCalls());
}

