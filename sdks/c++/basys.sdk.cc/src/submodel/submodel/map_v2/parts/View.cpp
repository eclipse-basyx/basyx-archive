#include <BaSyx/submodel/map_v2/parts/View.h>

using namespace basyx::submodel;
using namespace basyx::submodel::map;

constexpr char View::Path::ContainedElements[];
constexpr char View::Path::SemanticId[];

View::View(const std::string &idShort, Referable *parent)
  : vab::ElementMap{}
  , Referable(idShort, parent)
{
  this->map.insertKey(Path::ContainedElements, this->contained_elements.getMap());
}

const api::IElementContainer<api::IReferable> & View::getContainedElements() const
{
  return this->contained_elements;
}

void View::addContainedElement(std::unique_ptr<Referable> referable)
{
  this->contained_elements.addElement(std::move(referable));
}

const api::IReference  *View::getSemanticId() const
{
  return this->semanticId.get();
}

void View::setSemanticId(std::unique_ptr<Reference> reference)
{
  this->semanticId = std::move(reference);
  this->map.insertKey(Path::SemanticId, this->semanticId->getMap());
}

