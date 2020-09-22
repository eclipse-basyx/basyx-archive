#include <BaSyx/submodel/map_v2/submodelelement/operation/Operation.h>

using namespace basyx::submodel::map;
using namespace basyx::submodel::api;

constexpr char Operation::Path::Invokable[];
constexpr char Operation::Path::InputVariable[];
constexpr char Operation::Path::OutputVariable[];
constexpr char Operation::Path::InoutputVariable[];

Operation::Operation(const std::string & idShort, basyx::object invokable)
	: SubmodelElement(idShort)
	, invokable(basyx::object::make_null())
{
	this->invokable = invokable;
	this->map.insertKey(Path::Invokable, this->invokable);
	this->map.insertKey(Path::InputVariable, inputVariables.getMap());
	this->map.insertKey(Path::OutputVariable, outputVariables.getMap());
	this->map.insertKey(Path::InoutputVariable, inOutVariables.getMap());
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