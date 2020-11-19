#include <BaSyx/submodel/map_v2/qualifier/Identifiable.h>

using namespace basyx::submodel;
using namespace basyx::submodel::map;
using namespace basyx::submodel::api;

constexpr char Identifiable::Path::IdType[];
constexpr char Identifiable::Path::Id[];
constexpr char Identifiable::Path::AdministrativeInformation[];
constexpr char Identifiable::Path::Identifier[];

Identifiable::Identifiable(const std::string & idShort, const simple::Identifier & identifier)
	: Referable(idShort)
	, vab::ElementMap()
{
	auto identifierMap = basyx::object::make_map();
	identifierMap.insertKey(Path::Id, identifier.getId());
	identifierMap.insertKey(Path::IdType, IdentifierType_::to_string(identifier.getIdType()));
	this->map.insertKey(Path::Identifier, identifierMap);
}

bool Identifiable::hasAdministrativeInformation() const noexcept
{
  return not this->map.getProperty(Path::AdministrativeInformation).IsNull();
};

simple::Identifier Identifiable::getIdentification() const
{
	auto identifierMap = this->map.getProperty(Path::Identifier);
	return simple::Identifier{
		IdentifierType_::from_string(identifierMap.getProperty(Path::IdType).Get<std::string&>()),
		identifierMap.getProperty(Path::Id).Get<std::string&>()
	};
}

const api::IAdministrativeInformation & Identifiable::getAdministrativeInformation() const
{
	return this->administrativeInformation;
}

api::IAdministrativeInformation & Identifiable::getAdministrativeInformation()
{
	return this->administrativeInformation;
}

void Identifiable::setAdministrativeInformation(const AdministrativeInformation &administrativeInformation)
{
  this->administrativeInformation = administrativeInformation;
  this->map.insertKey(Path::AdministrativeInformation, this->administrativeInformation.getMap());
}
