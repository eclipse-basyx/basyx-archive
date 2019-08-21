/*
 * ISecurity.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_ISecurity_H_
#define BASYX_METAMODEL_ISecurity_H_


#include "types/BaSysTypes.h"

class ISecurity
{
public:
	

	virtual ~ISecurity() = default;

	virtual basyx::any getAccessControlPolicyPoints() = 0;
	virtual void setAccessControlPolicyPoints(basyx::any obj) = 0;

	virtual basyx::any getTrustAnchor() = 0;
	virtual void setTrustAnchor(basyx::any obj) = 0;
};

#endif

