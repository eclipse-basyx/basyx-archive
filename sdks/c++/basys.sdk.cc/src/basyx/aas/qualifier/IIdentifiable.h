/*
 * IIdentifiable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IIdentifiable_H_
#define BASYX_METAMODEL_IIdentifiable_H_


#include "identifier/IIdentifier.h"
#include "IAdministrativeInformation.h"

#include <string>

class IIdentifiable
{
public:
	virtual ~IIdentifiable() = default;

	virtual void setAdministration(std::string version, std::string revision) = 0;
	virtual std::shared_ptr<IAdministrativeInformation> getAdministration() = 0;

	virtual void setIdentification(std::string idType, std::string id) = 0;
	virtual std::shared_ptr<IIdentifier> getIdentification() = 0;
	
};

#endif

