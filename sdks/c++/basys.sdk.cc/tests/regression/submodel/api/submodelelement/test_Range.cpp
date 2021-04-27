#include <gtest/gtest.h>

#include <BaSyx/submodel/api_v2/submodelelement/IRange.h>
#include <BaSyx/submodel/simple/submodelelement/Range.h>
#include <BaSyx/submodel/map_v2/submodelelement/Range.h>

#include <BaSyx/submodel/api_v2/submodelelement/property/XSDAnySimpleType.h>

using namespace basyx::xsd_types;
using namespace basyx::submodel;

// Implementations to run tests for
using ImplTypes = ::testing::Types<
  simple::Range<int>,
  map::Range<int>
>;

template<class Impl>
class RangeTest :public ::testing::Test {
protected:
  std::unique_ptr<api::IRange<int>> range;

protected:
  void SetUp() override
  {
    this->range = util::make_unique<Impl>("id");
  }

  void TearDown() override
  {
  }
};

TYPED_TEST_CASE(RangeTest, ImplTypes);

TYPED_TEST(RangeTest, TestGetDataTypeDef)
{
  ASSERT_EQ(this->range->getDataTypeDef(), "int");
}

TYPED_TEST(RangeTest, TestMaxMin)
{
  ASSERT_EQ(this->range->getMax(), nullptr);
  ASSERT_EQ(this->range->getMin(), nullptr);

  this->range->setMax(util::make_unique<int>(3));
  ASSERT_EQ(*this->range->getMax(), 3);

  this->range->setMin(util::make_unique<int>(-2));
  ASSERT_EQ(*this->range->getMin(), -2);
}
