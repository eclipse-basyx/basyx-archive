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

void ConnectedRelationshipElement::setFirst(const std::shared_ptr<aas::reference::IReference> & first)
{
  //todo
  //this->setProxyValue(submodelelement::RelationshipElementPath::FIRST, first);
}

std::shared_ptr<aas::reference::IReference> ConnectedRelationshipElement::getFirst() const
{
  //todo
  return nullptr;// this->getProxyValue(submodelelement::RelationshipElementPath::FIRST);
}

void ConnectedRelationshipElement::setSecond(const std::shared_ptr<aas::reference::IReference> & second)
{
  //todo
  //this->setProxyValue(submodelelement::RelationshipElementPath::SECOND, second);
}

std::shared_ptr<aas::reference::IReference> ConnectedRelationshipElement::getSecond() const
{
  //todo
  return nullptr;// this->getProxyValue(submodelelement::RelationshipElementPath::SECOND);
}

}
}
}
}
