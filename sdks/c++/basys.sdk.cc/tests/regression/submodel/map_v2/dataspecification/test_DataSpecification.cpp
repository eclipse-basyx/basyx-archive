#include <gtest/gtest.h>

#include <BaSyx/submodel/api_v2/dataspecification/IDataSpecificationContent.h>
#include "BaSyx/submodel/map_v2/dataspecification/DataSpecification.h"

using namespace basyx::submodel::map;
using namespace basyx::submodel;

class DataSpecificationTest : public ::testing::Test
{
protected:
  void SetUp() override
  {}

class DataSpecificationContentMock
  : public virtual api::IDataSpecificationContent
  , public basyx::vab::ElementMap
  {
  public:
    DataSpecificationContentMock()
      : basyx::vab::ElementMap{}
    {}

    bool isMock()
    {
      return true;
    }
  };

class DataSpecificationContentMock2
  : public virtual api::IDataSpecificationContent
  , public basyx::vab::ElementMap
  {
  public:
    DataSpecificationContentMock2()
      : basyx::vab::ElementMap{}
    {}

    bool isMock2()
    {
      return true;
    }
  };
};

TEST_F(DataSpecificationTest, TestConstructor)
{
//  simple::Identifier id;
//  std::unique_ptr<api::IDataSpecificationContent> content = util::make_unique<DataSpecificationContentMock>();
//  DataSpecification dataSpecification("id", id, std::move(content));
//
//  ASSERT_EQ(dataSpecification.getIdShort(), "id");
//  ASSERT_TRUE(dataSpecification.getIdentification() == id);
//  ASSERT_TRUE((dynamic_cast<DataSpecificationContentMock&>(dataSpecification.getContent())).isMock());
}

TEST_F(DataSpecificationTest, TestContent)
{
//  simple::Identifier id;
//  std::unique_ptr<api::IDataSpecificationContent> content = util::make_unique<DataSpecificationContentMock>();
//  DataSpecification dataSpecification("id", id, std::move(content));
//
//  ASSERT_TRUE((dynamic_cast<DataSpecificationContentMock&>(dataSpecification.getContent())).isMock());
//
//  std::unique_ptr<api::IDataSpecificationContent> content2 = util::make_unique<DataSpecificationContentMock2>();
//  dataSpecification.setContent(std::move(content2));
//  ASSERT_TRUE((dynamic_cast<DataSpecificationContentMock2&>(dataSpecification.getContent())).isMock2());
}
