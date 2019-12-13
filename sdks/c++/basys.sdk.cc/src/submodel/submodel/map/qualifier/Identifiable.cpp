/*
 * Identifiable.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/qualifier/Identifiable.h>

#include <BaSyx/submodel/map/qualifier/AdministrativeInformation.h>
#include <BaSyx/submodel/map/identifier/Identifier.h>

namespace basyx {
namespace submodel {


Identifiable::Identifiable() :
  vab::ElementMap{}
{
  this->setIdentification(Identifier {"", ""});
  this->setAdministration(AdministrativeInformation {"", ""});
}

Identifiable::Identifiable(
	const std::string & version, 
	const std::string & revision, 
	const std::string & idShort, 
  const std::string & category, 
	const Description & description, 
	const std::string & idType, 
	const std::string & id) 
    : Referable{idShort, category, description}
{
  this->setIdentification(Identifier{id, idType});
  this->setAdministration(AdministrativeInformation {version, revision});
}

std::shared_ptr<IAdministrativeInformation> Identifiable::getAdministration() const
{
	return std::make_shared<AdministrativeInformation>(this->map.getProperty(IIdentifiable::Path::Administration));
}

std::shared_ptr<IIdentifier> Identifiable::getIdentification() const
{
  return std::make_shared<Identifier>(this->map.getProperty(IIdentifiable::Path::Identification));
}

void Identifiable::setAdministration(const std::shared_ptr<IAdministrativeInformation> & administration)
{
  this->insertMapElement(IIdentifiable::Path::Administration, AdministrativeInformation(administration));
}

void Identifiable::setAdministration(const IAdministrativeInformation & administration)
{
  this->insertMapElement(IIdentifiable::Path::Administration, AdministrativeInformation(administration));
}

void Identifiable::setIdentification(const std::shared_ptr<IIdentifier> & identification)
{
  this->insertMapElement(IIdentifiable::Path::Identification, Identifier(identification));
}

void Identifiable::setIdentification(const IIdentifier & identification)
{
  this->insertMapElement(IIdentifiable::Path::Identification, Identifier(identification));
}

}
}
