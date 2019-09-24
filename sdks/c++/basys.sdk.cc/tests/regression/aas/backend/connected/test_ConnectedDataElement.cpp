/*
 * test_ConnectedDataElement  .cpp
 *
 *      Author: wendel
 */

#include <gtest/gtest.h>

#include "aas/backend/connected/submodelelement/ConnectedDataElement.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "support/VABProxyMock.cpp"

using namespace basyx::aas::backend;
using namespace basyx::vab::core::proxy;
using namespace basyx::aas::qualifier;

class ConnectedDataElementTest : public ::testing::Test
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


TEST_F(ConnectedDataElementTest, TestGetIdShort)
{
  ConnectedDataElement data_element(proxy);

  auto id = data_element.getIdShort();

  ASSERT_EQ("called with " + std::string(ReferablePaths::IDSHORT), id);
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedDataElementTest, TestSetIdShort)
{
  auto data_element = ConnectedDataElement(proxy);

  data_element.setIdShort("new id");

  ASSERT_EQ(std::string(ReferablePaths::IDSHORT), mock->updateElementCallValues.at(0).first);
  ASSERT_EQ("new id", mock->updateElementCallValues.at(0).second.Get<std::string>());
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedDataElementTest, TestGetCategory)
{
  auto data_element = ConnectedDataElement(proxy);

  auto category = data_element.getCategory();

  ASSERT_EQ("called with " + std::string(ReferablePaths::CATEGORY), category);
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedDataElementTest, TestSetCategory)
{
  auto data_element = ConnectedDataElement(proxy);

  data_element.setCategory("new category");

  ASSERT_EQ(std::string(ReferablePaths::CATEGORY), mock->updateElementCallValues.at(0).first);
  ASSERT_EQ("new category", mock->updateElementCallValues.at(0).second.Get<std::string>());
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedDataElementTest, TestGetDescription)
{
  auto data_element = ConnectedDataElement(proxy);

  auto desc = data_element.getDescription();

  ASSERT_EQ("called with " + std::string(ReferablePaths::DESCRIPTION), desc);
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedDataElementTest, TestSetDescription)
{
  auto data_element = ConnectedDataElement(proxy);

  data_element.setDescription("new description");

  ASSERT_EQ(std::string(ReferablePaths::DESCRIPTION), mock->updateElementCallValues.at(0).first);
  ASSERT_EQ("new description", mock->updateElementCallValues.at(0).second.Get<std::string>());
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedDataElementTest, TestGetIdNotLocal)
{
  auto data_element = ConnectedDataElement(proxy);

  auto id = data_element.getId();

  ASSERT_EQ("called with " + std::string(ReferablePaths::IDSHORT), id);
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedDataElementTest, TestSetIdNotLocal)
{
  auto data_element = ConnectedDataElement(proxy);

  data_element.setId("new id");

  ASSERT_EQ(std::string(ReferablePaths::IDSHORT), mock->updateElementCallValues.at(0).first);
  ASSERT_EQ("new id", mock->updateElementCallValues.at(0).second.Get<std::string>());
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedDataElementTest, TestGetIdIsLocal)
{
  auto data_element = ConnectedDataElement(proxy);

  data_element.setLocalValue(ReferablePaths::IDSHORT, std::string("the id"));

  auto id = data_element.getId();

  ASSERT_EQ("the id", id);
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedDataElementTest, TestSetIdIsLocal)
{
  auto data_element = ConnectedDataElement(proxy);

  data_element.setLocalValue(ReferablePaths::IDSHORT, "the id");

  data_element.setId("new id");

  ASSERT_EQ("new id" ,data_element.getLocalValue(ReferablePaths::IDSHORT).Get<std::string>());
  ASSERT_EQ(0, mock->overallMockCalls());
}

TEST_F(ConnectedDataElementTest, TestGetHasKindreference)
{
  auto data_element = ConnectedDataElement(proxy);

  auto kind = data_element.getHasKindReference();

  ASSERT_EQ("called with " + std::string(haskind::Paths::KIND), kind);
  ASSERT_EQ(1, mock->overallMockCalls());
}

TEST_F(ConnectedDataElementTest, TestSetHasKindreference)
{
  auto data_element = ConnectedDataElement(proxy);

  data_element.setHasKindReference("new kind");

  ASSERT_EQ(std::string(haskind::Paths::KIND), mock->updateElementCallValues.at(0).first);
  ASSERT_EQ("new kind", mock->updateElementCallValues.at(0).second.Get<std::string>());
  ASSERT_EQ(1, mock->overallMockCalls());
}


