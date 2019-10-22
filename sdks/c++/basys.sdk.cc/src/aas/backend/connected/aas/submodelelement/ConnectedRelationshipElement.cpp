/*
 * ConnectedRelationshipElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedRelationshipElement.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {


ConnectedRelationshipElement::ConnectedRelationshipElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedSubmodelElement(proxy)
{}

void ConnectedRelationshipElement::setFirst(const basyx::any & first)
{
  this->setProxyValue(submodelelement::RelationshipElementPath::FIRST, first);
}

basyx::any ConnectedRelationshipElement::getFirst() const
{
  return this->getProxyValue(submodelelement::RelationshipElementPath::FIRST);
}

void ConnectedRelationshipElement::setSecond(const basyx::any & second)
{
  this->setProxyValue(submodelelement::RelationshipElementPath::SECOND, second);
}

basyx::any ConnectedRelationshipElement::getSecond() const
{
  return this->getProxyValue(submodelelement::RelationshipElementPath::SECOND);
}

}
}
}
}
