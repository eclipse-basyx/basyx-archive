/*
 * ConnectedReferenceElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedReferenceElement.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {
ConnectedReferenceElement::ConnectedReferenceElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedDataElement(proxy)
{}

void ConnectedReferenceElement::setValue(const basyx::any & ref)
{
  this->setProxyValue(submodelelement::property::PropertyPaths::VALUE, ref);
}

basyx::any ConnectedReferenceElement::getValue() const
{
  return this->getProxyValue(submodelelement::property::PropertyPaths::VALUE);
}

}
}
}
}

