#ifndef _LANGSTRINGSET_H
#define _LANGSTRINGSET_H

#include <BaSyx/submodel/api/submodelelement/langstring/ILangStringSet.h>

#include <BaSyx/shared/object.h>

#include <BaSyx/vab/ElementMap.h>

#include <string>

namespace basyx {
namespace submodel {

class LangStringSet 
	: public ILangStringSet
	, public virtual vab::ElementMap
{
public:
    LangStringSet();
    LangStringSet(basyx::object object);

    const std::string & getLangString(const std::string & languageCode) const;
    void addLangString(const std::string & languageCode, const std::string & langString);
};

}
}

#endif /* _LANGSTRINGSET_H */
