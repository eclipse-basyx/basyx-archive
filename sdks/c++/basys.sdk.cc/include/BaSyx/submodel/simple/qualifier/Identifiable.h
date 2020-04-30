#ifndef BASYX_SUBMODEL_SIMPLE_QUALIFIER_IDENTIFIABLE_H
#define BASYX_SUBMODEL_SIMPLE_QUALIFIER_IDENTIFIABLE_H

#include <BaSyx/submodel/api_v2/qualifier/IIdentifiable.h>
#include <BaSyx/submodel/simple/qualifier/Referable.h>
#include <BaSyx/submodel/simple/qualifier/AdministrativeInformation.h>

namespace basyx {
namespace submodel {
namespace simple {

class Identifiable : public api::IIdentifiable
{
private:
	Referable referable;
	Identifier identifier;
	AdministrativeInformation administrativeInformation;
public:
	virtual ~Identifiable() = default;

	// Constructors
	Identifiable(const std::string & idShort, const Identifier & identifier);

	// Member-access
	inline const Referable & getReferable() const noexcept { return this->referable; };
	inline Referable & getReferable() noexcept { return this->referable; };

	bool hasAdministrativeInformation() const noexcept;

	// Inherited via IReferable
	virtual std::string const & getIdShort() const override;
	virtual const std::string * const getCategory() const override;
	virtual void setCategory(const std::string & category) override;
	virtual LangStringSet & getDescription() override;
	virtual const LangStringSet & getDescription() const override;
	virtual const IReferable * const getParent() const override;

	// Inherited via IIdentifiable
	virtual const AdministrativeInformation & getAdministrativeInformation() const override;
	virtual AdministrativeInformation & getAdministrativeInformation() override;

	virtual Identifier getIdentification() const override;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_QUALIFIER_IDENTIFIABLE_H */
