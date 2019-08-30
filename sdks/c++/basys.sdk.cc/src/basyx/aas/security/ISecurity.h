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

	virtual basyx::any getAccessControlPolicyPoints() const = 0;
	virtual void setAccessControlPolicyPoints(const basyx::any & obj) = 0;

	virtual basyx::any getTrustAnchor() const = 0;
	virtual void setTrustAnchor(const basyx::any & obj) = 0;
};

#endif

