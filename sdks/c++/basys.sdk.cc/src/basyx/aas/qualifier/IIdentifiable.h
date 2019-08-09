/*
 * IIdentifiable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IIdentifiable_H_
#define BASYX_METAMODEL_IIdentifiable_H_


#include "IIdentifier.h"
#include "IAdministrativeInformation.h"

#include <string>

class IIdentifiable
{
public:
	IIdentifiable();
	virtual ~IIdentifiable() = 0;

	virtual IAdministrativeInformation getAdministration() = 0;

	virtual IIdentifier getIdentification() = 0;

	virtual void setAdministration(std::string version, std::string revision) = 0;

	virtual void setIdentification(std::string idType, std::string id) = 0;
};

#endif

