#ifndef BASYX_SUBMODEL_MAP_V2_COMMON_LANGSTRINGSET_H
#define BASYX_SUBMODEL_MAP_V2_COMMON_LANGSTRINGSET_H

#include <BaSyx/submodel/api_v2/common/ILangStringSet.h>
#include <BaSyx/vab/ElementMap.h>

#include <string>
#include <initializer_list>
#include <vector>
#include <unordered_map>

namespace basyx {
namespace submodel {
namespace map {

class LangStringSet : 
	public api::ILangStringSet,
	public virtual vab::ElementMap
{
public:
	struct Path {
		static constexpr char Text[] = "text";
		static constexpr char Language[] = "language;";
	};
public:
	using langStringMap_t = std::unordered_map<std::string, std::string>;
private:
	langStringMap_t langStrings;
public:
	using vab::ElementMap::ElementMap;

	LangStringSet();
  LangStringSet(const ILangStringSet & other);

  static LangStringSet from_object(basyx::object);

	virtual langCodeSet_t getLanguageCodes() const override;

	virtual const std::string & get(const std::string & languageCode) const override;
	virtual void add(const std::string & languageCode, const std::string & langString) override;
	virtual bool empty() const noexcept override;

  friend bool api::operator==(const api::ILangStringSet & left, const api::ILangStringSet & right);
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_COMMON_LANGSTRINGSET_H */
