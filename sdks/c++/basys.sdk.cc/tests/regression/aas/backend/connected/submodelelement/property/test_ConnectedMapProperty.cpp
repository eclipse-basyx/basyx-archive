/*
 * test_ConnectedMapProperty.cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "backend/connected/aas/submodelelement/property/ConnectedMapProperty.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "support/VABProxyMock.cpp"

using namespace basyx::aas::backend::connected;
using namespace basyx::vab::core::proxy;
using namespace basyx::aas::qualifier;


class ConnectedMapPropertyTest : public ::testing::Test
{
public:
  std::shared_ptr<IVABElementProxy> proxy;
  std::shared_ptr<mockups::VABProxyMockUp<mockups::ProxyType::Map>> mock;
protected:
  void SetUp() override
  {
    mock = std::make_shared<mockups::VABProxyMockUp<mockups::ProxyType::Map>>();
    proxy = mock;
  }
};


TEST_F(ConnectedMapPropertyTest, TestGetValueNotPresent)
{
  ConnectedMapProperty connected_map_property(proxy);
  
  ASSERT_THROW(connected_map_property.getValue("asdf"), std::out_of_range);
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedMapPropertyTest, TestGetValue)
{
  ConnectedMapProperty connected_map_property(proxy);

  mock->map["asdf"] = basyx::any("value");

  auto value = connected_map_property.getValue("asdf");

  ASSERT_EQ(1, mock->overallMockCalls());
  ASSERT_EQ("value", value.Get<std::string>());
}

TEST_F(ConnectedMapPropertyTest, TestPutNotPresent)
{
  ConnectedMapProperty connected_map_property(proxy);

  basyx::any value("value");

  connected_map_property.put("path", value);

  ASSERT_EQ(1, mock->overallMockCalls());
  ASSERT_EQ(std::string(basyx::aas::submodelelement::property::PropertyPaths::VALUE) + "//path", mock->updateElementCallValues.at(0).first);
  ASSERT_EQ("value", mock->updateElementCallValues.at(0).second.Get<std::string>());
}

TEST_F(ConnectedMapPropertyTest, TestSetMap)
{
  ConnectedMapProperty connected_map_property(proxy);

  basyx::objectMap_t map;

  connected_map_property.set(map);

  ASSERT_EQ(1, mock->overallMockCalls());
  ASSERT_EQ(std::string(basyx::aas::submodelelement::property::PropertyPaths::VALUE), mock->updateElementCallValues.at(0).first);
  ASSERT_EQ(map, mock->updateElementCallValues.at(0).second.Get<basyx::objectMap_t>());
}

TEST_F(ConnectedMapPropertyTest, TestGetKeys)
{
  ConnectedMapProperty connected_map_property(proxy);

  basyx::any value1(std::string("value"));
  basyx::any value2(std::string("value"));

  mock->map.emplace("path1", value1);
  mock->map.emplace("path2", value2);

  auto keys = connected_map_property.getKeys();

  ASSERT_EQ(1, mock->overallMockCalls());
  ASSERT_EQ(std::string(basyx::aas::submodelelement::property::PropertyPaths::VALUE), mock->getElementCallValues.at(0));

  ASSERT_TRUE(keys.at(0).Get<std::string>() == "path1" or keys.at(0).Get<std::string>() == "path2");
  ASSERT_TRUE(keys.at(1).Get<std::string>() == "path1" or keys.at(1).Get<std::string>() == "path2");
}

TEST_F(ConnectedMapPropertyTest, TestGetEntryCount)
{
  ConnectedMapProperty connected_map_property(proxy);

  basyx::any value1(std::string("value"));
  basyx::any value2(std::string("value"));

  mock->map.emplace("path1", value1);
  mock->map.emplace("path2", value2);

  auto key_count = connected_map_property.getEntryCount();

  ASSERT_EQ(1, mock->overallMockCalls());
  ASSERT_EQ(std::string(basyx::aas::submodelelement::property::PropertyPaths::VALUE), mock->getElementCallValues.at(0));
  ASSERT_EQ(2, key_count);
}

TEST_F(ConnectedMapPropertyTest, TestRemove)
{
  ConnectedMapProperty connected_map_property(proxy);

  connected_map_property.remove("path");

  ASSERT_EQ(1, mock->overallMockCalls());
  ASSERT_EQ(std::string(basyx::aas::submodelelement::property::PropertyPaths::VALUE) + "//path", mock->removeElementCallValues.at(0));
}

