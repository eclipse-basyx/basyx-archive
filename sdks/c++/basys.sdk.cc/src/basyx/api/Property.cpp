/*
 * Property.cpp
 *
 *      Author: wendel
 */

#include "Property.h"

Property::Property(PropertyType type)
{
  this->type = type;
}

PropertyType Property::getPropertyType()
{
  return this->type;
}
	
void Property::setValue(basyx::any value)
{
  this->value = value;
}

basyx::any Property::getValue()
{
  return this->value;
}

void Property::setValueId(basyx::any id)
{
  this->id = id;
}

basyx::any Property::getValueId()
{
  return this->id;
}