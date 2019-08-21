/*
 * IOperationVariable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IOperationVariable_H_
#define BASYX_METAMODEL_IOperationVariable_H_


#include "submodelelement/ISubmodelElement.h"

#include <memory>

class IOperationVariable
{
public:
	virtual ~IOperationVariable() = default;

	virtual void setValue(std::shared_ptr<ISubmodelElement> value) = 0;
	virtual std::shared_ptr<ISubmodelElement> getValue() = 0;
};

#endif

