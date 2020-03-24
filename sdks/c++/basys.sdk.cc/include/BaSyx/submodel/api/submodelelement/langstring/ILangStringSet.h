#ifndef _ILANGSTRINGSET_H
#define _ILANGSTRINGSET_H

#include <string>

namespace basyx {
namespace submodel {

class ILangStringSet
{
public:
	struct Path {
		static constexpr char Language[] = "language";
		static constexpr char Text[] = "text";
	};
public: // Constructors / dtor
	ILangStringSet() = default; 


	virtual ~ILangStringSet() = 0;
public:

    virtual const std::string & getLangString(const std::string & languageCode) const = 0;
	virtual void addLangString(const std::string & languageCode, const std::string & langString) = 0 ;
};

inline ILangStringSet::~ILangStringSet() = default;

}
}

#endif /* _ILANGSTRINGSET_H */
