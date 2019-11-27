/*
 * AdministrativeInformation.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_ADMINISTRATIVEINFORMATION_H_
#define AAS_IMPL_METAMODEL_ADMINISTRATIVEINFORMATION_H_

#include "submodel/api/qualifier/IAdministrativeInformation.h"
#include "submodel/map/qualifier/HasDataSpecification.h"

#include "basyx/object.h"

#include "vab/ElementMap.h"

namespace basyx {
namespace submodel {

class AdministrativeInformation :
	public IAdministrativeInformation,
	public HasDataSpecification
{
public:
  ~AdministrativeInformation() = default;

  AdministrativeInformation();
  AdministrativeInformation(const std::string & version, const std::string & revision);
  AdministrativeInformation(basyx::object object);

  void setVersion(const std::string & version);
  void setRevision(const std::string & revision);

  virtual std::string getVersion() const override;
  virtual std::string getRevision() const override;

  // Inherited via IAdministrativeInformation
  virtual basyx::specificCollection_t<IReference> getDataSpecificationReferences() const override;
};

}
}

#endif
