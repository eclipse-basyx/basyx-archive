/*
 * IQualifiable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IQualifiable_H_
#define BASYX_METAMODEL_IQualifiable_H_


#include "IConstraint.h"

#include <vector>

class IQualifiable
{
public:
	

	virtual ~IQualifiable() = default;

	virtual void setQualifier(std::vector<IConstraint> qualifiers) = 0;
	virtual std::vector<IConstraint> getQualifier() = 0;
};

#endif

