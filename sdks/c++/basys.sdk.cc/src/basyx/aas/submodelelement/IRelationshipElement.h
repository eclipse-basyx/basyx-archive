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
	

	virtual ~IRelationshipElement() = default;

	virtual void setFirst(IReference first) = 0;
	virtual IReference getFirst() = 0;

	virtual void setSecond(IReference second) = 0;
	virtual IReference getSecond() = 0;
};

#endif

