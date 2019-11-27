/*
 * Operation.cpp
 *
 *      Author: wendel
 */

#include "Operation.h"
#include "basyx/object/obj_function.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace submodelelement {
namespace operation {

Operation::Operation()
{}

Operation::Operation(operation_var_list in, operation_var_list out, basyx::detail::functionWrapper invocable) :
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

basyx::detail::functionWrapper Operation::getInvocable() const
{
  return this->invocable;
}

basyx::object Operation::invoke(basyx::object & parameters) const
{
  return invocable.invoke(parameters); 
}

void Operation::setParameterTypes(const operation_var_list & parameterTypes)
{
  this->in_variables = parameterTypes;
}

void Operation::setReturnTypes(const operation_var_list & returnTypes)
{
  this->out_variables = returnTypes;
}

void Operation::setInvocable(basyx::detail::functionWrapper invocable)
{
  this->invocable = invocable;
}

}
}
}
}
}
}
