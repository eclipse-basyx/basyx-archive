/*
 * AdministrativeInformation.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_SIMPLE_QUALIFIER_ADMINISTRATIVEINFORMATION_H
#define BASYX_SUBMODEL_SIMPLE_QUALIFIER_ADMINISTRATIVEINFORMATION_H

#include <BaSyx/submodel/simple/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/api_v2/qualifier/IAdministrativeInformation.h>

namespace basyx {
namespace submodel {
namespace simple {

class AdministrativeInformation
  : public api::IAdministrativeInformation
  , public HasDataSpecification
{
private:
	std::string version;
	std::string revision;
public:
	~AdministrativeInformation() = default;

	AdministrativeInformation();
	AdministrativeInformation(const std::string & version, const std::string & revision);
	explicit AdministrativeInformation(const IAdministrativeInformation & other);

	void setVersion(const std::string & version) override;
	void setRevision(const std::string & revision) override;

	inline bool hasVersion() const override { return not version.empty(); };
	inline bool hasRevision() const override { return not revision.empty(); };

	inline bool exists() const noexcept { return !version.empty() && !revision.empty(); };

	virtual const std::string * const getVersion() const override;
	virtual const std::string * const getRevision() const override;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_QUALIFIER_ADMINISTRATIVEINFORMATION_H */
