/*
 * IProperty.h
 *
 *  Created on: 29.04.2018
 *      Author: kuhn, wendel
 */
#ifndef API_IPROPERTY_H_
#define API_IPROPERTY_H_

#include "IElement.h"
#include "types/BaSysTypes.h"

enum PropertyType
{
	Single, Collection, Map, Container
};


/* *********************************************************************************
 * Property interface
 * *********************************************************************************/
class IProperty : public IElement 
{

public:
	virtual PropertyType getPropertyType() = 0;
	
	virtual void setValue(basyx::any obj) = 0;
	virtual basyx::any getValue() = 0;

	virtual void setValueId(basyx::any obj) = 0;
	virtual basyx::any getValueId() = 0;

};


#endif /* API_IPROPERTY_H_ */
