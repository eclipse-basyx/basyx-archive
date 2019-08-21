/*
 * IConceptDictionary.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IConceptDictionary_H_
#define BASYX_METAMODEL_IConceptDictionary_H_


#include "IReferable.h"

#include <string>
#include <vector>

class IConceptDictionary : virtual IReferable
{
public:
	

	virtual ~IConceptDictionary() = default;

	virtual std::vector<std::string> getConceptDescription() = 0;
	virtual void setConceptDescription(std::vector<std::string> ref) = 0;
};

#endif

