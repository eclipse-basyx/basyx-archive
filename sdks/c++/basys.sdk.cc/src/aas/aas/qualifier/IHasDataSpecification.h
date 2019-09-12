/*
 * IHasDataSpecification.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IHasDataSpecification_H_
#define BASYX_METAMODEL_IHasDataSpecification_H_


#include "aas/reference/IReference.h"

#include <memory>
#include <vector>

class IHasDataSpecification
{
public:
  virtual ~IHasDataSpecification() = default;
  
	virtual std::vector<std::shared_ptr<IReference>> getDataSpecificationReferences() const = 0;
	virtual void setDataSpecificationReferences(const std::vector< std::shared_ptr<IReference>> & ref) = 0;
};

#endif
