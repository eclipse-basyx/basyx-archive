#include <BaSyx/submodel/simple/parts/View.h>

using namespace basyx::submodel;
using namespace basyx::submodel::simple;

View::View(const std::string &idShort, Referable * parent)
  : referable(idShort, parent)
  , dataSpec()
{}

const api::IElementContainer<api::IReferable> & View::getContainedElements() const
{
  return this->contained_elements;
}

void View::addContainedElement(std::unique_ptr<Referable> referable)
{
  this->contained_elements.addElement(std::move(referable));
}

const api::IReference &View::getSemanticId() const
{
  return this->semanticId;
}

void View::setSemanticId(const api::IReference &reference)
{
  this->semanticId = reference;
}

void View::addDataSpecification(const simple::Reference & reference)
{
	this->dataSpec.addDataSpecification(reference);
}

const std::vector<simple::Reference> View::getDataSpecificationReference() const
{
	return this->dataSpec.getDataSpecificationReference();
}

const std::string & View::getIdShort() const
{
	return this->referable.getIdShort();
}

const std::string * const View::getCategory() const
{
	return this->referable.getCategory();
};

void View::setCategory(const std::string & category)
{
	this->referable.setCategory(category);
};

LangStringSet & View::getDescription()
{
	return this->referable.getDescription();
}

const LangStringSet & View::getDescription() const
{
	return this->referable.getDescription();
}

api::IReferable * View::getParent() const
{
	return this->referable.getParent();
}

void View::setParent(IReferable * parent)
{
	this->referable.setParent(parent);
}

simple::Reference View::getReference() const
{
	return this->referable.getReference();
};

