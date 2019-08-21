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

	virtual void setValue(objectCollection_t value) = 0;
	virtual objectCollection_t getValue() = 0;

	virtual void setOrdered(bool value) = 0;
	virtual bool isOrdered() = 0;

	virtual void setAllowDuplicates(bool value) = 0;
	virtual bool isAllowDuplicates() = 0;

	virtual void setElements(std::hash_map<std::string, ISubmodelElement> value) = 0;
	virtual std::hash_map<std::string, ISubmodelElement> getElements() = 0;
};

#endif

