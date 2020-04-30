#include <BaSyx/submodel/simple/qualifier/Identifiable.h>

using namespace basyx::submodel::simple;
using namespace basyx::submodel::api;

Identifiable::Identifiable(const std::string & idShort, const Identifier & identifier)
	: referable(idShort)
	, identifier(identifier)
{
}

bool Identifiable::hasAdministrativeInformation() const noexcept
{ 
	return this->administrativeInformation.exists(); 
};


std::string const & Identifiable::getIdShort() const
{
	return this->referable.getIdShort();
}

const std::string * const Identifiable::getCategory() const
{
	return this->referable.getCategory();
}

void Identifiable::setCategory(const std::string & category)
{
	this->referable.setCategory(category);
}

LangStringSet & Identifiable::getDescription()
{
	return this->referable.getDescription();
}

const LangStringSet & Identifiable::getDescription() const
{
	return this->referable.getDescription();
}

const IReferable * const Identifiable::getParent() const
{
	return this->referable.getParent();
}

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
