#include <BaSyx/submodel/simple/common/LangStringSet.h>

#include <BaSyx/shared/types.h>

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
  LangStringSet::langCodeSet_t langCodes;
  for (auto & langCode : this->langStrings)
    langCodes.insert(langCode.first);
  return langCodes;
};


LangStringSet::LangStringSet(std::initializer_list<LangStringSet::langStringMap_t::value_type> il)
{
	for (const auto & entry : il)
		this->add(entry.first, entry.second);
}

LangStringSet::LangStringSet(const api::ILangStringSet &other)
{
  for( auto & lang_code : other.getLanguageCodes())
    this->add(lang_code, other.get(lang_code));
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
