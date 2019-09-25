/*
 * IQualifiable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IQualifiable_H_
#define BASYX_METAMODEL_IQualifiable_H_


#include "IConstraint.h"
#include "basyx/types.h"

#include <vector>
#include <memory>

class IQualifiable
{
public:
	virtual ~IQualifiable() = default;

	//virtual void setQualifier(const basyx::objectCollection_t & qualifiers) = 0;
	virtual basyx::objectCollection_t getQualifier() const = 0;
};

#endif

