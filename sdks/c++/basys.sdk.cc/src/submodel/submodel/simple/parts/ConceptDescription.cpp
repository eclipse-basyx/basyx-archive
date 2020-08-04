#include <BaSyx/submodel/simple/parts/ConceptDescription.h>

using namespace basyx::submodel::simple;
using namespace basyx::submodel;


ConceptDescription::ConceptDescription(const std::string & idShort, const Identifier & identifier)
	: Identifiable{idShort, identifier}
{}

const api::IElementContainer<api::IDataSpecification> & ConceptDescription::getEmbeddedDataSpecification() const
{
	return this->embeddedDataSpecs;
}

const std::vector<std::unique_ptr<api::IReference>> & ConceptDescription::getIsCaseOf() const
{
	return this->isCaseOf;
}

void ConceptDescription::addEmbeddedDataSpecification(std::unique_ptr<DataSpecification> data_specification)
{
  this->embeddedDataSpecs.addElement(std::move(data_specification));
}

void ConceptDescription::addIsCaseOf(std::unique_ptr<Reference> reference)
{
  this->isCaseOf.push_back(std::move(reference));
};

// Inherited via IHasDataSpecification
void ConceptDescription::addDataSpecification(const Reference & reference)
{
	return this->dataSpec.addDataSpecification(reference);
};

const std::vector<Reference> ConceptDescription::getDataSpecificationReference() const
{
	return this->dataSpec.getDataSpecificationReference();
}
