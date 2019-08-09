/*
 * IHasDataSpecification.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IHasDataSpecification_H_
#define BASYX_METAMODEL_IHasDataSpecification_H_


#include "IReference.h"

#include <vector>

class IHasDataSpecification
{
public:
  IHasDataSpecification();
  virtual ~IHasDataSpecification() = 0;
  
	std::vector<IReference> getDataSpecificationReferences();
	void setDataSpecificationReferences(std::vector<IReference> ref);
};

#endif
