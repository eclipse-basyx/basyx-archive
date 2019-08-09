/*
 * IFormula.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IFormula_H_
#define BASYX_METAMODEL_IFormula_H_


#include "IReference.h"

#include <vector>

class IFormula
{
public:
	IFormula();
	virtual ~IFormula() = 0;

	virtual void setDependsOn(std::vector<IReference> dependsOn) = 0;
	virtual  std::vector<IReference> getDependsOn() = 0;
};

#endif

