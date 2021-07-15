#include <BaSyx/submodel/simple/submodelelement/operation/Operation.h>

using namespace basyx::submodel::simple;
using namespace basyx::submodel::api;

basyx::submodel::simple::Operation::Operation(const std::string & idShort)
	: SubmodelElement(idShort)
	, invokable(basyx::object::make_null())
{
};

IElementContainer<IOperationVariable> & Operation::getInputVariables()
{
	return this->inputVariables;
}

IElementContainer<IOperationVariable> & Operation::getOutputVariables()
{
	return this->outputVariables;
}

IElementContainer<IOperationVariable> & Operation::getInOutputVariables()
{
	return this->inOutVariables;
}

basyx::object Operation::invoke(basyx::object args)
{
	return this->invokable;
}

void Operation::addInputVariable(std::unique_ptr<OperationVariable> operationVariable)
{
  this->inputVariables.addElement(std::move(operationVariable));
}

void Operation::addOutputVariable(std::unique_ptr<OperationVariable> operationVariable)
{
  this->outputVariables.addElement(std::move(operationVariable));
}

void Operation::addInOutVariable(std::unique_ptr<OperationVariable> operationVariable)
{
  this->inOutVariables.addElement(std::move(operationVariable));
}
