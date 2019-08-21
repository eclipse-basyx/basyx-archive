/*
 * IAdministrativeInformation.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IAdministrativeInformation_H_
#define BASYX_METAMODEL_IAdministrativeInformation_H_


#include "IHasDataSpecification.h"

#include <string>

class IAdministrativeInformation : public IHasDataSpecification
{
public:
	

	virtual ~IAdministrativeInformation() = default;

	virtual void setVersion(std::string version) = 0;
	virtual std::string getVersion() = 0;

	virtual void setRevision(std::string revision) = 0;
	virtual std::string getRevision() = 0;
};

#endif

