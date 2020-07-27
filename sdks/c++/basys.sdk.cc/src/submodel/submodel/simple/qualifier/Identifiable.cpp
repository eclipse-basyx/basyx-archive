#include <BaSyx/submodel/simple/qualifier/Identifiable.h>

using namespace basyx::submodel::simple;
using namespace basyx::submodel::api;

Identifiable::Identifiable(const std::string & idShort, const Identifier & identifier)
	: Referable(idShort)
	, identifier(identifier)
{}

Identifiable::Identifiable(const IIdentifiable &other)
  : Referable(other)
  , identifier(other.getIdentification().getIdType(), other.getIdentification().getId())
  , administrativeInformation(other.getAdministrativeInformation())
{

}

bool Identifiable::hasAdministrativeInformation() const noexcept
{ 
	return this->administrativeInformation.exists(); 
};

const AdministrativeInformation & Identifiable::getAdministrativeInformation() const
{
	return this->administrativeInformation;
}

AdministrativeInformation & Identifiable::getAdministrativeInformation()
{
	return this->administrativeInformation;
}

Identifier Identifiable::getIdentification() const
{
	return this->identifier;
}

void Identifiable::setAdministrativeInformation(const AdministrativeInformation &administrativeInformation)
{
  this->administrativeInformation = administrativeInformation;
}
