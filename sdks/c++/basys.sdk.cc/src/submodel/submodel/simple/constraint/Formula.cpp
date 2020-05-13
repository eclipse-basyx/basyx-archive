#include <BaSyx/submodel/simple/constraint/Formula.h>


using namespace basyx::submodel;
using namespace basyx::submodel::simple;

Formula::Formula(const std::vector<Reference> & dependencies)
	: dependencies(dependencies)
{
};

Formula::Formula(std::vector<Reference> && dependencies)
	: dependencies(std::move(dependencies))
{
};


Formula::Formula(const api::IFormula & other)
	: dependencies(std::move(other.getDependencies()))
{
};

std::vector<simple::Reference> Formula::getDependencies() const
{
	return this->dependencies;
};

void Formula::addDependency(const api::IReference & reference)
{
	this->dependencies.emplace_back(reference.getKeys());
};

ModelTypes Formula::GetModelType() const
{
	return ModelTypes::Formula;
};