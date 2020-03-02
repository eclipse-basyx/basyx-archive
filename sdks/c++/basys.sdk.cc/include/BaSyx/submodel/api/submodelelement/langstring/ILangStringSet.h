#ifndef _ILANGSTRINGSET_H
#define _ILANGSTRINGSET_H

#include <string>

class ILangStringSet
{
public:
    virtual ~ILangStringSet() = 0;

    virtual const std::string & getLangString(const std::string & languageCode) const = 0;
    virtual void addLangString(const std::string & languageCode, const std::string & langString) = 0;
};

inline ILangStringSet::~ILangStringSet() = default;

#endif /* _ILANGSTRINGSET_H */
