#include <BaSyx/submodel/simple/submodelelement/operation/Operation.h>

using namespace basyx::submodel::simple;
using namespace basyx::submodel::api;

basyx::submodel::simple::Operation::Operation(const std::string & idShort)
	: SubmodelElement(idShort)
	, invokable(basyx::object::make_null())
{
};

IElementContainer<ISubmodelElement> & Operation::getInputVariables()
{
	return this->inputVariables;
}

IElementContainer<ISubmodelElement> & Operation::getOutputVariables()
{
	return this->outputVariables;
}

IElementContainer<ISubmodelElement> & Operation::getInOutputVariables()
{
	return this->inOutVariables;
}

basyx::object Operation::invoke(basyx::object args)
{
	return this->invokable;
}