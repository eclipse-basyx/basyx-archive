#ifndef BASYX_SUBMODEL_API_V2_QUALIFIER_IHASKIND_H
#define BASYX_SUBMODEL_API_V2_QUALIFIER_IHASKIND_H

#include <string>

#include <BaSyx/submodel/enumerations/ModelingKind.h>

#include <BaSyx/util/util.h>

namespace basyx {
namespace submodel {

class IHasKind
{
public:
	virtual ~IHasKind() = default;
	virtual ModelingKind getKind() const = 0;
};


}
}
#endif /* BASYX_SUBMODEL_API_V2_QUALIFIER_IHASKIND_H */
