/*
 * IConceptDescription.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IConceptDescription_H_
#define BASYX_METAMODEL_IConceptDescription_H_

#include "IHasDataSpecification.h"
#include "IIdentifiable.h"

#include <string>
#include <vector>

class IConceptDescription : virtual IHasDataSpecification, IIdentifiable
{
public:
	

	virtual ~IConceptDescription() = default;

	virtual std::vector<std::string> getisCaseOf() = 0;
	virtual void setIscaseOf(std::vector<std::string> ref) = 0;
};

#endif
