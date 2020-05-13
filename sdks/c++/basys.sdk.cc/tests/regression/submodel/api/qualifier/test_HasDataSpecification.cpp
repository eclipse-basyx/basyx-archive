#include <gtest/gtest.h>

#include <BaSyx/submodel/api_v2/qualifier/IHasDataSpecification.h>
#include <BaSyx/submodel/simple/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/map_v2/qualifier/HasDataSpecification.h>

using namespace basyx::submodel;

// Implementations to run tests for
using ImplTypes = ::testing::Types<
	simple::HasDataSpecification, 
	map::HasDataSpecification
>;

template<class Impl>
class HasDataSpecTest :public ::testing::Test {
protected:
	using impl_t = Impl;
protected:
	std::unique_ptr<api::IHasDataSpecification> hasDataSpec;
protected:
	simple::Key testKey1{ KeyElements::Asset, true, KeyType::Custom, "Test1" };
	simple::Key testKey2{ KeyElements::Blob, true, KeyType::Custom, "Test2" };
protected:
	void SetUp() override
	{
		this->hasDataSpec = util::make_unique<Impl>();
	}

	void TearDown() override
	{
	}
};

TYPED_TEST_CASE(HasDataSpecTest, ImplTypes);

TYPED_TEST(HasDataSpecTest, TestConstructor)
{
	auto dataSpec = this->hasDataSpec->getDataSpecificationReference();
	ASSERT_EQ(dataSpec.size(), 0);
}

TYPED_TEST(HasDataSpecTest, TestAddSingleDataSpec)
{
	simple::Reference ref{ this->testKey1 };
	this->hasDataSpec->addDataSpecification(ref);

	auto dataSpec = this->hasDataSpec->getDataSpecificationReference();
	ASSERT_EQ(dataSpec.size(), 1);
	ASSERT_EQ(dataSpec[0], ref);
}

TYPED_TEST(HasDataSpecTest, TestAddMultipleDataSpec)
{
	simple::Reference ref1{ this->testKey1 };
	simple::Reference ref2{ this->testKey2 };
	this->hasDataSpec->addDataSpecification(ref1);
	this->hasDataSpec->addDataSpecification(ref2);

	auto dataSpec = this->hasDataSpec->getDataSpecificationReference();
	ASSERT_EQ(dataSpec.size(), 2);
	ASSERT_EQ(dataSpec[0], ref1);
	ASSERT_EQ(dataSpec[1], ref2);
}
