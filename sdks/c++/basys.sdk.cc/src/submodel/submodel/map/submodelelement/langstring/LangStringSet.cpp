#include <BaSyx/submodel/map/submodelelement/langstring/LangStringSet.h>

basyx::submodel::LangStringSet::LangStringSet()
	: vab::ElementMap{basyx::object::make_list<basyx::object>()}
{

}

basyx::submodel::LangStringSet::LangStringSet(basyx::object object)
{

}

const std::string & basyx::submodel::LangStringSet::getLangString(const std::string & languageCode) const
{
	return std::string{};
}

void basyx::submodel::LangStringSet::addLangString(const std::string & languageCode, const std::string & langString)
{

}
