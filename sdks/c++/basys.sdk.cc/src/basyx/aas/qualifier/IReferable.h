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

	virtual std::string getIdshort() const = 0;
	virtual void setIdshort(const std::string & idShort) = 0;

	virtual std::string getCategory() const = 0;
	virtual void setCategory(const std::string & category) = 0;

	virtual std::string getDescription() const = 0;
	virtual void setDescription(const std::string & description) = 0;

	virtual std::shared_ptr<IReference> getParent() const = 0;
	virtual void setParent(const std::shared_ptr<IReference> & obj) = 0;

};

#endif
