/*
 * IAccessControlPolicyPoints.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IAccessControlPolicyPoints_H_
#define BASYX_METAMODEL_IAccessControlPolicyPoints_H_


#include "types/BaSysTypes.h"

class IAccessControlPolicyPoints
{
public:
	virtual ~IAccessControlPolicyPoints() = default;

	virtual void setPolicyAdministrationPoint(const basyx::any & obj) = 0;
	virtual basyx::any getPolicyAdministrationPoint() const = 0;

	virtual void setPolicyDecisionPoint(const basyx::any & obj) = 0;
	virtual basyx::any getPolicyDecisionPoint() const = 0;

	virtual void setPolicyEnforcementPoint(const basyx::any & obj) = 0;
	virtual basyx::any getPolicyEnforcementPoint() const = 0;

	virtual void setPolicyInformationPoints(const basyx::any & obj) = 0;
	virtual basyx::any getPolicyInformationPoints() const = 0;

};

#endif
