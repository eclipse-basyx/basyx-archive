/*
 * AdministrativeInformation.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_ADMINISTRATIVEINFORMATION_H_
#define AAS_IMPL_METAMODEL_ADMINISTRATIVEINFORMATION_H_

#include <BaSyx/submodel/api/qualifier/IAdministrativeInformation.h>
#include <BaSyx/submodel/map/qualifier/HasDataSpecification.h>

#include <BaSyx/shared/object.h>

#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {

class AdministrativeInformation :
	public virtual IAdministrativeInformation,
	public virtual HasDataSpecification,
  public virtual vab::ElementMap
{
public:
  ~AdministrativeInformation() = default;

  AdministrativeInformation();
  AdministrativeInformation(const std::string & version, const std::string & revision);
  AdministrativeInformation(basyx::object object);
  AdministrativeInformation(const IAdministrativeInformation & other);

  void setVersion(const std::string & version);
  void setRevision(const std::string & revision);

  virtual std::string getVersion() const override;
  virtual std::string getRevision() const override;
};

}
}

#endif
