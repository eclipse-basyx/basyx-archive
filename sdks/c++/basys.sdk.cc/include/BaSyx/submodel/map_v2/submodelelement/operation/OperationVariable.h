#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_OPERATION_OPERATIONVARIABLE_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_OPERATION_OPERATIONVARIABLE_H

#include <BaSyx/submodel/api_v2/submodelelement/operation/IOperationVariable.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>

namespace basyx {
namespace submodel {
namespace map {

template<typename T>
class OperationVariable : 
	public SubmodelElement, 
	public IOperationVariable
{
private:
	std::unique_ptr<SubmodelElement> value;
public:
	OperationVariable(const std::string & idShort, std::unique_ptr<ISubmodelElement> value)
		: SubmodelElement(idShort, Kind::Type)
		, value(std::move(value))
	{
		this->value = dynamic_cast<SubmodelElement>(value.get());
		this->map.insertKey("value", this->value->getMap());
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
};


}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_OPERATION_OPERATIONVARIABLE_H */
