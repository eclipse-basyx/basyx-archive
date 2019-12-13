/*
 * SubmodelElementCollection.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/submodelelement/SubmodelElementCollection.h>

namespace basyx {
namespace submodel {

SubmodelElementCollection::SubmodelElementCollection() :
  ordered {false},
  allowDuplicates {false}
{}

SubmodelElementCollection::SubmodelElementCollection(const basyx::specificCollection_t<ISubmodelElement>& value, const bool ordered, const bool allowDuplicates) :
  //todo if ordered -> order
  value {value},
  ordered {ordered},
  allowDuplicates {allowDuplicates}
{}

void SubmodelElementCollection::setValue(const basyx::specificCollection_t<ISubmodelElement> & value)
{
  //todo if ordered -> order
  this->value = value;
}

basyx::specificCollection_t<ISubmodelElement> SubmodelElementCollection::getValue() const
{
  return basyx::specificCollection_t<ISubmodelElement>();
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

void SubmodelElementCollection::setElements(const basyx::specificMap_t<ISubmodelElement> & value)
{
  this->elements = elements;
}

basyx::specificMap_t<ISubmodelElement> SubmodelElementCollection::getElements() const
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
