/*
 * SubmodelElement.cpp
 *
 *      Author: wendel
 */

#include "SubmodelElement.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace submodelelement {

using namespace aas;

SubmodelElement::SubmodelElement() :
  description {"", ""}
{}

//SubmodelElement::SubmodelElement(const basyx::objectMap_t & submodelElementMap)
//{
//
//}

basyx::specificCollection_t<reference::IReference> SubmodelElement::getDataSpecificationReferences() const
{
  return this->dataSpecificationReferences;
}

std::string SubmodelElement::getIdShort() const
{
  return this->idShort;
}

std::string SubmodelElement::getCategory() const
{
  return this->category;
}

aas::qualifier::impl::Description SubmodelElement::getDescription() const
{
  return this->description;
}

std::shared_ptr<reference::IReference> SubmodelElement::getParent() const
{
  return this->parent;
}

basyx::specificCollection_t<aas::qualifier::qualifiable::IConstraint> SubmodelElement::getQualifier() const
{
  return this->qualifier;
}

std::shared_ptr<reference::IReference> SubmodelElement::getSemanticId() const
{
  return this->semanticId;
}

qualifier::haskind::Kind SubmodelElement::getHasKindReference() const
{
  return this->hasKindReference;
}

void SubmodelElement::setDataSpecificationReferences(const basyx::specificCollection_t<aas::reference::IReference> & dataSpecificationReferences)
{
  this->dataSpecificationReferences = dataSpecificationReferences;
}

void SubmodelElement::setIdShort(const std::string & idShort)
{
  this->idShort = idShort;
}

void SubmodelElement::setCategory(const std::string & category)
{
  this->category = category;
}

void SubmodelElement::setDescription(const aas::qualifier::impl::Description & description)
{
  this->description = description;
}

void SubmodelElement::setParent(const std::shared_ptr<aas::reference::IReference> & parent)
{
  this->parent = parent;
}

void SubmodelElement::setQualifier(const basyx::specificCollection_t<aas::qualifier::qualifiable::IConstraint> & qualifier)
{
  this->qualifier = qualifier;
}

void SubmodelElement::setSemanticId(const std::shared_ptr<aas::reference::IReference> & semanticId)
{
  this->semanticId = semanticId;
}

void SubmodelElement::setHasKindReference(const qualifier::haskind::Kind & hasKindReference)
{
  this->hasKindReference = hasKindReference;
}

}
}
}
}
}
