#include <BaSyx/submodel/simple/qualifier/AdministrativeInformation.h>

namespace basyx {
namespace submodel {
namespace simple {

AdministrativeInformation::AdministrativeInformation()
{

};

AdministrativeInformation::AdministrativeInformation(const std::string & version, const std::string & revision)
{

};

void AdministrativeInformation::setVersion(const std::string & version)
{
	this->version = version;
}

void AdministrativeInformation::setRevision(const std::string & revision)
{
	this->revision = revision;
}

std::string AdministrativeInformation::getVersion() const
{
	return version;
}

std::string AdministrativeInformation::getRevision() const
{
	return revision;
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