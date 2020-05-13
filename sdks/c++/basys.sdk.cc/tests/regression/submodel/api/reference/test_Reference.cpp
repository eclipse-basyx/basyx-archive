#include <gtest/gtest.h>

#include <BaSyx/submodel/api_v2/reference/IReference.h>
#include <BaSyx/submodel/simple/reference/Reference.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>

using namespace basyx::submodel;

// Implementations to run tests for
using ImplTypes = ::testing::Types<
	simple::Reference,
	map::Reference
>;

template<class Impl>
class ReferenceTest :public ::testing::Test {
protected:
	std::unique_ptr<api::IReference> reference;
protected:
	void SetUp() override
	{
		this->reference = util::make_unique<Impl>();
	}

	void TearDown() override
	{
	}
};

TYPED_TEST_CASE(ReferenceTest, ImplTypes);

TYPED_TEST(ReferenceTest, TestConstructor)
{
	auto keys = this->reference->getKeys();
	ASSERT_EQ(keys.size(), 0);
}

TYPED_TEST(ReferenceTest, TestAddKey)
{
	simple::Key key{ KeyElements::Asset, true, KeyType::Custom, "key" };
	this->reference->addKey(key);

	auto keys = this->reference->getKeys();
	ASSERT_EQ(keys.size(), 1);
	ASSERT_EQ(keys[0], key);
}
