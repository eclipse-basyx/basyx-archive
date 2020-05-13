#include <BaSyx/submodel/simple/qualifier/Qualifiable.h>

using namespace basyx::submodel::api;
using namespace basyx::submodel::simple;

Qualifiable::Qualifiable(const std::vector<Formula> & formulas, const std::vector<Qualifier> & qualifiers)
	: formulas(formulas), qualifiers(qualifiers)
{
};

Qualifiable::Qualifiable(std::vector<Formula> && formulas, std::vector<Qualifier> && qualifiers)
	: formulas(formulas), qualifiers(qualifiers)
{
};

void basyx::submodel::simple::Qualifiable::addFormula(const api::IFormula & formula)
{
	this->formulas.emplace_back(std::move(formula.getDependencies()));
}

void basyx::submodel::simple::Qualifiable::addQualifier(const api::IQualifier & qualifier)
{
	this->qualifiers.emplace_back(qualifier);
}

std::vector<Formula> basyx::submodel::simple::Qualifiable::getFormulas() const
{
	return this->formulas;
}

std::vector<Qualifier> basyx::submodel::simple::Qualifiable::getQualifiers() const
{
	return this->qualifiers;
}
