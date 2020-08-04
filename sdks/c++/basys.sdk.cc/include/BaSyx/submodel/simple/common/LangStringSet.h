#ifndef BASYX_SUBMODEL_SIMPLE_COMMON_LANGSTRINGSET_H
#define BASYX_SUBMODEL_SIMPLE_COMMON_LANGSTRINGSET_H

#include <BaSyx/submodel/api_v2/common/ILangStringSet.h>

#include <string>
#include <initializer_list>
#include <vector>
#include <unordered_map>

namespace basyx {
namespace submodel {
namespace simple {

class LangStringSet : public api::ILangStringSet
{
public:
	using langStringMap_t = std::unordered_map<std::string, std::string>;
private:
	langStringMap_t langStrings;
public:
	LangStringSet();
	LangStringSet(const std::string & languageCode, const std::string & langString);
	LangStringSet(std::initializer_list<langStringMap_t::value_type>);
  LangStringSet(const api::ILangStringSet & other);
	virtual ~LangStringSet() = default;

	langCodeSet_t getLanguageCodes() const override;

  const std::string & get(const std::string & languageCode) const override;
  void add(const std::string & languageCode, const std::string & langString) override;
	bool empty() const noexcept override;

  friend bool api::operator==(const api::ILangStringSet & left, const api::ILangStringSet & right);
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_COMMON_LANGSTRINGSET_H */
