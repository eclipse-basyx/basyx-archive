#ifndef BASYX_SUBMODEL_API_V2_COMMON_IMODELTYPE_H
#define BASYX_SUBMODEL_API_V2_COMMON_IMODELTYPE_H

#include <BaSyx/submodel/enumerations/ModelTypes.h>

namespace basyx {
namespace submodel {
namespace api {

class IModelType
{
public:
	virtual ~IModelType() = 0;
	virtual ModelTypes GetModelType() const = 0;
};

inline IModelType::~IModelType() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_COMMON_IMODELTYPE_H */
