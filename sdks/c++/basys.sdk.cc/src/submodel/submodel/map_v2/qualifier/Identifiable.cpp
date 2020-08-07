#include <BaSyx/submodel/map_v2/qualifier/Identifiable.h>

using namespace basyx::submodel;
using namespace basyx::submodel::map;
using namespace basyx::submodel::api;

struct IdentifierPath {
	static constexpr char IdType[] = "idType";
	static constexpr char Id[] = "id";
  static constexpr char AdministrativeInformation[] = "AdministrativeInformation";
};

constexpr char IdentifierPath::IdType[];
constexpr char IdentifierPath::Id[];
constexpr char IdentifierPath::AdministrativeInformation[];

Identifiable::Identifiable(const std::string & idShort, const simple::Identifier & identifier)
	: Referable(idShort)
	, vab::ElementMap()
{
	auto identifierMap = basyx::object::make_map();
	identifierMap.insertKey(IdentifierPath::Id, identifier.getId());
	identifierMap.insertKey(IdentifierPath::IdType, IdentifierType_::to_string(identifier.getIdType()));
	this->map.insertKey("identifier", identifierMap);
}

bool Identifiable::hasAdministrativeInformation() const noexcept
{
  return not this->map.getProperty(IdentifierPath::AdministrativeInformation).IsNull();
};

simple::Identifier Identifiable::getIdentification() const
{
	auto identifierMap = this->map.getProperty("identifier");
	return simple::Identifier{
		IdentifierType_::from_string(identifierMap.getProperty(IdentifierPath::IdType).Get<std::string&>()),
		identifierMap.getProperty(IdentifierPath::Id).Get<std::string&>()
	};
}

const AdministrativeInformation & Identifiable::getAdministrativeInformation() const
{
	return this->administrativeInformation;
}

AdministrativeInformation & Identifiable::getAdministrativeInformation()
{
	return this->administrativeInformation;
}

void Identifiable::setAdministrativeInformation(const AdministrativeInformation &administrativeInformation)
{
  this->administrativeInformation = administrativeInformation;
  this->map.insertKey(IdentifierPath::AdministrativeInformation, this->administrativeInformation.getMap());
}
