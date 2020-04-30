#include <BaSyx/submodel/simple/parts/ConceptDescription.h>

using namespace basyx::submodel::simple;
using namespace basyx::submodel::api;


ConceptDescription::ConceptDescription(const std::string & idShort, const Identifier & identifier)
	: 	ident(idShort, identifier)
{
};


IElementContainer<IDataSpecification> & ConceptDescription::getEmbeddedDataSpecification()
{
	return this->embeddedDataSpecs;
}

std::vector<Reference>  & ConceptDescription::getIsCaseOf()
{
	return this->isCaseOf;
}


// Inherited via IHasDataSpecification
void ConceptDescription::addDataSpecification(const Reference & reference)
{
	return this->dataSpec.addDataSpecification(reference);
};

const std::vector<Reference> ConceptDescription::getDataSpecificationReference() const
{
	return this->dataSpec.getDataSpecificationReference();
};


const std::string & ConceptDescription::getIdShort() const
{
	return this->ident.getIdShort();
}

const std::string * const ConceptDescription::getCategory() const
{
	return this->ident.getCategory();
}

LangStringSet & ConceptDescription::getDescription()
{
	return this->ident.getDescription();
}

const LangStringSet & ConceptDescription::getDescription() const
{
	return this->ident.getDescription();
}

const IReferable * const ConceptDescription::getParent() const
{
	return this->ident.getParent();
}

const AdministrativeInformation & ConceptDescription::getAdministrativeInformation() const
{
	return this->ident.getAdministrativeInformation();
}

AdministrativeInformation & ConceptDescription::getAdministrativeInformation()
{
	return this->ident.getAdministrativeInformation();
}

Identifier ConceptDescription::getIdentification() const
{
	return this->ident.getIdentification();
}

bool ConceptDescription::hasAdministrativeInformation() const
{
	return this->ident.hasAdministrativeInformation();
}
