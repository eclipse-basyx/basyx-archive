/*
 * ReferenceElement.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/submodelelement/ReferenceElement.h>
#include <BaSyx/submodel/map/reference/Reference.h>

namespace basyx {
namespace submodel {

ReferenceElement::ReferenceElement() :
  vab::ElementMap{},
  ModelType{IReferenceElement::Path::Modeltype}
{}

ReferenceElement::ReferenceElement(const IReference & reference) :
  vab::ElementMap{},
  ModelType{IReferenceElement::Path::Modeltype}
{
  this->setValue(reference);
}

ReferenceElement::ReferenceElement(const basyx::object & map) :
  vab::ElementMap{map}
{}

void ReferenceElement::setValue(const IReference & ref)
{
  Reference reference {ref};
  this->insertMapElement(IProperty::Path::Value, reference);
}

std::shared_ptr<IReference> ReferenceElement::getValue() const
{
  return std::make_shared<Reference>(this->map.getProperty(IProperty::Path::Value));
}

}
}
