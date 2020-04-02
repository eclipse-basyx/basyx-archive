/*
 * AssetAdministrationShell.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_ASSETADMINISTRATIONSHELL_H_
#define AAS_IMPL_METAMODEL_HASHMAP_ASSETADMINISTRATIONSHELL_H_

#include <BaSyx/aas/api/metamodel/IAssetAdministrationShell.h>

#include <BaSyx/submodel/map/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/map/qualifier/Identifiable.h>
#include <BaSyx/submodel/map/qualifier/Referable.h>
#include <BaSyx/submodel/map/modeltype/ModelType.h>

#include <BaSyx/shared/object.h>
#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace aas {

class AssetAdministrationShell 
  : public IAssetAdministrationShell
  , public virtual vab::ElementMap
  , public virtual submodel::HasDataSpecification
  , public virtual submodel::Identifiable
  , public virtual submodel::Referable
  , public virtual submodel::ModelType
{
public:
  ~AssetAdministrationShell() = default;

  // constructors
  AssetAdministrationShell();
  AssetAdministrationShell(basyx::object obj);
  AssetAdministrationShell(std::shared_ptr<submodel::IReference> parentAAS);
  
  // Inherited via IAssetAdministrationShell
  virtual std::shared_ptr<ISecurity> getSecurity() const override;
  virtual void setSecurity(std::shared_ptr<ISecurity> security) const override;
  virtual std::shared_ptr<submodel::IReference> getDerivedFrom() const override;
  virtual void setDerivedFrom(std::shared_ptr<submodel::IReference> derived_from) const override;
};

}
}

#endif
