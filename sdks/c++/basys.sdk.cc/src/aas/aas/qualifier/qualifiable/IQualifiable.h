/*
 * IQualifiable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IQualifiable_H_
#define BASYX_METAMODEL_IQualifiable_H_


#include "IConstraint.h"

#include <vector>
#include <memory>

class IQualifiable
{
public:
	virtual ~IQualifiable() = default;

	virtual void setQualifier(const std::vector< std::shared_ptr<IConstraint>> & qualifiers) = 0;
	virtual std::vector< std::shared_ptr<IConstraint>> getQualifier() const = 0;
};

#endif

