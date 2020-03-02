/*
 * IAssetAdministrationShell.h
 *
 *      Author: wendel
 */

#ifndef AAS_IASSETADMINISTRATIONSHELL_H_
#define AAS_IASSETADMINISTRATIONSHELL_H_

#include "submodel/api/qualifier/IHasDataSpecification.h"
#include "submodel/api/qualifier/IIdentifiable.h"
#include "submodel/api/qualifier/IReferable.h"
#include "aas/api/security/ISecurity.h"
#include "submodel/api/reference/IReference.h"

#include <memory>

namespace basyx {
namespace aas {

class IAssetAdministrationShell
  : public virtual submodel::IHasDataSpecification
  , public virtual submodel::IIdentifiable
  , public virtual submodel::IReferable
{
public:
  struct Path
  {
    static constexpr char ModelType[] = "AssetAdministationShell";
    static constexpr char Security[] = "security";
    static constexpr char DerivedFrom[] = "derivedFrom";
  };
public:
  virtual std::shared_ptr<ISecurity> getSecurity() const = 0;
  virtual void setSecurity(std::shared_ptr<ISecurity> security) const = 0;

  virtual std::shared_ptr<submodel::IReference> getDerivedFrom() const = 0;
  virtual void setDerivedFrom(std::shared_ptr<submodel::IReference> derived_from) const = 0;
};

}
}

#endif