#ifndef BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_OPERATION_OPERATION_H
#define BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_OPERATION_OPERATION_H

#include <BaSyx/submodel/api_v2/submodelelement/operation/IOperation.h>
#include <BaSyx/submodel/simple/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/simple/submodelelement/operation/OperationVariable.h>
#include <BaSyx/submodel/simple/common/ElementContainer.h>

namespace basyx {
namespace submodel {
namespace simple {

class Operation
  : public SubmodelElement
  , public api::IOperation
{
private:
	ElementContainer<api::IOperationVariable> inputVariables;
	ElementContainer<api::IOperationVariable> outputVariables;
	ElementContainer<api::IOperationVariable> inOutVariables;
	basyx::object invokable;
public:
	Operation(const std::string & idShort);
	
	~Operation() = default;

	// Inherited via IOperation
	virtual api::IElementContainer<api::IOperationVariable> & getInputVariables() override;
	virtual api::IElementContainer<api::IOperationVariable> & getOutputVariables() override;
	virtual api::IElementContainer<api::IOperationVariable> & getInOutputVariables() override;
	virtual basyx::object invoke(basyx::object args) override;

	void addInputVariable(std::unique_ptr<OperationVariable> operationVariable);
  void addOutputVariable(std::unique_ptr<OperationVariable> operationVariable);
  void addInOutVariable(std::unique_ptr<OperationVariable> operationVariable);
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_OPERATION_OPERATION_H */
