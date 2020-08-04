#include <BaSyx/submodel/simple/parts/View.h>

using namespace basyx::submodel;
using namespace basyx::submodel::simple;

View::View(const std::string &idShort, const Referable *parent)
  : Referable(idShort, parent)
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