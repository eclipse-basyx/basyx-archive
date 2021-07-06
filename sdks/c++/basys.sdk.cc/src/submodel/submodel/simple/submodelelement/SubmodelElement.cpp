#include <BaSyx/submodel/simple/submodelelement/SubmodelElement.h>

using namespace basyx::submodel;
using namespace basyx::submodel::simple;
using namespace basyx::submodel::api;

SubmodelElement::SubmodelElement(const std::string & idShort, ModelingKind kind)
	: referable(idShort)
	, kind(kind)
{

}

const IReference & SubmodelElement::getSemanticId() const
{
	return this->semanticId;
};

void SubmodelElement::setSemanticId(const api::IReference & semanticId)
{
	this->semanticId = semanticId;
};

void SubmodelElement::addDataSpecification(const Reference & reference)
{
	this->dataSpecification.addDataSpecification(reference);
}

const std::vector<Reference> SubmodelElement::getDataSpecificationReference() const
{
	return this->dataSpecification.getDataSpecificationReference();
}

const std::string & SubmodelElement::getIdShort() const
{
	return this->referable.getIdShort();
}

void SubmodelElement::setCategory(const std::string &category)
{
  this->referable.setCategory(category);
}

const std::string * const SubmodelElement::getCategory() const
{
	return this->referable.getCategory();
}

simple::LangStringSet & SubmodelElement::getDescription()
{
	return this->referable.getDescription();
}

const simple::LangStringSet & SubmodelElement::getDescription() const
{
	return this->referable.getDescription();
}

IReferable * SubmodelElement::getParent() const
{
	return this->referable.getParent();
}

void SubmodelElement::setParent(IReferable * parent)
{
	this->setParent(parent);
};

ModelingKind SubmodelElement::getKind() const
{
	return this->kind;
}

void SubmodelElement::addFormula(const api::IFormula &formula)
{
  this->qualifiable.addFormula(formula);
}

void SubmodelElement::addQualifier(const api::IQualifier &qualifier)
{
  this->qualifiable.addQualifier(qualifier);
}

std::vector<Qualifier> SubmodelElement::getQualifiers() const
{
  return this->qualifiable.getQualifiers();
}

std::vector<Formula> SubmodelElement::getFormulas() const
{
  return this->qualifiable.getFormulas();
}

ModelTypes SubmodelElement::GetModelType() const
{
  return this->modelType;
}

Key SubmodelElement::getKey(bool local) const 
{
	return this->referable.getKey(local);
}

simple::Reference SubmodelElement::getReference() const
{
	return this->referable.getReference();
}
