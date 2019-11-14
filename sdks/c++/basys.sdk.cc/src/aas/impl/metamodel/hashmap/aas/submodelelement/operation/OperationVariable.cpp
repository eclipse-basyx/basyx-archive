/*
 * OperationVariable.cpp
 *
 *      Author: wendel
 */

#include "OperationVariable.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace submodelelement {
namespace operation {

OperationVariable::OperationVariable()
{}

OperationVariable::OperationVariable(const std::shared_ptr<aas::submodelelement::ISubmodelElement> & value) :
  value {value}
{}

std::shared_ptr<aas::submodelelement::ISubmodelElement> OperationVariable::getValue() const
{
  return this->value;
}

void OperationVariable::setValue(const std::shared_ptr<aas::submodelelement::ISubmodelElement> & value)
{
  this->value = value;
}

}
}
}
}
}
}
