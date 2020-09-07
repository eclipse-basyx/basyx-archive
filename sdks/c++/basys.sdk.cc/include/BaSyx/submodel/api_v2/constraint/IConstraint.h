#ifndef BASYX_SUBMODEL_API_V2_CONSTRAINT_ICONSTRAINT_H
#define BASYX_SUBMODEL_API_V2_CONSTRAINT_ICONSTRAINT_H

#include <BaSyx/submodel/api_v2/common/IModelType.h>

namespace basyx {
namespace submodel {
namespace api {


class IConstraint
{
public:
	virtual ~IConstraint() = 0;
};

inline IConstraint::~IConstraint() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_CONSTRAINT_ICONSTRAINT_H */
