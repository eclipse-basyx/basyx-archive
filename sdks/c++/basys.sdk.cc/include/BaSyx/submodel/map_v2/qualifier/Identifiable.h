#ifndef BASYX_SUBMODEL_MAP_V2_QUALIFIER_IDENTIFIABLE_H
#define BASYX_SUBMODEL_MAP_V2_QUALIFIER_IDENTIFIABLE_H

#include <BaSyx/submodel/api_v2/qualifier/IIdentifiable.h>
#include <BaSyx/submodel/map_v2/qualifier/Referable.h>
#include <BaSyx/submodel/map_v2/qualifier/AdministrativeInformation.h>

namespace basyx {
namespace submodel {
namespace map {

class Identifiable : 
	public virtual api::IIdentifiable,
	public Referable
{
public:
  struct Path {
    static constexpr char IdType[] = "idType";
    static constexpr char Id[] = "id";
    static constexpr char AdministrativeInformation[] = "administrativeInformation";
    static constexpr char Identifier[] = "Identification";
  };
private:
	map::AdministrativeInformation administrativeInformation;
public:
	virtual ~Identifiable() = default;

	// Constructors
	Identifiable(const std::string & idShort, const simple::Identifier & identifier);

	bool hasAdministrativeInformation() const noexcept override;

	// Inherited via IIdentifiable
	const api::IAdministrativeInformation & getAdministrativeInformation() const override;
	api::IAdministrativeInformation & getAdministrativeInformation() override;

	virtual simple::Identifier getIdentification() const override;

    void setAdministrativeInformation(const AdministrativeInformation & administrativeInformation);
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_QUALIFIER_IDENTIFIABLE_H */
