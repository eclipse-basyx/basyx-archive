/*
 * IRelationshipElement.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IRelationshipElement_H_
#define BASYX_METAMODEL_IRelationshipElement_H_


#include "aas/reference/IReference.h"

class IRelationshipElement
{
public:
	virtual ~IRelationshipElement() = default;

	virtual void setFirst(const IReference & first) = 0;
	virtual IReference getFirst() const = 0;

	virtual void setSecond(const IReference & second)= 0;
	virtual IReference getSecond() const = 0;
};

#endif

