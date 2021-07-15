#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_OPERATION_OPERATION_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_OPERATION_OPERATION_H

#include <BaSyx/submodel/api_v2/submodelelement/operation/IOperation.h>
#include <BaSyx/submodel/map_v2/submodelelement/operation/OperationVariable.h>
#include <BaSyx/submodel/map_v2/common/ElementContainer.h>
#include <BaSyx/submodel/map_v2/common/ModelType.h>

namespace basyx {
namespace submodel {
namespace map {

class Operation : 
	public api::IOperation,
	public SubmodelElement,
	public ModelType<ModelTypes::Operation>
{
public:
  struct Path {
    static constexpr char Invokable[] = "invokable";
    static constexpr char InputVariable[] = "inputVariable";
    static constexpr char OutputVariable[] = "outputVariable";
    static constexpr char InoutputVariable[] = "inoutputVariable";
  };
private:
	ElementContainer<api::IOperationVariable> inputVariables;
	ElementContainer<api::IOperationVariable> outputVariables;
	ElementContainer<api::IOperationVariable> inOutVariables;
	basyx::object invokable;
public:
	Operation(const std::string & idShort, basyx::object invokable);
  Operation(basyx::object obj);
	
	~Operation() = default;

	// Inherited via IOperation
	virtual api::IElementContainer<api::IOperationVariable> & getInputVariables() override;
	virtual api::IElementContainer<api::IOperationVariable> & getOutputVariables() override;
	virtual api::IElementContainer<api::IOperationVariable> & getInOutputVariables() override;
	virtual basyx::object invoke(basyx::object args) override;

  void addInputVariable(std::unique_ptr<OperationVariable> operationVariable);
  void addOutputVariable(std::unique_ptr<OperationVariable> operationVariable);
  void addInOutVariable(std::unique_ptr<OperationVariable> operationVariable);

	virtual KeyElements getKeyElementType() const override { return KeyElements::Operation; };
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_OPERATION_OPERATION_H */
