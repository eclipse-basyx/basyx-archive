/*
 * IReferable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IReferable_H_
#define BASYX_METAMODEL_IReferable_H_


#include "reference/IReference.h"

#include <string>
#include <memory>

class IReferable
{
public:
	

	virtual ~IReferable() = default;

	virtual std::string getIdshort() = 0;
	virtual void setIdshort(std::string idShort) = 0;

	virtual std::string getCategory() = 0;
	virtual void setCategory(std::string category) = 0;

	virtual std::string getDescription() = 0;
	virtual void setDescription(std::string description) = 0;

	virtual std::shared_ptr<IReference> getParent() = 0;
	virtual void setParent(std::shared_ptr<IReference> obj) = 0;

};

#endif
