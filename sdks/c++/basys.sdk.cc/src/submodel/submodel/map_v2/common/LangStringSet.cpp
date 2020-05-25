#include <algorithm>

#include <BaSyx/submodel/map_v2/common/LangStringSet.h>

namespace basyx {
namespace submodel {
namespace map {

constexpr char LangStringSet::Path::Text[];
constexpr char LangStringSet::Path::Language[];

std::string empty_string;

LangStringSet::LangStringSet()
    : vab::ElementMap(basyx::object::make_object_list())
{};

LangStringSet::LangStringSet(const ILangStringSet & other)
    : vab::ElementMap(basyx::object::make_object_list())
{
  auto langCodes = other.getLanguageCodes();
  for (auto langCode : langCodes)
    this->add(langCode, other.get(langCode));
}

LangStringSet::langCodeSet_t LangStringSet::getLanguageCodes() const
{
  auto &langStrings = this->map.Get<basyx::object::object_list_t &>();
  std::vector<std::string> langCodes;
  std::transform(langStrings.begin(), langStrings.end(), std::back_inserter(langCodes),
                 [](basyx::object langSet) { return langSet.getProperty(Path::Language).GetStringContent(); });
  return LangStringSet::langCodeSet_t(langCodes.begin(), langCodes.end());
}

const std::string &LangStringSet::get(const std::string &languageCode) const
{
  auto &langStrings = this->map.Get<basyx::object::object_list_t &>();
  auto langString = std::find_if(
      langStrings.begin(), langStrings.end(),
      [&languageCode](basyx::object &obj) {
        const auto &langCode = obj.getProperty(Path::Language).Get<std::string &>();
        return langCode == languageCode;
      });

  if (langString != langStrings.end())
    return langString->getProperty(Path::Text).Get<std::string &>();

  return empty_string;
}

void LangStringSet::add(const std::string &languageCode, const std::string &langString)
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
}

namespace api {
bool operator==(const basyx::submodel::api::ILangStringSet &left, const basyx::submodel::api::ILangStringSet &right)
{
  auto langCodes = left.getLanguageCodes();
  if (langCodes.size() != right.getLanguageCodes().size())
    return false;
  for (auto langCode : langCodes)
  {
    auto leftString = left.get(langCode);
    if (leftString.compare(right.get(langCode)) != 0)
      return false;
  }
  return true;
}

}
}
}