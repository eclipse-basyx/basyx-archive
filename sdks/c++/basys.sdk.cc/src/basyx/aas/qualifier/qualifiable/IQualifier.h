/*
 * IQualifier.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IQualifier_H_
#define BASYX_METAMODEL_IQualifier_H_


#include "IReference.h"

#include "types/BaSysTypes.h"

#include <string>

class IQualifier
{
public:
	

	virtual ~IQualifier() = default;

	virtual void setQualifierType(std::string obj) = 0;
	virtual std::string getQualifierType() = 0;

	virtual void setQualifierValue(basyx::any obj) = 0;
	virtual basyx::any getQualifierValue() = 0;

	virtual void setQualifierValueId(IReference obj) = 0;
	virtual IReference getQualifierValueId() = 0;
};

#endif

