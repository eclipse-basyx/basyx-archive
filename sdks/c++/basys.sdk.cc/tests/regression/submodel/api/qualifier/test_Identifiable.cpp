#include <gtest/gtest.h>

#include <BaSyx/submodel/api_v2/qualifier/IIdentifiable.h>
#include <BaSyx/submodel/simple/qualifier/Identifiable.h>
#include <BaSyx/submodel/map_v2/qualifier/Identifiable.h>

using namespace basyx::submodel;

// Constants
static const char * idShort = "testId";

// Implementations to run tests for
using ImplTypes = ::testing::Types<
	simple::Identifiable,
	map::Identifiable
>;

template<class Impl>
class IdentifiableTest :public ::testing::Test {
protected:
	std::unique_ptr<api::IIdentifiable> identifiable;
protected:
	void SetUp() override
	{
		this->identifiable = util::make_unique<Impl>(idShort, simple::Identifier{ IdentifierType::Custom, "Test" });
	}

	void TearDown() override
	{
	}
};

TYPED_TEST_CASE(IdentifiableTest, ImplTypes);

TYPED_TEST(IdentifiableTest, TestConstructor)
{
	ASSERT_EQ(this->identifiable->getIdShort(), idShort);
	ASSERT_EQ(this->identifiable->getCategory(), nullptr);
	ASSERT_EQ(this->identifiable->getParent(), nullptr);

	auto ident = this->identifiable->getIdentification();
	ASSERT_EQ(ident.getId(), "Test" );
	ASSERT_EQ(ident.getIdType(), IdentifierType::Custom );
}