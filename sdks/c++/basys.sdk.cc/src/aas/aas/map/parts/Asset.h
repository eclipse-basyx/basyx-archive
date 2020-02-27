/*
 * Asset.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_PARTS_ASSET_H_
#define AAS_IMPL_METAMODEL_HASHMAP_PARTS_ASSET_H_

#include "aas/api/parts/IAsset.h"

#include "submodel/map/qualifier/HasDataSpecification.h"
#include "submodel/map/qualifier/HasKind.h"
#include "submodel/map/qualifier/Identifiable.h"
#include "submodel/map/modeltype/ModelType.h"

namespace basyx {
namespace aas {

class Asset 
  : public IAsset
  , public virtual submodel::HasKind
  , public virtual submodel::HasDataSpecification
  , public virtual submodel::Identifiable
  , public virtual submodel::ModelType
{
public:
  ~Asset() = default;

  // constructors
  Asset();

  /**
   *
   * @param submodel A reference to a Submodel that defines the handling of
   *                 additional domain specific (proprietary) Identifiers for the
   *                 asset like e.g. serial number etc.
  */
  Asset(const std::shared_ptr<submodel::IReference> & submodel);
   
  // Inherited via IAsset
  virtual std::shared_ptr<submodel::IReference> getAssetIdentificationModel() const override;
  virtual std::shared_ptr<submodel::IReference> getBillOfMaterial() const override;


  void setAssetIdentificationModel(const std::shared_ptr<submodel::IReference>& submodel);
  void setBillOfMaterial(const std::shared_ptr<submodel::IReference>& submodel);
};

}
}

#endif
