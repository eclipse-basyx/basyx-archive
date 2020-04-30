#include <gtest/gtest.h>

#include <BaSyx/submodel/api_v2/common/ILangStringSet.h>
#include <BaSyx/submodel/map_v2/common/LangStringSet.h>
#include <BaSyx/submodel/simple/common/LangStringSet.h>

#include <BaSyx/util/util.h>

using namespace basyx::submodel;

using ImplTypes = ::testing::Types<map::LangStringSet, simple::LangStringSet>;

template<class Impl>
class LangStringTest :public ::testing::Test {
protected:
	std::unique_ptr<api::ILangStringSet> langStringSet;
protected:
	void SetUp() override
	{
		this->langStringSet = util::make_unique<Impl>();
	}

	void TearDown() override
	{
	}
};

TYPED_TEST_CASE(LangStringTest, ImplTypes);

TYPED_TEST(LangStringTest, TestUnknownLangCode)
{
	const auto string = this->langStringSet->get("UN");
	
	ASSERT_TRUE(string.empty());
}

TYPED_TEST(LangStringTest, Test)
{
	constexpr char stringValDe[] = "Das ist ein Test!";
	constexpr char stringValEn[] = "This is a test!";

	this->langStringSet->add("DE", stringValDe);
	this->langStringSet->add("EN", stringValEn);

	const auto & deString = this->langStringSet->get("DE");
	const auto & enString = this->langStringSet->get("EN");

	ASSERT_EQ(deString, stringValDe);
	ASSERT_EQ(enString, stringValEn);
};