#ifndef BASYX_SUBMODEL_API_V2_QUALIFIER_IHASSEMANTICS_H
#define BASYX_SUBMODEL_API_V2_QUALIFIER_IHASSEMANTICS_H

#include <BaSyx/submodel/api_v2/reference/IReference.h>

#include <string>
#include <memory>

namespace basyx {
namespace submodel {
namespace api {

class IHasSemantics
{
public:
	virtual ~IHasSemantics() = 0;
	virtual IReference & getSemanticId() = 0;
};

inline IHasSemantics::~IHasSemantics() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_QUALIFIER_IHASSEMANTICS_H */
