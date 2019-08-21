/*
 * Operation.cpp
 *
 *      Author: wendel
 */

#include "Operation.h"

std::vector<std::shared_ptr<IOperationVariable>> Operation::getParameterTypes()
{
  return this->parameter_types;
}

void Operation::setReturnTypes(std::vector<std::shared_ptr<IOperationVariable>> returnTypes)
{
  this->return_types = return_types;
}

std::vector<std::shared_ptr<IOperationVariable>> Operation::getReturnTypes()
{
  return this->return_types;
}

void Operation::SetParameterTypes(std::vector<std::shared_ptr<IOperationVariable>> parameterTypes)
{
  this->parameter_types = parameter_types;
}
