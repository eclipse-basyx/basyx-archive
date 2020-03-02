/*
 * AdministrativeInformation.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/qualifier/AdministrativeInformation.h>

namespace basyx {
namespace submodel {

AdministrativeInformation::AdministrativeInformation()
	: HasDataSpecification{}
  , vab::ElementMap {}
{
	this->map.insertKey(IAdministrativeInformation::Path::Version, "");
	this->map.insertKey(IAdministrativeInformation::Path::Revision, "");
}

AdministrativeInformation::AdministrativeInformation(const std::string & version, const std::string & revision)
	: HasDataSpecification{}
  , vab::ElementMap{}
{
	this->map.insertKey(IAdministrativeInformation::Path::Version, version);
	this->map.insertKey(IAdministrativeInformation::Path::Revision, revision);
}

AdministrativeInformation::AdministrativeInformation(basyx::object obj)
	: vab::ElementMap{obj}
{
}

AdministrativeInformation::AdministrativeInformation(const IAdministrativeInformation & other)
  : vab::ElementMap {}
{
  this->setDataSpecificationReferences(other.getDataSpecificationReferences());
  this->setRevision(other.getRevision());
  this->setVersion(other.getVersion());
}

void AdministrativeInformation::setVersion(const std::string & version)
{
	this->map.insertKey(IAdministrativeInformation::Path::Version, version, true);
}

void AdministrativeInformation::setRevision(const std::string & revision)
{
	this->map.insertKey(IAdministrativeInformation::Path::Revision, revision, true);
}

std::string AdministrativeInformation::getVersion() const
{
	return this->map.getProperty(IAdministrativeInformation::Path::Version).GetStringContent();
}

std::string AdministrativeInformation::getRevision() const
{
	return this->map.getProperty(IAdministrativeInformation::Path::Revision).GetStringContent();
}

}
}
