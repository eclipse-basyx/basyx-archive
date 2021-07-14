#include <BaSyx/submodel/map_v2/submodelelement/operation/OperationVariable.h>

using namespace basyx::submodel::map;

constexpr char OperationVariable::Path::Value[];

OperationVariable::OperationVariable(const std::string & idShort, std::unique_ptr<SubmodelElement> value)
  : SubmodelElement(idShort, ModelingKind::Template)
  , value(std::move(value))
{
  this->map.insertKey(Path::Value, this->value->getMap());
}

OperationVariable::OperationVariable(basyx::object obj)
  : SubmodelElement(obj)
{
  this->value = SubmodelElementFactory::Create(obj.getProperty(Path::Value));
  this->map.insertKey(Path::Value, this->value->getMap());
}

OperationVariable::OperationVariable(const OperationVariable & other)
  : SubmodelElement(other.getMap())
{
  this->value = SubmodelElementFactory::Create(other.getValue().getMap());
  this->map.insertKey(Path::Value, this->value->getMap());
}

OperationVariable::OperationVariable(OperationVariable && other) noexcept
  : SubmodelElement(other.getMap())
{
  this->value = SubmodelElementFactory::Create(other.getValue().getMap());
  this->map.insertKey(Path::Value, this->value->getMap());
}

SubmodelElement & OperationVariable::getValue() const
{
  return *this->value;
}
