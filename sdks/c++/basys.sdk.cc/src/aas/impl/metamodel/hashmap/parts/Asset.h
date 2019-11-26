/*
 * Asset.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_PARTS_ASSET_H_
#define AAS_IMPL_METAMODEL_HASHMAP_PARTS_ASSET_H_

#include "aas/parts/IAsset.h"

namespace basyx {
namespace aas {
namespace parts {

class Asset : public IAsset
{
public:
  ~Asset() = default;

  // Inherited via IAsset
  virtual basyx::specificCollection_t<reference::IReference> getDataSpecificationReferences() const override;
  virtual submodel::metamodel::map::qualifier::haskind::Kind getHasKindReference() const override;
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  virtual qualifier::impl::Description getDescription() const override;
  virtual std::shared_ptr<reference::IReference> getParent() const override;
  virtual std::shared_ptr<qualifier::IAdministrativeInformation> getAdministration() const override;
  virtual std::shared_ptr<identifier::IIdentifier> getIdentification() const override;
  virtual std::shared_ptr<reference::IReference> getAssetIdentificationModel() const override;
  virtual void setAssetIdentificationModel(const std::shared_ptr<reference::IReference>& submodel) override;
};

}
}
}

#endif
