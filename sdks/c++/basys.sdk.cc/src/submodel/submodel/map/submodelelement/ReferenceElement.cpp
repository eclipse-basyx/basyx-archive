/*
 * ReferenceElement.cpp
 *
 *      Author: wendel
 */

#include "ReferenceElement.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace submodelelement {

ReferenceElement::ReferenceElement()
{}

ReferenceElement::ReferenceElement(const std::shared_ptr<aas::reference::IReference>& reference) :
  reference {reference}
{}

void ReferenceElement::setValue(const std::shared_ptr<aas::reference::IReference> & ref)
{
  this->reference = reference;
}

std::shared_ptr<aas::reference::IReference> ReferenceElement::getValue() const
{
  return this->reference;
}

}
}
}
}
}
