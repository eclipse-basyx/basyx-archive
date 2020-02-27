/*
 * RelationshipElement.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/submodelelement/RelationshipElement.h>
#include <BaSyx/submodel/map/reference/Reference.h>

namespace basyx {
namespace submodel {

RelationshipElement::RelationshipElement() :
  vab::ElementMap {},
  ModelType{IRelationshipElement::Path::ModelType}
{}

RelationshipElement::RelationshipElement(const IReference & first, const IReference & second) :
  vab::ElementMap{},
  ModelType {IRelationshipElement::Path::ModelType}
{
  this->setFirst(first);
  this->setSecond(second);
}

void RelationshipElement::setFirst(const IReference& first)
{
  Reference ref {first};
  this->insertMapElement(IRelationshipElement::Path::First, ref);
}

std::shared_ptr<IReference> RelationshipElement::getFirst() const
{
  return std::make_shared<Reference>(this->map.getProperty(IRelationshipElement::Path::First));
}

void RelationshipElement::setSecond(const IReference& second)
{
  Reference ref {second};
  this->insertMapElement(IRelationshipElement::Path::Second, ref);
}

std::shared_ptr<IReference> RelationshipElement::getSecond() const
{
  return std::make_shared<Reference>(this->map.getProperty(IRelationshipElement::Path::Second));
}

}
}
