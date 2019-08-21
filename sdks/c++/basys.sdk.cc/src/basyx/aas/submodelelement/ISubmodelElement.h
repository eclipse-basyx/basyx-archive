/*
 * ISubmodelElement.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_ISubmodelElement_H_
#define BASYX_METAMODEL_ISubmodelElement_H_


#include "api/IElement.h"
#include "qualifier/IHasDataSpecification.h"
#include "qualifier/IReferable.h"
#include "qualifier/qualifiable/IQualifiable.h"
#include "qualifier/IHasSemantics.h"
#include "qualifier/haskind/IHasKind.h"

class ISubmodelElement : public IElement, IHasDataSpecification, IReferable, IQualifiable, IHasSemantics, IHasKind
{
public:
	

	virtual ~ISubmodelElement() = default;
};

#endif

