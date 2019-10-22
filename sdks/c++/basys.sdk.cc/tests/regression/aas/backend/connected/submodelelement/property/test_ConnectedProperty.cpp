/*
 * test_ConnectedProperty.cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "backend/connected/aas/submodelelement/property/ConnectedProperty.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "support/VABProxyMock.cpp" 
#include "basyx/anyTypeChecker.h"

using namespace basyx::aas::submodelelement::property;
using namespace basyx::vab::core::proxy;
using namespace basyx::aas::backend::connected;

class ConnectedPropertyTest : public ::testing::Test
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

TEST_F(ConnectedPropertyTest, TestGetPropertyType)
{
  std::shared_ptr<IProperty> property(new ConnectedProperty(PropertyType::Collection, proxy));
  
  ASSERT_EQ(PropertyType::Collection, property->getPropertyType());
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedPropertyTest, TestSetValueString)
{
  std::shared_ptr<IProperty> property(new ConnectedProperty(PropertyType::Collection, proxy));

  basyx::any any = std::string("this is a string");

  property->setValue(any);

  auto calling_parameters = mock->updateElementCallValues;
  
  ASSERT_EQ(calling_parameters.at(0).first, PropertyPaths::VALUE);
  ASSERT_EQ(calling_parameters.at(0).second.Get<std::string>(), any.Get<std::string>());
  ASSERT_EQ(calling_parameters.at(1).first, PropertyPaths::VALUETYPE);
  ASSERT_EQ(calling_parameters.at(1).second.Get<std::string>(), basyx::PropertyTypeInfo::STRING);
  ASSERT_EQ(2, mock->overallMockCalls());
}

TEST_F(ConnectedPropertyTest, TestSetValueInt)
{
  std::shared_ptr<IProperty> property(new ConnectedProperty(PropertyType::Collection, proxy));

  basyx::any any = 235;

  property->setValue(any);

  auto calling_parameters = mock->updateElementCallValues;

  ASSERT_EQ(calling_parameters.at(0).first, PropertyPaths::VALUE);
  ASSERT_EQ(calling_parameters.at(0).second.Get<int>(), any.Get<int>());
  ASSERT_EQ(calling_parameters.at(1).first, PropertyPaths::VALUETYPE);
  ASSERT_EQ(calling_parameters.at(1).second.Get<std::string>(), basyx::PropertyTypeInfo::INT);
  ASSERT_EQ(2, mock->overallMockCalls());
}

TEST_F(ConnectedPropertyTest, TestGetValue)
{
  std::shared_ptr<IProperty> property(new ConnectedProperty(PropertyType::Collection, proxy));

  auto value = property->getValue();

  ASSERT_EQ(value.Get<std::string>(), "called with " + std::string(PropertyPaths::VALUE));
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedPropertyTest, TestSetValueID)
{
  std::shared_ptr<IProperty> property(new ConnectedProperty(PropertyType::Collection, proxy));

  basyx::any any = std::string("ID");
  property->setValueId(any);

  auto calling_parameters = mock->updateElementCallValues;

  ASSERT_EQ(calling_parameters.at(0).first, PropertyPaths::VALUEID);
  ASSERT_EQ(calling_parameters.at(0).second.Get<std::string>(), any.Get<std::string>());
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedPropertyTest, TestGetValueID)
{
  std::shared_ptr<IProperty> property(new ConnectedProperty(PropertyType::Collection, proxy));

  auto any = property->getValueId();

  ASSERT_EQ(any.Get<std::string>(), "called with " + std::string(PropertyPaths::VALUEID));
  ASSERT_EQ(1, mock->overallMockCalls());
}

//TEST_F(ConnectedPropertyTest, TestGetID)
//{
//  std::shared_ptr<IProperty> property(new ConnectedProperty(PropertyType::Collection, proxy));
//
//  auto id = property->getId();
//
//  //Further tests can be found in ConnectedDataElementTests
//  ASSERT_EQ(id, "called with " + std::string(basyx::aas::qualifier::ReferablePaths::IDSHORT));
//  ASSERT_EQ(1, mock->overallMockCalls());
//}
//
//TEST_F(ConnectedPropertyTest, TestSetID)
//{
//  std::shared_ptr<IProperty> property(new ConnectedProperty(PropertyType::Collection, proxy));
//
//  property->setId("ID");
//
//  ASSERT_EQ(property->getId(), "called with " + std::string(basyx::aas::qualifier::ReferablePaths::IDSHORT));
//  ASSERT_EQ(1, mock->updateElementValue_calls);
//  ASSERT_EQ(1, mock->readElementValue_calls);
//  ASSERT_EQ(2, mock->overallMockCalls());
//}
