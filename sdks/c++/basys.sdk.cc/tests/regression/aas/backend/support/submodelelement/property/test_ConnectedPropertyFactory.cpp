/*
 * test_ConnectedElement  .cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "vab/core/proxy/IVABElementProxy.h"
#include "support/VABProxyMock.cpp"
#include "backend/support/submodelelement/property/ConnectedPropertyFactory.h"

using namespace basyx::vab::core::proxy;
using basyx::aas::backend::connected::support::ConnectedPropertyFactory::createProperty;
//
//class ConnectedPropertyFactory : public ::testing::Test
//{
//public:
//  std::shared_ptr<IVABElementProxy> proxy;
//  std::shared_ptr<mockups::VABProxyMockMap> mock;
//protected:
//  void SetUp() override
//  {
//    mock = std::make_shared<mockups::VABProxyMockMap>();
//    proxy = mock;
//  }
//};
//
//TEST_F(ConnectedPropertyFactory, TestConstructor)
//{
//  createProperty(proxy);
//  
//  ASSERT_EQ(0, mock->overallMockCalls());
//}
//
//TEST_F(ConnectedPropertyFactory, TestConstructor)
//{
//  std::shared_ptr<basyx::objectMap_t> map(new basyx::objectMap_t);
//  map->emplace("object", basyx::any(std::string("object")));
//
//  createProperty();
//
//  ASSERT_EQ(1, map->size());
//  ASSERT_EQ("object", map->at("object").Get<std::string>());
//  ASSERT_EQ(0, mock->overallMockCalls());
//}

