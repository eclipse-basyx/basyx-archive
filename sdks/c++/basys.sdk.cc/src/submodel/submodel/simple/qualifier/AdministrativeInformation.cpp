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

void AdministrativeInformation::addDataSpecification(const Reference & reference)
{
	this->hasDataSpecification.addDataSpecification(reference);
}

const std::vector<Reference> AdministrativeInformation::getDataSpecificationReference() const
{
	return this->hasDataSpecification.getDataSpecificationReference();
}

}
}
}