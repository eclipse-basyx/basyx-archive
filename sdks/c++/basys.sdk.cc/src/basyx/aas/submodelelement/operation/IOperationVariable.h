/*
 * IOperationVariable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IOperationVariable_H_
#define BASYX_METAMODEL_IOperationVariable_H_


#include "ISubmodelElement.h"

class IOperationVariable
{
public:
	IOperationVariable();
	virtual ~IOperationVariable() = 0;

	virtual void setValue(ISubmodelElement value) = 0;
	virtual ISubmodelElement getValue() = 0;
};

#endif

