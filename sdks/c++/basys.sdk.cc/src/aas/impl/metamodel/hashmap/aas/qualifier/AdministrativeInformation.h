/*
 * AdministrativeInformation.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_ADMINISTRATIVEINFORMATION_H_
#define AAS_IMPL_METAMODEL_ADMINISTRATIVEINFORMATION_H_

#include "aas/qualifier/IAdministrativeInformation.h"

namespace basyx {
namespace aas {
namespace qualifier {
namespace impl {

class AdministrativeInformation : public IAdministrativeInformation
{
public:
  ~AdministrativeInformation() = default;

  AdministrativeInformation();
  AdministrativeInformation(const std::string & version, const std::string & revision);
  AdministrativeInformation(basyx::objectMap_t & map);

  void setDataSpecificationReferences(const basyx::specificCollection_t<reference::IReference> & data_specification_references);
  void setVersion(const std::string & version);
  void setRevision(const std::string & revision);

  virtual basyx::specificCollection_t<reference::IReference> getDataSpecificationReferences() const override;
  virtual std::string getVersion() const override;
  virtual std::string getRevision() const override;

private:
  basyx::specificCollection_t<reference::IReference> data_specification_references;
  std::string version, revision;
};

}
}
}
}

#endif
