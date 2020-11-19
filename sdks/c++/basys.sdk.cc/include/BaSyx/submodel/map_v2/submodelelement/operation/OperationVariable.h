#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_OPERATION_OPERATIONVARIABLE_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_OPERATION_OPERATIONVARIABLE_H

#include <BaSyx/submodel/api_v2/submodelelement/operation/IOperationVariable.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>

namespace basyx {
namespace submodel {
namespace map {

struct OperationVariablePath {
  static constexpr char Value[] = "value";
};

constexpr char OperationVariablePath::Value[];

template<typename T>
class OperationVariable : 
	public SubmodelElement, 
	public api::IOperationVariable,
	public ModelType<ModelTypes::OperationVariable>
{
private:
	std::unique_ptr<SubmodelElement> value;
public:
	OperationVariable(const std::string & idShort, std::unique_ptr<SubmodelElement> value)
		: SubmodelElement(idShort, ModelingKind::Template)
		, value(std::move(value))
	{
		this->map.insertKey(OperationVariablePath::Value, this->value->getMap());
	};

	OperationVariable(const OperationVariable & other) = default;
	OperationVariable(OperationVariable && other) noexcept = default;

	OperationVariable & operator=(const OperationVariable & other) = default;
	OperationVariable & operator=(OperationVariable && other) noexcept = default;

	~OperationVariable() = default;
public:
	// Inherited via IOperationVariable
	virtual ISubmodelElement & getValue() const
	{
		return *this->value;
	};

	virtual inline KeyElements getKeyElementType() const override { return KeyElements::OperationVariable; };
};


}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_OPERATION_OPERATIONVARIABLE_H */
