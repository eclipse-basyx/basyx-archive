/*
 * IIdentifiable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IIdentifiable_H_
#define BASYX_METAMODEL_IIdentifiable_H_


#include "aas/identifier/IIdentifier.h"
#include "IAdministrativeInformation.h"

#include <string>

class IIdentifiable
{
public:
	virtual ~IIdentifiable() = default;

	virtual void setAdministration(const std::string & version, const std::string & revision) = 0;
	virtual std::shared_ptr<IAdministrativeInformation> getAdministration() const = 0;

	virtual void setIdentification(const std::string & idType, const std::string & id) = 0;
	virtual std::shared_ptr<IIdentifier> getIdentification() const = 0;
	
};

#endif

