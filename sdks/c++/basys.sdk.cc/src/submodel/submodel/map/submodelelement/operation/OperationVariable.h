/*
 * OperationVariable.h
 *
 *      Author: wendel
 */

#ifndef SUBMODEL_METAMODEL_SUBMODELEMENT_OPERATION_OPERATIONVARIABLE_H_
#define SUBMODEL_METAMODEL_SUBMODELEMENT_OPERATION_OPERATIONVARIABLE_H_

#include "submodel/api/submodelelement/operation/IOperationVariable.h"
#include "submodel/map/submodelelement/SubmodelElement.h"

#include "basyx/object.h"

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
	virtual basyx::object getValue() const override;
	virtual std::string getType() const override;
	                                                                        
	// not inherited
	void setValue(const std::shared_ptr<ISubmodelElement> & value);
	void setType(const std::string & string);
};

}
}

#endif
