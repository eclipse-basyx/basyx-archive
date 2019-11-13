/*
 * Operation.cpp
 *
 *      Author: wendel
 */

#include "Operation.h"
#include "basyx/function.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace submodelelement {
namespace operation {

Operation::Operation()
{}

Operation::Operation(operation_var_list in, operation_var_list out, std::shared_ptr<basyx::function_base> invocable) :
  in_variables {in},
  out_variables {out},
  invocable {invocable}
{}

operation_var_list Operation::getParameterTypes() const
{
  return this->in_variables;
}

operation_var_list Operation::getReturnTypes() const
{
  return this->out_variables;
}

std::shared_ptr<basyx::function_base> Operation::getInvocable() const
{
  return this->invocable;
}

basyx::any Operation::invoke(basyx::objectCollection_t & parameters) const
{
  return this->invocable->invoke_any(parameters);
}

void Operation::setParameterTypes(const operation_var_list & parameterTypes)
{
  this->in_variables = parameterTypes;
}

void Operation::setReturnTypes(const operation_var_list & returnTypes)
{
  this->out_variables = returnTypes;
}

void Operation::setInvocable(const std::shared_ptr<basyx::function_base>& invocable)
{
  this->invocable = invocable;
}

}
}
}
}
}
}
