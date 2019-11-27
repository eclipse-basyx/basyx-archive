/*
 * Referable.cpp
 *
 *      Author: wendel
 */

#include "Referable.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {

using namespace aas;

Referable::Referable() :
  description { "", "" }
{}

Referable::Referable(const std::string & idShort, const std::string & category, const aas::qualifier::impl::Description & description) :
  idShort { idShort },
  category { category },
  description { description }
{}

std::string Referable::getIdShort() const
{
  return this->idShort;
}

std::string Referable::getCategory() const
{
  return this->category;
}

aas::qualifier::impl::Description Referable::getDescription() const
{
  return this->description;
}

std::shared_ptr<reference::IReference> Referable::getParent() const
{
  return this->parentReference;
}

void Referable::setIdShort(const std::string & shortID)
{
  this->idShort = shortID;
}

void Referable::setCategory(const std::string & category)
{
  this->category = category;
}

void Referable::setDescription(const aas::qualifier::impl::Description & description)
{
  this->description = description;
}

void Referable::setParent(const std::shared_ptr<aas::reference::IReference>& parentReference)
{
  this->parentReference = parentReference;
}

}
}
}
}
}
