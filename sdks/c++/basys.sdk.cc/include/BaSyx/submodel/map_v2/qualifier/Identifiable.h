#ifndef BASYX_SUBMODEL_MAP_V2_QUALIFIER_IDENTIFIABLE_H
#define BASYX_SUBMODEL_MAP_V2_QUALIFIER_IDENTIFIABLE_H

#include <BaSyx/submodel/api_v2/qualifier/IIdentifiable.h>
#include <BaSyx/submodel/map_v2/qualifier/Referable.h>
#include <BaSyx/submodel/simple/qualifier/AdministrativeInformation.h>

namespace basyx {
namespace submodel {
namespace map {

class Identifiable : 
	public virtual api::IIdentifiable,
	public Referable
{
private:
//	Referable referable;
	simple::AdministrativeInformation administrativeInformation;
public:
	virtual ~Identifiable() = default;

	// Constructors
	Identifiable(const std::string & idShort, const simple::Identifier & identifier);

	//// Member-access
	//inline const Referable & getReferable() const noexcept { return this->referable; };
	//inline Referable & getReferable() noexcept { return this->referable; };

	bool hasAdministrativeInformation() const noexcept;

	// Inherited via IIdentifiable
	virtual const simple::AdministrativeInformation & getAdministrativeInformation() const override;
	virtual simple::AdministrativeInformation & getAdministrativeInformation() override;

	virtual simple::Identifier getIdentification() const override;
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_QUALIFIER_IDENTIFIABLE_H */
