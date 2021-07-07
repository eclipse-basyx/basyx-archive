#include <gtest/gtest.h>

#include <BaSyx/submodel/api_v2/constraint/IFormula.h>
#include <BaSyx/submodel/simple/constraint/Formula.h>
#include <BaSyx/submodel/map_v2/constraint/Formula.h>

#include "support/TestingObjects.h"

using namespace basyx::submodel;

// Implementations to run tests for
using ImplTypes = ::testing::Types<
	simple::Formula,
	map::Formula
>;

template<class Impl>
class FormulaTest :public ::testing::Test {
protected:
	using impl_t = Impl;
	std::unique_ptr<api::IFormula> formula;
protected:
	void SetUp() override
	{
		this->formula = util::make_unique<Impl>();
	}

	void TearDown() override
	{
	}
};

TYPED_TEST_CASE(FormulaTest, ImplTypes);

TYPED_TEST(FormulaTest, TestConstructor)
{
	ASSERT_TRUE(this->formula->getDependencies().empty());
}

TYPED_TEST(FormulaTest, TestListConstructor)
{
  using impl_t = typename TestFixture::impl_t;

  std::vector<simple::Reference> dependencies;
  dependencies.push_back(TestingObjects::map::testingReference_1());
  dependencies.push_back(TestingObjects::map::testingReference_2());

  impl_t formula(dependencies);

  auto deps = formula.getDependencies();
  ASSERT_EQ(dependencies, deps);
}

TYPED_TEST(FormulaTest, TestAdd)
{
	this->formula->addDependency(
		simple::Reference(simple::Key{ KeyElements::Asset, true, KeyType::Custom, "test" })
		);

	const auto & dependencies = this->formula->getDependencies();

	ASSERT_EQ(dependencies.size(), 1);
	const auto & ref = dependencies.at(0);

	const auto & keys = ref.getKeys();
	ASSERT_EQ(keys.size(), 1);
	ASSERT_EQ(keys[0].isLocal(), true);
	ASSERT_EQ(keys[0].getIdType(), KeyType::Custom);
	ASSERT_EQ(keys[0].getType(), KeyElements::Asset);
	ASSERT_EQ(keys[0].getValue(), "test");
}
