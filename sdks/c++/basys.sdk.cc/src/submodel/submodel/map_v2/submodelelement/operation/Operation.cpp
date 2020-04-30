#include <BaSyx/submodel/map_v2/submodelelement/operation/Operation.h>

using namespace basyx::submodel::map;
using namespace basyx::submodel::api;

Operation::Operation(const std::string & idShort, basyx::object invokable)
	: SubmodelElement(idShort)
	, invokable(basyx::object::make_null())
{
	this->invokable = invokable;
	this->map.insertKey("inputVariable", inputVariables.getMap());
	this->map.insertKey("outputVariable", outputVariables.getMap());
	this->map.insertKey("inoutputVariable", inOutVariables.getMap());
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
	return this->invokable.invoke(args);
}