#ifndef BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_OPERATION_OPERATION_H
#define BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_OPERATION_OPERATION_H

#include <BaSyx/submodel/api_v2/submodelelement/operation/IOperation.h>
#include <BaSyx/submodel/simple/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/simple/common/ElementContainer.h>

namespace basyx {
namespace submodel {
namespace simple {

class Operation : 
	public SubmodelElement, 
	public api::IOperation
{
private:
	ElementContainer<ISubmodelElement> inputVariables;
	ElementContainer<ISubmodelElement> outputVariables;
	ElementContainer<ISubmodelElement> inOutVariables;
	basyx::object invokable;
public:
	Operation(const std::string & idShort);
	
	~Operation() = default;

	// Inherited via IOperation
	virtual api::IElementContainer<ISubmodelElement> & getInputVariables() override;
	virtual api::IElementContainer<ISubmodelElement> & getOutputVariables() override;
	virtual api::IElementContainer<ISubmodelElement> & getInOutputVariables() override;
	virtual basyx::object invoke(basyx::object args) override;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_OPERATION_OPERATION_H */
