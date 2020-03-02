/*
 * Operation.h
 *
 *      Author: wendel
 */

#ifndef SUBMODEL_METAMODEL_SUBMODELEMENT_OPERATION_OPERATION_H_
#define SUBMODEL_METAMODEL_SUBMODELEMENT_OPERATION_OPERATION_H_

#include <BaSyx/shared/object.h>
#include <BaSyx/shared/types.h>

#include <BaSyx/submodel/api/submodelelement/operation/IOperationVariable.h>
#include <BaSyx/submodel/api/submodelelement/operation/IOperation.h>
#include <BaSyx/submodel/map/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map/submodelelement/operation/OperationVariable.h>


namespace basyx {
namespace submodel {

class Operation : 
	public virtual SubmodelElement, 
	public virtual IOperation
{
public:
	using Path = IOperation::Path;
public:
	~Operation() = default;

	// constructors
	Operation();
	Operation(const IOperation & other);
	Operation(const basyx::object & obj);
	
	// Inherited via IOperation
	virtual basyx::specificCollection_t<IOperationVariable> getParameterTypes() const override;
	virtual std::shared_ptr<IOperationVariable> getReturnType() const override;
	virtual basyx::object getInvocable() const override;

	// not inherited
	void setParameterTypes(const basyx::specificCollection_t<IOperationVariable> & parameterTypes);
	void setReturnTypes(const std::shared_ptr<IOperationVariable> & returnTypes);
	void setInvocable(basyx::object invocable);

	// helper methods
	template<typename T>
	void addParameter(const std::string & name)
	{
		OperationVariable op_var;
		op_var.setIdShort(name);
		op_var.setType(util::to_string<basyx::type::basyx_type<T>::value_type>());
		this->map.getProperty(Path::Input).insert(op_var.getMap());
	};

	template<typename T>
	void setReturnType(const std::string & name)
	{
		OperationVariable ret_var;
		ret_var.setIdShort(name);
		ret_var.setType(util::to_string<basyx::type::basyx_type<T>::value_type>());
		this->map.insertKey(Path::Output, ret_var.getMap(), true);
	};

	virtual basyx::object invoke(basyx::object & parameters) const override;
};

}
}

#endif
