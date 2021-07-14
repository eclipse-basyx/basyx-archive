#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_OPERATION_OPERATIONVARIABLE_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_OPERATION_OPERATIONVARIABLE_H

#include <BaSyx/submodel/api_v2/submodelelement/operation/IOperationVariable.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElementFactory.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>

namespace basyx {
namespace submodel {
namespace map {

class OperationVariable : 
	public SubmodelElement, 
	public api::IOperationVariable,
	public ModelType<ModelTypes::OperationVariable>
{
public:
  struct Path {
    static constexpr const char Value[] = "value";
  };

private:
	std::unique_ptr<SubmodelElement> value;

public:
	OperationVariable(const std::string & idShort, std::unique_ptr<SubmodelElement> value);
	OperationVariable(basyx::object obj);

	OperationVariable(const OperationVariable & other);
	OperationVariable(OperationVariable && other) noexcept;

	OperationVariable & operator=(const OperationVariable & other) = default;
	OperationVariable & operator=(OperationVariable && other) noexcept = default;

	~OperationVariable() = default;

	// Inherited via IOperationVariable
	virtual SubmodelElement & getValue() const;

	virtual inline KeyElements getKeyElementType() const override { return KeyElements::OperationVariable; };
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_OPERATION_OPERATIONVARIABLE_H */
