/*
 * IHasDataSpecification.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IHasDataSpecification_H_
#define BASYX_METAMODEL_IHasDataSpecification_H_


#include "reference/IReference.h"

#include <vector>

class IHasDataSpecification
{
public:
  virtual ~IHasDataSpecification() = default;
  
	std::vector<IReference> getDataSpecificationReferences();
	void setDataSpecificationReferences(std::vector<IReference> ref);
};

#endif
