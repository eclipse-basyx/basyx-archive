/*
 * ISubmodelElementCollection.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_ISubmodelElementCollection_H_
#define BASYX_METAMODEL_ISubmodelElementCollection_H_


#include "ISubmodelElement.h"
#include "types\BaSysTypes.h"

#include <hash_map>

class ISubmodelElementCollection
{
public:
	virtual ~ISubmodelElementCollection() = default;

	virtual void setValue(const basyx::objectCollection_t & value) = 0;
	virtual basyx::objectCollection_t getValue() const = 0;

	virtual void setOrdered(const bool & value) = 0;
	virtual bool isOrdered() const = 0;

	virtual void setAllowDuplicates(const bool & value)= 0;
	virtual bool isAllowDuplicates() const = 0;

	virtual void setElements(const std::hash_map<std::string, ISubmodelElement> & value) = 0;
	virtual std::hash_map<std::string, ISubmodelElement> getElements() const = 0;
};

#endif

