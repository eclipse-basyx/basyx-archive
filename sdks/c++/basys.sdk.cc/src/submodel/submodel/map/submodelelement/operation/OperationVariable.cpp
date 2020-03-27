/*
 * ModelType.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/submodelelement/operation/OperationVariable.h>

namespace basyx {
namespace submodel {

OperationVariable::OperationVariable()
	: ModelType(IOperationVariable::Path::ModelType)
  , vab::ElementMap{}
{}

OperationVariable::OperationVariable(basyx::object object)
	: vab::ElementMap{object}
{}

std::shared_ptr<ISubmodelElement> OperationVariable::getValue() const
{
	return std::make_shared<SubmodelElement>(this->map.getProperty(IOperationVariable::Path::Value));
}

std::string OperationVariable::getType() const
{
	return this->map.getProperty(Path::Type).GetStringContent();
}

void OperationVariable::setValue(const SubmodelElement & value)
{
	this->map.insertKey(IOperationVariable::Path::Value, value.getMap(), true);
}

void OperationVariable::setValue(const ISubmodelElement & value)
{
  auto map = SubmodelElement(value).getMap();
  this->map.insertKey(IOperationVariable::Path::Value, map, true);
}

void OperationVariable::setType(const std::string & string)
{
	this->map.insertKey(Path::Type, string, true);
}

}
}
