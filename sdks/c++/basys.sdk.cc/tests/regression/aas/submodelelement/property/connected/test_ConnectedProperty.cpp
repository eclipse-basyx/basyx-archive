/*
 * test_ConnectedProperty.cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "aas/submodelelement/property/connected/ConnectedProperty.h"
#include "vab/core/proxy/IVABElementProxy.h"

using namespace basyx::aas::submodelelement::property;

class ConnectedPropertyTest : public ::testing::Test
{
  std::shared_ptr<basyx::vab::core::proxy::IVABElementProxy> proxy;
protected:
  void SetUp() override
  {
  }
};

TEST_F(ConnectedPropertyTest, TestReadElementValueTest)
{
  auto property = std::make_shared<IProperty>(ConnectedProperty(PropertyType::Collection, proxy));

}
