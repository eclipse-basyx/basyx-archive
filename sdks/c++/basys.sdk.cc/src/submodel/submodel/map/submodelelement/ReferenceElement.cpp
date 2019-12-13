/*
 * ReferenceElement.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/submodelelement/ReferenceElement.h>

namespace basyx {
namespace submodel {

ReferenceElement::ReferenceElement()
{}

ReferenceElement::ReferenceElement(const std::shared_ptr<IReference>& reference) :
  reference {reference}
{}

void ReferenceElement::setValue(const std::shared_ptr<IReference> & ref)
{
  this->reference = reference;
}

std::shared_ptr<IReference> ReferenceElement::getValue() const
{
  return this->reference;
}

}
}
