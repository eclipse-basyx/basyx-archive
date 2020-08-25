#include <gtest/gtest.h>
#include <BaSyx/submodel/simple/submodelelement/property/Property.h>
#include <BaSyx/submodel/map_v2/submodelelement/property/Property.h>

using namespace basyx::submodel;

// Implementations to run tests for
using ImplTypes = ::testing::Types<
	simple::Property<int>,
	map::Property<int>
>;

template<class Impl>
class PropertyTest :public ::testing::Test {
protected:
	using impl_t = Impl;
protected:
	std::unique_ptr<api::IProperty> property;
protected:
	void SetUp() override
	{
		std::string idShort("id test");
		this->property = util::make_unique<Impl>(idShort);
	}

	void TearDown() override
	{
	}
};

TYPED_TEST_CASE(PropertyTest, ImplTypes);

TYPED_TEST(PropertyTest, TestConstructor)
{
  ASSERT_EQ(this->property->getIdShort(), std::string("id test"));
}

TYPED_TEST(PropertyTest, TestValueType)
{
  this->property->setValueType("valueType test");
  ASSERT_EQ(this->property->getValueType(), std::string("valueType test"));
}

TYPED_TEST(PropertyTest, TestObject)
{
  basyx::object o(123);
  this->property->setObject(o);
  ASSERT_EQ(this->property->getObject(), basyx::object(123));
}

TYPED_TEST(PropertyTest, TestValueID)
{
  simple::Key key(KeyElements::ConceptDictionary, false, KeyType::Custom, "test key");
  std::unique_ptr<api::IReference> ref = util::make_unique<simple::Reference>(key);
  this->property->setValueId(*ref);
  ASSERT_EQ(this->property->getValueId()->getKeys().at(0), key);
}



