#ifndef BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_OPERATION_OPERATIONVARIABLE_H
#define BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_OPERATION_OPERATIONVARIABLE_H

#include <BaSyx/submodel/api_v2/submodelelement/operation/IOperationVariable.h>
#include <BaSyx/submodel/simple/submodelelement/SubmodelElement.h>

namespace basyx {
namespace submodel {
namespace simple {

template<typename T>
class OperationVariable : 
	public virtual SubmodelElement, 
	public virtual IOperationVariable
{
private:
	std::unique_ptr<ISubmodelElement> value;
public:
	OperationVariable(const std::string & idShort) : SubmodelElement(idShort, Kind::Type)
	{};

	OperationVariable(const std::string & idShort, std::unique_ptr<ISubmodelElement> value)
		: SubmodelElement(idShort, Kind::Type)
		, value(std::move(value))
	{
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

#endif /* BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_OPERATION_OPERATIONVARIABLE_H */
