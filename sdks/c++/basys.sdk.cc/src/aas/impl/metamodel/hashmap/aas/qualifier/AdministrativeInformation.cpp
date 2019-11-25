/*
 * AdministrativeInformation.cpp
 *
 *      Author: wendel
 */

#include "AdministrativeInformation.h"

namespace basyx {
namespace aas {
namespace qualifier {
namespace impl {

AdministrativeInformation::AdministrativeInformation() :
  data_specification_references()
{}

AdministrativeInformation::AdministrativeInformation(const std::string & version, const std::string & revision) :
  data_specification_references(),
  version {version},
  revision {revision}
{}

AdministrativeInformation::AdministrativeInformation(basyx::object::object_map_t & map)
{
  this->revision = map.at(qualifier::internalAdministrationPaths::REVISION).GetStringContent();
  this->version = map.at(qualifier::internalAdministrationPaths::VERSION).GetStringContent();
  this->data_specification_references = map.at(HasDataSpecificationPaths::HASDATASPECIFICATION).Get<basyx::specificCollection_t<reference::IReference>>();
}

void AdministrativeInformation::setDataSpecificationReferences(const basyx::specificCollection_t<reference::IReference>& data_specification_references)
{
  this->data_specification_references = data_specification_references;
}

void AdministrativeInformation::setVersion(const std::string & version)
{
  this->version = version;
}

void AdministrativeInformation::setRevision(const std::string & revision)
{
  this->revision = revision;
}

basyx::specificCollection_t<reference::IReference> AdministrativeInformation::getDataSpecificationReferences() const
{
  return this->data_specification_references;
}

std::string AdministrativeInformation::getVersion() const
{
  return this->version;
}

std::string AdministrativeInformation::getRevision() const
{
  return this->revision;
}

}
}
}
}