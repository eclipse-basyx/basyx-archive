/*
 * AdministrativeInformation.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_SIMPLE_QUALIFIER_ADMINISTRATIVEINFORMATION_H
#define BASYX_SUBMODEL_SIMPLE_QUALIFIER_ADMINISTRATIVEINFORMATION_H

//#include <BaSyx/submodel/api_v2/qualifier/IAdministrativeInformation.h>
#include <BaSyx/submodel/simple/qualifier/HasDataSpecification.h>

namespace basyx {
namespace submodel {
namespace simple {

class AdministrativeInformation : public api::IHasDataSpecification
{
private:
	HasDataSpecification hasDataSpecification;

	std::string version;
	std::string revision;
public:
	~AdministrativeInformation() = default;

	AdministrativeInformation();
	AdministrativeInformation(const std::string & version, const std::string & revision);
//	AdministrativeInformation(const IAdministrativeInformation & other);

	void setVersion(const std::string & version);
	void setRevision(const std::string & revision);

	inline bool hasVersion() const { return version.empty(); };
	inline bool hasRevision() const { return revision.empty(); };

	inline bool exists() const noexcept { return !version.empty() && !revision.empty(); };

	virtual std::string getVersion() const;
	virtual std::string getRevision() const;

	// Inherited via IHasDataSpecification
	virtual void addDataSpecification(const Reference & reference) override;
	const std::vector<Reference> getDataSpecificationReference() const override;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_QUALIFIER_ADMINISTRATIVEINFORMATION_H */
