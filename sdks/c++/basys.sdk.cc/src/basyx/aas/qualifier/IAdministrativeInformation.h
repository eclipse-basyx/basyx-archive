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

	virtual void setVersion(const std::string & version) = 0;
	virtual std::string getVersion() const = 0;

	virtual void setRevision(const std::string & revision) = 0;
	virtual std::string getRevision() const = 0;
};

#endif

