/*
 * ConnectedReferenceElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedReferenceElement.h"

namespace basyx {
namespace submodel {

ConnectedReferenceElement::ConnectedReferenceElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedDataElement(proxy)
{}

void ConnectedReferenceElement::setValue(const std::shared_ptr<IReference> & ref)
{
  //todo
  //this->setProxyValue(submodelelement::property::PropertyPaths::VALUE, ref);
}

std::shared_ptr<IReference> ConnectedReferenceElement::getValue() const
{
  //todo 
  return nullptr;// this->getProxyValue(submodelelement::property::PropertyPaths::VALUE);
}

}
}

