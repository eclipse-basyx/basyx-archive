/*
 * IHasKind.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IHasKind_H_
#define BASYX_METAMODEL_IHasKind_H_


#include <string>

class IHasKind
{
public:
	

	virtual ~IHasKind() = default;

	virtual std::string getHasKindReference() = 0;
	virtual void setHasKindReference(std::string kind) = 0;
};

#endif

