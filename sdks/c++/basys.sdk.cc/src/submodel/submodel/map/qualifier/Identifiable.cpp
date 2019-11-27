/*
 * Identifiable.cpp
 *
 *      Author: wendel
 */

#include "Identifiable.h"

#include "AdministrativeInformation.h"
#include "submodel/map/identifier/Identifier.h"

namespace basyx {
namespace submodel {


Identifiable::Identifiable()
  //administration {new aas::qualifier::impl::AdministrativeInformation},
  //identification {new aas::identifier::impl::Identifier}
{
}

Identifiable::Identifiable(
	const std::string & version, 
	const std::string & revision, 
	const std::string & idShort, 
    const std::string & category, 
	const Description & description, 
	const std::string & idType, 
	const std::string & id) 
{
}

std::shared_ptr<IAdministrativeInformation> Identifiable::getAdministration() const
{
	return nullptr;
}

std::shared_ptr<IIdentifier> Identifiable::getIdentification() const
{
	return nullptr;
}

void Identifiable::setAdministration(const std::shared_ptr<IAdministrativeInformation> & administration)
{
}

void Identifiable::setIdentification(const std::shared_ptr<IIdentifier> & identification)
{
}

void Identifiable::setIdentification(const IIdentifier & identification)
{

}

}
}
