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
private:
//	Referable referable;
	map::AdministrativeInformation administrativeInformation;
public:
	virtual ~Identifiable() = default;

	// Constructors
	Identifiable(const std::string & idShort, const simple::Identifier & identifier);

	//// Member-access
	//inline const Referable & getReferable() const noexcept { return this->referable; };
	//inline Referable & getReferable() noexcept { return this->referable; };

	bool hasAdministrativeInformation() const noexcept;

	// Inherited via IIdentifiable
	const AdministrativeInformation & getAdministrativeInformation() const override;
	AdministrativeInformation & getAdministrativeInformation() override;

	virtual simple::Identifier getIdentification() const override;

  void setAdministrativeInformation(const AdministrativeInformation & administrativeInformation);
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_QUALIFIER_IDENTIFIABLE_H */
