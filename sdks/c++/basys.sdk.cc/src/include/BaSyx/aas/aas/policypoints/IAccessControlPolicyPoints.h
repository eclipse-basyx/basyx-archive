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

	virtual void setPolicyAdministrationPoint(const basyx::object & obj) = 0;
	virtual basyx::object getPolicyAdministrationPoint() const = 0;

	virtual void setPolicyDecisionPoint(const basyx::object & obj) = 0;
	virtual basyx::object getPolicyDecisionPoint() const = 0;

	virtual void setPolicyEnforcementPoint(const basyx::object & obj) = 0;
	virtual basyx::object getPolicyEnforcementPoint() const = 0;

	virtual void setPolicyInformationPoints(const basyx::object & obj) = 0;
	virtual basyx::object getPolicyInformationPoints() const = 0;

};

#endif
