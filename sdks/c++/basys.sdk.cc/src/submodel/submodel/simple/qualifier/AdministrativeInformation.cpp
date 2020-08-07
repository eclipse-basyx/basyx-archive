#include <BaSyx/submodel/simple/qualifier/AdministrativeInformation.h>

namespace basyx {
namespace submodel {
namespace simple {

AdministrativeInformation::AdministrativeInformation()
{};

AdministrativeInformation::AdministrativeInformation(const std::string & version, const std::string & revision)
  : version{version}
  , revision{revision}
{};

AdministrativeInformation::AdministrativeInformation(const api::IAdministrativeInformation &other)
    : revision{*other.getRevision()}
    , version{*other.getVersion()}
{}

void AdministrativeInformation::setVersion(const std::string & version)
{
	this->version = version;
}

void AdministrativeInformation::setRevision(const std::string & revision)
{
	this->revision = revision;
}

const std::string * const AdministrativeInformation::getVersion() const
{
  if (this->version.empty())
    return nullptr;

  return &this->version;
}

const std::string * const AdministrativeInformation::getRevision() const
{
  if (this->revision.empty())
    return nullptr;

  return &this->revision;
}

}
}
}