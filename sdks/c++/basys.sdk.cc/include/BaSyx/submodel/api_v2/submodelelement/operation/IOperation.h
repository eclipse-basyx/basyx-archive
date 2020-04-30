#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_OPERATION_IOPERATION_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_OPERATION_IOPERATION_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>
#include <BaSyx/submodel/api_v2/submodelelement/operation/IOperationVariable.h>
#include <BaSyx/submodel/api_v2/common/IElementContainer.h>

#include <BaSyx/shared/object.h>

#include <memory>
#include <vector>

namespace basyx {
namespace submodel {
namespace api {

class IOperation : public virtual ISubmodelElement
{
public:
	virtual ~IOperation() = 0;

	virtual IElementContainer<ISubmodelElement> & getInputVariables() = 0;
	virtual IElementContainer<ISubmodelElement> & getOutputVariables() = 0;
	virtual IElementContainer<ISubmodelElement> & getInOutputVariables() = 0;

	virtual basyx::object invoke(basyx::object args) = 0;
};

inline IOperation::~IOperation() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_OPERATION_IOPERATION_H */
