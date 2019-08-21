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

	virtual void setPolicyAdministrationPoint(basyx::any obj) = 0;
	virtual basyx::any getPolicyAdministrationPoint() = 0;

	virtual void setPolicyDecisionPoint(basyx::any obj) = 0;
	virtual basyx::any getPolicyDecisionPoint() = 0;

	virtual void setPolicyEnforcementPoint(basyx::any obj) = 0;
	virtual basyx::any getPolicyEnforcementPoint() = 0;

	virtual void setPolicyInformationPoints(basyx::any obj) = 0;
	virtual basyx::any getPolicyInformationPoints() = 0;

};

#endif
