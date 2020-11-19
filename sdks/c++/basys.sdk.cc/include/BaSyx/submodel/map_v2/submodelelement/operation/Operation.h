#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_OPERATION_OPERATION_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_OPERATION_OPERATION_H

#include <BaSyx/submodel/api_v2/submodelelement/operation/IOperation.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>
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
	ElementContainer<ISubmodelElement> inputVariables;
	ElementContainer<ISubmodelElement> outputVariables;
	ElementContainer<ISubmodelElement> inOutVariables;
	basyx::object invokable;
public:
	Operation(const std::string & idShort, basyx::object invokable);
	
	~Operation() = default;

	// Inherited via IOperation
	virtual api::IElementContainer<ISubmodelElement> & getInputVariables() override;
	virtual api::IElementContainer<ISubmodelElement> & getOutputVariables() override;
	virtual api::IElementContainer<ISubmodelElement> & getInOutputVariables() override;
	virtual basyx::object invoke(basyx::object args) override;

	virtual KeyElements getKeyElementType() const override { return KeyElements::Operation; };
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_OPERATION_OPERATION_H */
