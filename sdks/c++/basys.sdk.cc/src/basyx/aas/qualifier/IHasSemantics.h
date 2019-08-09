/*
 * IHasSemantics.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IHasSemantics_H_
#define BASYX_METAMODEL_IHasSemantics_H_


#include "IReference.h"

class IHasSemantics
{
public:
	IHasSemantics();
	virtual ~IHasSemantics() = 0;

	virtual IReference getSemanticId() = 0;
	virtual void setSemanticID(IReference ref) = 0;
};

#endif

