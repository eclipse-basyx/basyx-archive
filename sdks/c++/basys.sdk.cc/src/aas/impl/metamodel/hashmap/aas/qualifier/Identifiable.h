/*
 * Identifiable.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_IDENTIFIABLE_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_IDENTIFIABLE_H_

#include "aas/qualifier/IIdentifiable.h"
#include "Referable.h"
#include "basyx/object.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {

class Identifiable : public Referable, public aas::qualifier::IIdentifiable
{
public:
  ~Identifiable() = default;

  // constructors
  Identifiable();
  Identifiable(const std::string & version, const std::string & revision, const std::string & idShort, 
    const std::string & category, const aas::qualifier::impl::Description & description, const std::string & idType, const std::string & id);
  
  // Inherited via IIdentifiable
  virtual std::shared_ptr<aas::qualifier::IAdministrativeInformation> getAdministration() const override;
  virtual std::shared_ptr<aas::identifier::IIdentifier> getIdentification() const override;

  // not inherited
  void setAdministration(const std::shared_ptr<aas::qualifier::IAdministrativeInformation> & administration);
  void setIdentification(const std::shared_ptr<aas::identifier::IIdentifier> & identification);

private:
  std::shared_ptr<aas::qualifier::IAdministrativeInformation> administration;
  std::shared_ptr<aas::identifier::IIdentifier> identification;
};

}
}
}
}
}

#endif
