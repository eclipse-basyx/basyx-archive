/*
 * ModelType.h
 *
 *      Author: wendel
 */

#ifndef SUBMODEL_METAMODEL_SUBMODELEMENT_OPERATION_OPERATIONVARIABLE_H_
#define SUBMODEL_METAMODEL_SUBMODELEMENT_OPERATION_OPERATIONVARIABLE_H_

#include <BaSyx/submodel/api/submodelelement/operation/IOperationVariable.h>
#include <BaSyx/submodel/map/submodelelement/SubmodelElement.h>

#include <BaSyx/shared/object.h>

namespace basyx {
namespace submodel {

class OperationVariable : 
	public virtual SubmodelElement, 
	public virtual IOperationVariable
{
public:
	using Path = IOperationVariable::Path;
public:
	~OperationVariable() = default;
	
	// constructors
	OperationVariable();
	OperationVariable(basyx::object object);

	// Inherited via IOperationVariable
	virtual std::shared_ptr<ISubmodelElement> getValue() const override;
	virtual std::string getType() const override;
	                                                                        
	// not inherited
	void setValue(const ISubmodelElement & value);
	void setType(const std::string & string);
};

}
}

#endif
