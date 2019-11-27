/*
 * OperationVariable.cpp
 *
 *      Author: wendel
 */

#include "OperationVariable.h"

namespace basyx {
namespace submodel {

OperationVariable::OperationVariable()
	: ModelType("OperationVariable")
{
}

OperationVariable::OperationVariable(basyx::object object)
	: vab::ElementMap{object}
{
}



basyx::object OperationVariable::getValue() const
{
//  return this->value;
	return nullptr;
}

std::string OperationVariable::getType() const
{
	return this->map.getProperty(Path::Type).GetStringContent();
}

void OperationVariable::setValue(const std::shared_ptr<ISubmodelElement> & value)
{
//  this->value = value;
}

void OperationVariable::setType(const std::string & string)
{
	this->map.insertKey(Path::Type, string, true);
}

}
}
