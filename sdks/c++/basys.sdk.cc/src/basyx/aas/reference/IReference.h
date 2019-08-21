/*
 * IReference.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IReference_H_
#define BASYX_METAMODEL_IReference_H_


#include "IKey.h"

#include <vector>

class IReference
{
public:
	

	virtual ~IReference() = default;

	virtual std::vector<IKey> getKeys() = 0;
	virtual void setKeys(std::vector<IKey> keys) = 0;
};

#endif

