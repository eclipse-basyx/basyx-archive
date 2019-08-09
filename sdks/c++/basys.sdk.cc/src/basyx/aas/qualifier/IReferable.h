/*
 * IReferable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IReferable_H_
#define BASYX_METAMODEL_IReferable_H_


#include "IReference.h"

#include <string>

class IReferable
{
public:
	IReferable();
	virtual ~IReferable() = 0;

	virtual std::string getIdshort() = 0;
	virtual void setIdshort(std::string idShort) = 0;

	virtual std::string getCategory() = 0;
	virtual void setCategory(std::string category) = 0;

	virtual std::string getDescription() = 0;
	virtual void setDescription(std::string description) = 0;

	virtual IReference getParent() = 0;
	virtual void setParent(IReference obj) = 0;

};

#endif
