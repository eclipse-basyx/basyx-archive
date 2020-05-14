#include <BaSyx/submodel/simple/common/LangStringSet.h>

namespace basyx {
namespace submodel {
namespace simple {

const std::string empty_string{};

LangStringSet::LangStringSet()
{
};

LangStringSet::LangStringSet(const std::string & languageCode, const std::string & langString)
{
	this->langStrings.emplace(languageCode, langString);
};

LangStringSet::langCodeSet_t LangStringSet::getLanguageCodes() const
{
	return langCodeSet_t();
};


LangStringSet::LangStringSet(std::initializer_list<LangStringSet::langStringMap_t::value_type> il)
{
	for (const auto & entry : il)
		this->add(entry.first, entry.second);
}

const std::string & LangStringSet::get(const std::string & languageCode) const
{
	if (this->langStrings.find(languageCode) != langStrings.end()) {
		return langStrings.at(languageCode);
	}

	return empty_string;
};


void LangStringSet::add(const std::string & languageCode, const std::string & langString)
{
	this->langStrings.emplace(languageCode, langString);
};

bool LangStringSet::empty() const noexcept
{
	return this->langStrings.empty();
}

}
}
}