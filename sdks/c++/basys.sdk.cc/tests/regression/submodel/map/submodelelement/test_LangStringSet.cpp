#include <gtest/gtest.h>

#include <BaSyx/submodel/map/submodelelement/langstring/LangStringSet.h>
#include <BaSyx/submodel/map/qualifier/qualifiable/Constraint.h>
#include <BaSyx/submodel/map/modeltype/ModelType.h>

using namespace basyx::submodel;

class LangStringTest : public ::testing::Test
{
protected:
  void SetUp() override
  {}

  void TearDown() override
  {
  }
};

TEST_F(LangStringTest, TestInitializerListConstruction)
{
	LangStringSet langString{ {"DE", "Test" } };
	
	const auto & deString = langString.getLangString("DE");
	ASSERT_EQ(deString, "Test");
}

TEST_F(LangStringTest, TestUnknownLangCode)
{
	LangStringSet langString;

	const auto & string = langString.getLangString("UN");
	
	ASSERT_TRUE(string.empty());
}


TEST_F(LangStringTest, Test)
{
	LangStringSet langString;

	constexpr char stringValDe[] = "Das ist ein Test!";
	constexpr char stringValEn[] = "This is a test!";

	langString.addLangString("DE", stringValDe);
	langString.addLangString("EN", stringValEn);

	const auto & deString = langString.getLangString("DE");
	const auto & enString = langString.getLangString("EN");

	ASSERT_EQ(deString, stringValDe);
	ASSERT_EQ(enString, stringValEn);

	const auto & langCodes = langString.getLanguageCodes();

	ASSERT_EQ(langCodes.size(), 2);
}
