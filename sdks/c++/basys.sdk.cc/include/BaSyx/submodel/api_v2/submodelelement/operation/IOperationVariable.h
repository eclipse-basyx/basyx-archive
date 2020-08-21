#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_OPERATION_IOPERATIONVARIABLE_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_OPERATION_IOPERATIONVARIABLE_H


#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>

#include <memory>

namespace basyx {
namespace submodel {
namespace api {

class IOperationVariable : public virtual ISubmodelElement
{
public:
	virtual ~IOperationVariable() = 0;

	virtual ISubmodelElement & getValue() const = 0;

	virtual KeyElements getKeyElementType() const override { return KeyElements::OperationVariable; };
};

inline IOperationVariable::~IOperationVariable() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_OPERATION_IOPERATIONVARIABLE_H */

