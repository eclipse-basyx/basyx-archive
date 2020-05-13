#include <gtest/gtest.h>

#include <BaSyx/submodel/api_v2/constraint/IQualifier.h>
#include <BaSyx/submodel/simple/constraint/Qualifier.h>
#include <BaSyx/submodel/map_v2/constraint/Qualifier.h>

using namespace basyx::submodel;

// Implementations to run tests for
using ImplTypes = ::testing::Types<
	simple::Qualifier,
	map::Qualifier
>;

template<class Impl>
class QualifierTest :public ::testing::Test {
protected:
	using impl_t = Impl;
	std::unique_ptr<api::IQualifier> qualifier;
protected:
	void SetUp() override
	{
		this->qualifier = util::make_unique<Impl>("qType", "vType");
	}

	void TearDown() override
	{
	}
};

TYPED_TEST_CASE(QualifierTest, ImplTypes);

TYPED_TEST(QualifierTest, TestConstructor)
{
	ASSERT_EQ(this->qualifier->getQualifierType(), "qType");
	ASSERT_EQ(this->qualifier->getValueType(), "vType");
	ASSERT_EQ(this->qualifier->getValueDataType(), nullptr);
	ASSERT_EQ(this->qualifier->getValueId(), nullptr);
}

TYPED_TEST(QualifierTest, TestSetValueId)
{
	simple::Key testKey{ KeyElements::Asset, true, KeyType::Custom, "test" };
	this->qualifier->setValueId(
		simple::Reference(testKey)
	);

	ASSERT_EQ(this->qualifier->getQualifierType(), "qType");
	ASSERT_EQ(this->qualifier->getValueType(), "vType");
	ASSERT_EQ(this->qualifier->getValueDataType(), nullptr);
	ASSERT_NE(this->qualifier->getValueId(), nullptr);

	const auto * ref = this->qualifier->getValueId();
	const auto & keys = ref->getKeys();
	ASSERT_EQ(keys.size(), 1);
	ASSERT_EQ(keys[0], testKey);
}

TYPED_TEST(QualifierTest, TestSetValueDataType)
{
	this->qualifier->setValueDataType("testDataType");
	ASSERT_EQ(this->qualifier->getQualifierType(), "qType");
	ASSERT_EQ(this->qualifier->getValueType(), "vType");
	ASSERT_NE(this->qualifier->getValueDataType(), nullptr);
	ASSERT_EQ(*this->qualifier->getValueDataType(), "testDataType");
	ASSERT_EQ(this->qualifier->getValueId(), nullptr);
}
