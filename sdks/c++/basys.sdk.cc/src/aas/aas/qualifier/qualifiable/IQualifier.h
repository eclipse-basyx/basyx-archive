/*
 * IQualifier.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IQualifier_H_
#define BASYX_METAMODEL_IQualifier_H_


#include "aas/reference/IReference.h"

#include "basyx/types.h"

#include <string>

class IQualifier
{
public:
	virtual ~IQualifier() = default;

	virtual void setQualifierType(const std::string & obj) = 0;
	virtual std::string getQualifierType() const = 0;

	virtual void setQualifierValue(const basyx::any & obj) = 0;
	virtual basyx::any getQualifierValue() const = 0;

	virtual void setQualifierValueId(const IReference & obj) = 0;
	virtual IReference getQualifierValueId() const = 0;
};

#endif

