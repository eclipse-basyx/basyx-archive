/*
 * IFormula.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IFormula_H_
#define BASYX_METAMODEL_IFormula_H_


#include "reference/IReference.h"

#include <vector>

class IFormula
{
public:
	virtual ~IFormula() = default;

	virtual void setDependsOn(const std::vector<IReference> & dependsOn) = 0;
	virtual  std::vector<IReference> getDependsOn() const = 0;
};

#endif

