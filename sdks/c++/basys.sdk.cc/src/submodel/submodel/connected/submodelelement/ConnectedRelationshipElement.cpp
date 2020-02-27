/*
 * ConnectedRelationshipElement.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/connected/submodelelement/ConnectedRelationshipElement.h>

namespace basyx {
namespace submodel {

ConnectedRelationshipElement::ConnectedRelationshipElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedSubmodelElement(proxy)
{}

void ConnectedRelationshipElement::setFirst(const IReference & first)
{
  //todo
  //this->setProxyValue(submodelelement::RelationshipElementPath::FIRST, first);
}

std::shared_ptr<IReference> ConnectedRelationshipElement::getFirst() const
{
  //todo
  return nullptr;// this->getProxyValue(submodelelement::RelationshipElementPath::FIRST);
}

void ConnectedRelationshipElement::setSecond(const IReference & second)
{
  //todo
  //this->setProxyValue(submodelelement::RelationshipElementPath::SECOND, second);
}

std::shared_ptr<IReference> ConnectedRelationshipElement::getSecond() const
{
  //todo
  return nullptr;// this->getProxyValue(submodelelement::RelationshipElementPath::SECOND);
}

}
}
