/*
 * ISubmodelElement.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_ISubmodelElement_H_
#define BASYX_METAMODEL_ISubmodelElement_H_


#include "aas/submodelelement/IElement.h"
#include "aas/qualifier/IHasDataSpecification.h"
#include "aas/qualifier/IReferable.h"
#include "aas/qualifier/qualifiable/IQualifiable.h"
#include "aas/qualifier/IHasSemantics.h"
#include "aas/qualifier/haskind/IHasKind.h"

class ISubmodelElement : public IElement, IHasDataSpecification, IReferable, IQualifiable, IHasSemantics, IHasKind
{
public:
	virtual ~ISubmodelElement() = default;
};

#endif

