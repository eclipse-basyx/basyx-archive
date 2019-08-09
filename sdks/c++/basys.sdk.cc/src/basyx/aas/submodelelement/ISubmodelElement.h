/*
 * ISubmodelElement.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_ISubmodelElement_H_
#define BASYX_METAMODEL_ISubmodelElement_H_


#include "api/IElement.h"
#include "IHasDataSpecification.h"
#include "IReferable.h"
#include "IQualifiable.h"
#include "IHasSemantics.h"
#include "IHasKind.h"

class ISubmodelElement : public IElement, IHasDataSpecification, IReferable, IQualifiable, IHasSemantics, IHasKind
{
public:
	ISubmodelElement();
	virtual ~ISubmodelElement() = 0;
};

#endif

