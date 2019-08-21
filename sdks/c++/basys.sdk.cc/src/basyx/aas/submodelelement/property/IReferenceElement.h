
/*
 * IReferenceElement.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IREFERENCE_H_
#define BASYX_METAMODEL_IREFERENCE_H_

#include "IReference.h"
#include "api/IProperty.h"

class IReferenceElement : virtual IProperty
{
public:
	

	virtual ~IReferenceElement() = default;

	virtual void setValue(IReference ref) = 0;
	virtual IReference getValue() = 0;
};

#endif
