#include <BaSyx/submodel/map_v2/parts/View.h>

using namespace basyx::submodel;
using namespace basyx::submodel::map;

View::View(const std::string &idShort, const Referable *parent)
  : vab::ElementMap{}
  , Referable(idShort, parent)
{
  this->map.insertKey("ContainedElements", this->contained_elements.getMap());
  this->map.insertKey("SemanticId", this->semanticId.getMap());
}

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

