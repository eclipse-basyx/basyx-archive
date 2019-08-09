/*
 * IRelationshipElement.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IRelationshipElement_H_
#define BASYX_METAMODEL_IRelationshipElement_H_


#include "IReference.h"

class IRelationshipElement
{
public:
	IRelationshipElement();
	virtual ~IRelationshipElement() = 0;

	virtual void setFirst(IReference first) = 0;
	virtual IReference getFirst() = 0;

	virtual void setSecond(IReference second) = 0;
	virtual IReference getSecond() = 0;
};

#endif

