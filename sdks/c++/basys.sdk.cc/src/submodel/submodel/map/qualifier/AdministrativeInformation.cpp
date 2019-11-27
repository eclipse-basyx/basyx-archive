#include "AdministrativeInformation.h"
/*
 * AdministrativeInformation.cpp
 *
 *      Author: wendel
 */

#include "AdministrativeInformation.h"

namespace basyx {
namespace submodel {

AdministrativeInformation::AdministrativeInformation()
	: HasDataSpecification{}
{
	this->map.insertKey(IAdministrativeInformation::Path::Version, "");
	this->map.insertKey(IAdministrativeInformation::Path::Revision, "");
}

AdministrativeInformation::AdministrativeInformation(const std::string & version, const std::string & revision)
	: HasDataSpecification{}
{
	this->map.insertKey(IAdministrativeInformation::Path::Version, version);
	this->map.insertKey(IAdministrativeInformation::Path::Revision, revision);
}

AdministrativeInformation::AdministrativeInformation(basyx::object obj)
	: HasDataSpecification{ obj }
{
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

basyx::specificCollection_t<IReference> AdministrativeInformation::getDataSpecificationReferences() const
{
	return HasDataSpecification::getDataSpecificationReferences();
}

}
}