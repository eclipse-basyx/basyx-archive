#include <BaSyx/submodel/map_v2/common/LangStringSet.h>

using namespace basyx::submodel::map;

constexpr char LangStringSet::Path::Text[];
constexpr char LangStringSet::Path::Language[];

std::string empty_string;

LangStringSet::LangStringSet()
	: vab::ElementMap(basyx::object::make_object_list())
{
};

LangStringSet::langCodeSet_t LangStringSet::getLanguageCodes() const
{
	return LangStringSet::langCodeSet_t();
};

const std::string & LangStringSet::get(const std::string & languageCode) const
{
	auto & langStrings = this->map.Get<basyx::object::object_list_t&>();
	auto langString = std::find_if(
		langStrings.begin(), langStrings.end(),
		[&languageCode](basyx::object & obj) { 
			const auto & langCode = obj.getProperty(Path::Language).Get<std::string&>();
			return langCode == languageCode;
	});

	if (langString != langStrings.end())
		return langString->getProperty(Path::Text).Get<std::string&>();

	return empty_string;
};

void LangStringSet::add(const std::string & languageCode, const std::string & langString)
{
	auto langStringMap = basyx::object::make_map();
	langStringMap.insertKey(Path::Text, langString);
	langStringMap.insertKey(Path::Language, languageCode);
	this->map.insert(langStringMap);
};

bool LangStringSet::empty() const noexcept
{
	return this->map.empty();
};