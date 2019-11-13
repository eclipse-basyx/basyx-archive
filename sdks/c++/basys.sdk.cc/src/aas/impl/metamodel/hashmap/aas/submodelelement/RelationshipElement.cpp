/*
 * RelationshipElement.cpp
 *
 *      Author: wendel
 */

#include "RelationshipElement.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace submodelelement {

RelationshipElement::RelationshipElement()
{}

RelationshipElement::RelationshipElement(const std::shared_ptr<aas::reference::IReference>& first, const std::shared_ptr<aas::reference::IReference>& second) :
  first {first},
  second {second}
{}

void RelationshipElement::setFirst(const std::shared_ptr<aas::reference::IReference>& first)
{
  this->first = first;
}

std::shared_ptr<aas::reference::IReference> RelationshipElement::getFirst() const
{
  return this->first;
}

void RelationshipElement::setSecond(const std::shared_ptr<aas::reference::IReference>& second)
{
  this->second = second;
}

std::shared_ptr<aas::reference::IReference> RelationshipElement::getSecond() const
{
  return second;
}
}
}
}
}
}
