/*
 * Property.h
 *
 *      Author: wendel
 */
#ifndef API_PROPERTY_H_
#define API_PROPERTY_H_

#include "IProperty.h"

class Property : public IProperty 
{

public:
  Property(PropertyType type);
	virtual PropertyType getPropertyType();
	
	virtual void setValue(basyx::any value);
	virtual basyx::any getValue();

	virtual void setValueId(basyx::any id);
	virtual basyx::any getValueId();

private:
  PropertyType type;
  basyx::any value, id;
};


#endif 
