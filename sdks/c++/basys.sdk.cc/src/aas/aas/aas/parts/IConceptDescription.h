/*
 * IConceptDescription.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IConceptDescription_H_
#define BASYX_METAMODEL_IConceptDescription_H_

#include "qualifier/IHasDataSpecification.h"
#include "qualifier/IIdentifiable.h"

#include <string>
#include <vector>

class IConceptDescription : virtual IHasDataSpecification, IIdentifiable
{
public:
	

	virtual ~IConceptDescription() = default;

	virtual std::vector<std::string> getisCaseOf() const = 0;
	virtual void setIscaseOf(const std::vector<std::string> & ref) = 0;
};

#endif
