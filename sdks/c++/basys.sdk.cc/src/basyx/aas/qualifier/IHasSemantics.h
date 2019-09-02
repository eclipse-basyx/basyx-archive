/*
 * IHasSemantics.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IHasSemantics_H_
#define BASYX_METAMODEL_IHasSemantics_H_

#include <memory>
#include "aas/reference/IReference.h"

class IHasSemantics
{
public:
	virtual ~IHasSemantics() = default;

	virtual std::shared_ptr<std::shared_ptr<IReference>> getSemanticId() const = 0;
	virtual void setSemanticID(const std::shared_ptr<std::shared_ptr<IReference>> & ref) = 0;
};

#endif

