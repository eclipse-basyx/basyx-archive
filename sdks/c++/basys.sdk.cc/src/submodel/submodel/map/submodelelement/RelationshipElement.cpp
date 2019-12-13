/*
 * RelationshipElement.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/submodelelement/RelationshipElement.h>

namespace basyx {
namespace submodel {

RelationshipElement::RelationshipElement()
{}

RelationshipElement::RelationshipElement(const std::shared_ptr<IReference>& first, const std::shared_ptr<IReference>& second) :
  first {first},
  second {second}
{}

void RelationshipElement::setFirst(const std::shared_ptr<IReference>& first)
{
  this->first = first;
}

std::shared_ptr<IReference> RelationshipElement::getFirst() const
{
  return this->first;
}

void RelationshipElement::setSecond(const std::shared_ptr<IReference>& second)
{
  this->second = second;
}

std::shared_ptr<IReference> RelationshipElement::getSecond() const
{
  return second;
}

}
}
