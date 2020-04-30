#ifndef BASYX_SUBMODEL_API_V2_QUALIFIER_IHASKIND_H
#define BASYX_SUBMODEL_API_V2_QUALIFIER_IHASKIND_H

#include <string>

#include <BaSyx/util/util.h>

namespace basyx {
namespace submodel {

enum class Kind
{
	Type,
	Instance
};

class IHasKind
{
public:
	virtual ~IHasKind() = default;
	virtual Kind getKind() const = 0;
};


}
}

namespace util {
	const std::string & to_string(basyx::submodel::Kind kind);

	template<>
	basyx::submodel::Kind from_string<basyx::submodel::Kind>(const std::string & str);
}

#endif /* BASYX_SUBMODEL_API_V2_QUALIFIER_IHASKIND_H */
