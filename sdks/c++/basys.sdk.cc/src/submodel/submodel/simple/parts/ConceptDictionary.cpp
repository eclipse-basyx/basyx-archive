#include <BaSyx/submodel/simple/parts/ConceptDictionary.h>


namespace basyx {
namespace submodel {
namespace simple {
using namespace basyx::submodel::api;

ConceptDictionary::ConceptDictionary(const std::string & idShort)
  : conceptDescriptions()
  , referable(idShort)
{}

const api::IElementContainer<api::IConceptDescription> & ConceptDictionary::getConceptDescriptions() const
{
  return this->conceptDescriptions;
}

void ConceptDictionary::addConceptDescription(std::unique_ptr<ConceptDescription> description)
{
  this->conceptDescriptions.addElement(std::move(description));
}

const std::string & ConceptDictionary::getIdShort() const
{
	return this->referable.getIdShort();
}

const std::string * const ConceptDictionary::getCategory() const
{
	return this->referable.getCategory();
};

void ConceptDictionary::setCategory(const std::string & category)
{
	this->referable.setCategory(category);
}

LangStringSet & ConceptDictionary::getDescription()
{
	return this->referable.getDescription();
}

const LangStringSet & ConceptDictionary::getDescription() const
{
	return this->referable.getDescription();
}

IReferable * ConceptDictionary::getParent() const
{
	return this->referable.getParent();
}

void ConceptDictionary::setParent(IReferable * parent)
{
	this->referable.setParent(parent);
}

simple::Reference ConceptDictionary::getReference() const
{
	return this->referable.getReference();
};


}
}
}