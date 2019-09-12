/*
 * IHasDataSpecification.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IHasDataSpecification_H_
#define BASYX_METAMODEL_IHasDataSpecification_H_


#include "aas/reference/IReference.h"

#include <vector>

class IHasDataSpecification
{
public:
  virtual ~IHasDataSpecification() = default;
  
	virtual std::vector<IReference> getDataSpecificationReferences() const = 0;
	virtual void setDataSpecificationReferences(const std::vector<IReference> & ref) = 0;
};

#endif
