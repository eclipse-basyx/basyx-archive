#ifndef _LANGSTRINGSET_H
#define _LANGSTRINGSET_H

#include <BaSyx/submodel/api/submodelelement/langstring/ILangStringSet.h>

#include <BaSyx/shared/object.h>

#include <BaSyx/vab/ElementMap.h>

#include <string>
#include <initializer_list>

namespace basyx {
namespace submodel {

class LangStringSet 
	: public ILangStringSet
	, public virtual vab::ElementMap
{
public:
	using langCodeSet_t = const std::vector<std::reference_wrapper<const std::string>>;
public:
	using vab::ElementMap::ElementMap;

	LangStringSet();
	LangStringSet(std::initializer_list<std::pair<std::string, std::string>> il);

	langCodeSet_t getLanguageCodes() const;

    const std::string & getLangString(const std::string & languageCode) const;
    void addLangString(const std::string & languageCode, const std::string & langString);
};

}
}

#endif /* _LANGSTRINGSET_H */