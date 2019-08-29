/*
 * test_vab_elementproxy.cpp
 *
 *      Author: wendel
 */

#include "gtest/gtest.h"
#include "backends/provider/vab/VABElementProxy.h"
#include "regression/backends/protocols/provider/basyx/frame/MockupModelProvider.h"

#include <memory>

class BasyxVABElementProxy : public ::testing::Test
{
protected:
  void SetUp() override
  {
    proxy_address = "basyx://127.0.0.1";
    provider_mockup = std::make_shared<MockupModelProvider>();
  }

  std::shared_ptr<MockupModelProvider> provider_mockup;
  std::string proxy_address;
};

TEST_F(BasyxVABElementProxy, TestReadElementValueTest)
{
  VABElementProxy proxy(proxy_address, provider_mockup);

  // Initially no method should have been called
  ASSERT_EQ(MockupModelProvider::CalledFunction::NONE, provider_mockup->called);

  auto value = proxy.readElementValue(std::string("/some/path/"));

  //get function of model provider should have been called
  ASSERT_EQ(MockupModelProvider::CalledFunction::GET, provider_mockup->called);
  //Mockup returns value 2
  ASSERT_EQ(2, value.Get<int>());
  //mockup should have been called with combined address
  ASSERT_EQ(proxy_address + "//some/path", provider_mockup->path);
}

TEST_F(BasyxVABElementProxy, TestReadElementValueTemplateTest)
{
  VABElementProxy proxy(proxy_address, provider_mockup);

  auto value = proxy.readElementValue<int>(std::string("/some/path/"));

  //get function of model provider should have been called
  ASSERT_EQ(MockupModelProvider::CalledFunction::GET, provider_mockup->called);
  //Mockup returns value 2
  ASSERT_EQ(2, value);
  //mockup should have been called with combined address
  ASSERT_EQ(proxy_address + "//some/path", provider_mockup->path);
}

TEST_F(BasyxVABElementProxy, TestUpdateElementValue)
{
  VABElementProxy proxy(proxy_address, provider_mockup);
  
  basyx::any new_value(1);
  proxy.updateElementValue(std::string("some/path/clock"), new_value);

  // Function marker should not be set
  ASSERT_EQ(MockupModelProvider::CalledFunction::NONE, provider_mockup->called);
  //Mockup clock should be set to new_value
  ASSERT_EQ(new_value.Get<int>(), provider_mockup->clock.Get<int>());
}

TEST_F(BasyxVABElementProxy, TestUpdateElementValueOnUnvalidPath)
{
  VABElementProxy proxy(proxy_address, provider_mockup);

  basyx::any new_value(3);
  proxy.updateElementValue(std::string("/frozen/path"), new_value);

  // Function marker should not be set
  ASSERT_EQ(MockupModelProvider::CalledFunction::NONE, provider_mockup->called);
}

TEST_F(BasyxVABElementProxy, TestUpdateElementValueOnValidPath)
{
  VABElementProxy proxy(proxy_address, provider_mockup);

  basyx::any new_value(12);
  proxy.updateElementValue(std::string("/some/valid/path"), new_value);

  // Function marker should not be set
  ASSERT_EQ(MockupModelProvider::CalledFunction::SET, provider_mockup->called);
  // Set path should be same as called one
  ASSERT_EQ(proxy_address + "//some/valid/path", provider_mockup->path);
  // Set value should be set
  ASSERT_EQ(new_value.Get<int>(), provider_mockup->val.Get<int>());
}

TEST_F(BasyxVABElementProxy, TestCreateElement)
{
  VABElementProxy proxy(proxy_address, provider_mockup);

  basyx::any new_value(18);
  proxy.createElement(std::string("/some/path"), new_value);

  // Function marker should not be set
  ASSERT_EQ(MockupModelProvider::CalledFunction::CREATE, provider_mockup->called);
  // Set path should be same as called one
  ASSERT_EQ(proxy_address + "//some/path", provider_mockup->path);
  // Set value should be set
  ASSERT_EQ(new_value.Get<int>(), provider_mockup->val.Get<int>());
}

TEST_F(BasyxVABElementProxy, TestDeleteElement)
{
  VABElementProxy proxy(proxy_address, provider_mockup);

  basyx::any deleted_value(18);
  proxy.deleteElement(std::string("some/path"), deleted_value);

  // Function marker should be set
  ASSERT_EQ(MockupModelProvider::CalledFunction::DELETE_COMPLEX, provider_mockup->called);
  // Set path should be same as called one
  ASSERT_EQ(proxy_address + "//some/path", provider_mockup->path);
  // Set value should be set
  ASSERT_EQ(deleted_value.Get<int>(), provider_mockup->val.Get<int>());
}

TEST_F(BasyxVABElementProxy, TestDeleteElementSimple)
{
  VABElementProxy proxy(proxy_address, provider_mockup);

  proxy.deleteElement(std::string("some/other/path"));

  // Function marker should be set
  ASSERT_EQ(MockupModelProvider::CalledFunction::DELETE_SIMPLE, provider_mockup->called);
  // Set path should be same as called one
  ASSERT_EQ(proxy_address + "//some/other/path", provider_mockup->path);
}

TEST_F(BasyxVABElementProxy, TestInvokeOperation)
{
  VABElementProxy proxy(proxy_address, provider_mockup);

  basyx::objectCollection_t collection;
  collection.push_back(basyx::any(2));
  auto return_value = proxy.invoke(std::string("//some/path/to/invoke"), collection);

  // Since mockup returns always value three, this should be returned here as well
  ASSERT_EQ(3, return_value.Get<int>());
  // Function marker should be set
  ASSERT_EQ(MockupModelProvider::CalledFunction::INVOKE, provider_mockup->called);
  // Set path should be same as called one
  ASSERT_EQ(proxy_address + "//some/path/to/invoke", provider_mockup->path);
  // and the function should have been called with the collection
  ASSERT_EQ(collection.size(), provider_mockup->val.Get<basyx::objectCollection_t>().size());
  ASSERT_EQ(2, provider_mockup->val.Get<basyx::objectCollection_t>().at(0).Get<int>());
}

TEST_F(BasyxVABElementProxy, TestInvokeOperationTemplate)
{
  VABElementProxy proxy(proxy_address, provider_mockup);

  basyx::objectCollection_t collection;
  collection.push_back(basyx::any(2));
  auto return_value = proxy.invoke<int>(std::string("//some/path/to/invoke"), collection);

  // Since mockup returns always value three, this should be returned here as well
  ASSERT_EQ(3, return_value);
  // Function marker should be set
  ASSERT_EQ(MockupModelProvider::CalledFunction::INVOKE, provider_mockup->called);
  // Set path should be same as called one
  ASSERT_EQ(proxy_address + "//some/path/to/invoke", provider_mockup->path);
  // and the function should have been called with the collection
  ASSERT_EQ(collection.size(), provider_mockup->val.Get<basyx::objectCollection_t>().size());
  ASSERT_EQ(2, provider_mockup->val.Get<basyx::objectCollection_t>().at(0).Get<int>());
}
