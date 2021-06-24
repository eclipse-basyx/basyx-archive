#include <gtest/gtest.h>

#include <BaSyx/submodel/api_v2/qualifier/IReferable.h>
#include <BaSyx/submodel/simple/qualifier/Referable.h>
#include <BaSyx/submodel/map_v2/qualifier/Referable.h>

using namespace basyx::submodel;

// Constants
std::string idShort = "testId";

// Implementations to run tests for
using ImplTypes = ::testing::Types<
	simple::Referable, 
	map::Referable
>;

template<class Impl>
class ReferableTest :public ::testing::Test {
protected:
	using impl_t = Impl;
	std::unique_ptr<api::IReferable> referable;
protected:
	void SetUp() override
	{
		this->referable = util::make_unique<Impl>(idShort, nullptr);
	}

	void TearDown() override
	{
	}
};

TYPED_TEST_CASE(ReferableTest, ImplTypes);

TYPED_TEST(ReferableTest, TestConstructor)
{
	ASSERT_EQ(this->referable->getIdShort(), idShort);
	ASSERT_EQ(this->referable->getCategory(), nullptr);
}

TYPED_TEST(ReferableTest, TestSetCategory)
{
	ASSERT_EQ(this->referable->getCategory(), nullptr);
	this->referable->setCategory("category");
	ASSERT_TRUE(this->referable->getCategory() != nullptr);
	ASSERT_EQ(*this->referable->getCategory() , "category");
}
