#ifndef BASYX_SUBMODEL_SIMPLE_QUALIFIER_IDENTIFIABLE_H
#define BASYX_SUBMODEL_SIMPLE_QUALIFIER_IDENTIFIABLE_H

#include <BaSyx/submodel/api_v2/qualifier/IIdentifiable.h>
#include <BaSyx/submodel/simple/qualifier/Referable.h>
#include <BaSyx/submodel/simple/qualifier/AdministrativeInformation.h>

namespace basyx {
namespace submodel {
namespace simple {

class Identifiable
    : public virtual api::IIdentifiable
    , public Referable
{
private:
	Identifier identifier;
	AdministrativeInformation administrativeInformation;
public:
	virtual ~Identifiable() = default;

	// Constructors
	Identifiable(const std::string & idShort, const Identifier & identifier);
	explicit Identifiable(const api::IIdentifiable & other);

	bool hasAdministrativeInformation() const noexcept override;

	// Inherited via IIdentifiable
	const AdministrativeInformation & getAdministrativeInformation() const override;
	AdministrativeInformation & getAdministrativeInformation() override;

	Identifier getIdentification() const override;

	void setAdministrativeInformation(const AdministrativeInformation & administrativeInformation);
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_QUALIFIER_IDENTIFIABLE_H */
