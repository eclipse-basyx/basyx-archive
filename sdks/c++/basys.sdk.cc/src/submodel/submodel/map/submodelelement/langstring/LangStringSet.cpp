#include <BaSyx/submodel/map/submodelelement/langstring/LangStringSet.h>

const std::string empty{};

basyx::submodel::LangStringSet::LangStringSet()
	: vab::ElementMap{basyx::object::make_object_set()}
{
}

basyx::submodel::LangStringSet::LangStringSet(std::initializer_list<std::pair<std::string, std::string>> il)
	: LangStringSet()
{
	for (const auto & entry : il)
		this->addLangString(entry.first, entry.second);
}

const std::string & basyx::submodel::LangStringSet::getLangString(const std::string & languageCode) const
{
	auto & objectSet = this->getMap().Get<basyx::object::object_set_t&>();

	for (auto & entry : objectSet)
	{
		auto & object = const_cast<basyx::object&>(entry);
		if (object.getProperty(Path::Language) == languageCode) {
			return object.getProperty(Path::Text).GetStringContent();
		}
	};

	return empty;
}

basyx::submodel::LangStringSet::langCodeSet_t basyx::submodel::LangStringSet::getLanguageCodes() const
{
	auto & objectSet = this->getMap().Get<basyx::object::object_set_t&>();

	std::remove_const<langCodeSet_t>::type ret;
	ret.reserve(objectSet.size());

	for (auto & entry : objectSet)
	{
		auto & object = const_cast<basyx::object&>(entry);
		ret.emplace_back(  std::cref( object.getProperty(Path::Language).GetStringContent()) );
	};

	return ret;
};

void basyx::submodel::LangStringSet::addLangString(const std::string & languageCode, const std::string & langString)
{
	auto langStringMap = basyx::object::make_map();
	langStringMap.insertKey( Path::Language, languageCode );
	langStringMap.insertKey( Path::Text, langString);

	this->map.insert(langStringMap);
}