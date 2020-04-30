#ifndef BASYX_SUBMODEL_API_V2_COMMON_ILANGSTRINGSET_H
#define BASYX_SUBMODEL_API_V2_COMMON_ILANGSTRINGSET_H

#include <string>
#include <initializer_list>
#include <unordered_set>
#include <unordered_map>

namespace basyx {
namespace submodel {
namespace api {

class ILangStringSet
{
public:
	using langCodeSet_t = const std::unordered_set<std::string>;
public:
	virtual ~ILangStringSet();

	virtual langCodeSet_t getLanguageCodes() const = 0;

	virtual const std::string & get(const std::string & languageCode) const = 0;
	virtual void add(const std::string & languageCode, const std::string & langString) = 0;
	virtual bool empty() const noexcept = 0;
};

inline ILangStringSet::~ILangStringSet() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_COMMON_ILANGSTRINGSET_H */
