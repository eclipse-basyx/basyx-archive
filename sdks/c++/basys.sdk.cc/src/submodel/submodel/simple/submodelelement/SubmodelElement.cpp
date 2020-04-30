#include <BaSyx/submodel/simple/submodelelement/SubmodelElement.h>

using namespace basyx::submodel;
using namespace basyx::submodel::simple;
using namespace basyx::submodel::api;

SubmodelElement::SubmodelElement(const std::string & idShort, Kind kind)
	: referable(idShort)
	, kind(kind)
{

}

IReference & SubmodelElement::getSemanticId()
{
	return this->semanticId;
};

void SubmodelElement::setSemanticId(Reference semanticId)
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

const IReferable * const SubmodelElement::getParent() const
{
	return this->referable.getParent();
}

Kind SubmodelElement::getKind() const
{
	return this->kind;
}