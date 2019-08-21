/*
 * IView.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IView_H_
#define BASYX_METAMODEL_IView_H_


#include "qualifier/IHasSemantics.h"
#include "IHasDataSpecification.h"
#include "IReferable.h"

#include <vector>

class IView : virtual IHasSemantics, IHasDataSpecification, IReferable
{
public:
	

	virtual ~IView() = default;

	virtual void setContainedElement(std::vector<IReference> references) = 0;
	virtual std::vector<IReference> getContainedElement() = 0;
};

#endif

