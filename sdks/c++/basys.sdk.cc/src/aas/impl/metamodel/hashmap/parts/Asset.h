/*
 * Asset.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_PARTS_ASSET_H_
#define AAS_IMPL_METAMODEL_HASHMAP_PARTS_ASSET_H_

#include "aas/aas/parts/IAsset.h"

namespace basyx {
namespace aas {
namespace parts {

class Asset : public IAsset
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
  Asset(std::shared_ptr<reference::IReference> submodel);

  // Inherited via IAsset
  virtual basyx::specificCollection_t<reference::IReference> getDataSpecificationReferences() const override;
  virtual submodel::metamodel::map::qualifier::haskind::Kind getHasKindReference() const override;
  void setHasKindReference(const submodel::metamodel::map::qualifier::haskind::Kind & kind);
  virtual std::string getIdShort() const override;
  void setIdShort(const std::string & idShort);
  virtual std::string getCategory() const override;
  void setCategory(const std::string & category);
  virtual qualifier::impl::Description getDescription() const override;
  void setDescription(const qualifier::impl::Description & description);
  virtual std::shared_ptr<reference::IReference> getParent() const override;
  void setParent(const std::shared_ptr<reference::IReference> & parentReference);
  virtual std::shared_ptr<qualifier::IAdministrativeInformation> getAdministration() const override;
  void setAdministration(const std::shared_ptr<qualifier::IAdministrativeInformation> & administration);
  virtual std::shared_ptr<identifier::IIdentifier> getIdentification() const override;
  void setIdentification(const std::shared_ptr<identifier::IIdentifier> & identification);
  virtual std::shared_ptr<reference::IReference> getAssetIdentificationModel() const override;
  virtual void setAssetIdentificationModel(const std::shared_ptr<reference::IReference>& submodel) override;
  void setId(const std::string & id);

private:
  std::shared_ptr<reference::IReference> submodel;
  basyx::specificCollection_t<reference::IReference> dataSpecificationReferences;
};

}
}
}

#endif
