/*
 * SubmodelElementCollection.cpp
 *
 *      Author: wendel
 */

#include "SubmodelElementCollection.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace submodelelement {

SubmodelElementCollection::SubmodelElementCollection() :
  ordered {false},
  allowDuplicates {false}
{}

SubmodelElementCollection::SubmodelElementCollection(const basyx::specificCollection_t<aas::submodelelement::ISubmodelElement>& value, const bool ordered, const bool allowDuplicates) :
  //todo if ordered -> order
  value {value},
  ordered {ordered},
  allowDuplicates {allowDuplicates}
{}

void SubmodelElementCollection::setValue(const basyx::specificCollection_t<aas::submodelelement::ISubmodelElement> & value)
{
  //todo if ordered -> order
  this->value = value;
}

basyx::specificCollection_t<aas::submodelelement::ISubmodelElement> SubmodelElementCollection::getValue() const
{
  return basyx::specificCollection_t<aas::submodelelement::ISubmodelElement>();
}

void SubmodelElementCollection::setOrdered(const bool & value)
{
  if ( value )
    this->orderElements();
  this->ordered = value;
}

bool SubmodelElementCollection::isOrdered() const
{
  return this->ordered;
}

void SubmodelElementCollection::setAllowDuplicates(const bool & value)
{
  this->allowDuplicates = value;
}

bool SubmodelElementCollection::isAllowDuplicates() const
{
  return this->allowDuplicates;
}

void SubmodelElementCollection::setElements(const basyx::specificMap_t<aas::submodelelement::ISubmodelElement> & value)
{
  this->elements = elements;
}

basyx::specificMap_t<aas::submodelelement::ISubmodelElement> SubmodelElementCollection::getElements() const
{
  return this->elements;
}

void SubmodelElementCollection::orderElements()
{
  //todo
  //order the list of elements
}

}
}
}
}
}
