/*
 * AssetAdministrationShell.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_ASSETADMINISTRATIONSHELL_H_
#define AAS_IMPL_METAMODEL_HASHMAP_ASSETADMINISTRATIONSHELL_H_

#include "api/metamodel/aas/IAssetAdministrationShell.h"
#include "impl/metamodel/hashmap/aas/reference/Reference.h"
#include "impl/metamodel/hashmap/security/Security.h"
#include "impl/metamodel/hashmap/parts/Asset.h"
#include "impl/metamodel/hashmap/parts/ConceptDictionary.h"
#include "impl/metamodel/hashmap/parts/View.h"

namespace basyx {
namespace aas {

class AssetAdministrationShell : public api::IAssetAdministrationShell
{
public:
  // destructor
  ~AssetAdministrationShell() = default;

  // constructors
  AssetAdministrationShell();
  AssetAdministrationShell(reference::impl::Reference derivedFrom, security::Security security, parts::Asset asset,
    basyx::object::set_t<std::shared_ptr<descriptor::SubModelDescriptor>> submodels, basyx::object::set_t<std::shared_ptr<parts::ConceptDictionary>> dictionaries,
    basyx::object::set_t<std::shared_ptr<parts::View>> views);


  // Inherited via IAssetAdministrationShell
  virtual basyx::specificCollection_t<reference::IReference> getDataSpecificationReferences() const override;
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  virtual qualifier::impl::Description getDescription() const override;
  virtual std::shared_ptr<reference::IReference> getParent() const override;
  virtual std::shared_ptr<qualifier::IAdministrativeInformation> getAdministration() const override;
  virtual std::shared_ptr<identifier::IIdentifier> getIdentification() const override;
  virtual basyx::specificMap_t<ISubModel> getSubModels() const override;
  virtual void addSubModel(const descriptor::SubModelDescriptor subModelDescriptor) override;
  virtual std::shared_ptr<ISecurity> getSecurity() const override;
  virtual std::shared_ptr<reference::IReference> getDerivedFrom() const override;
  virtual std::shared_ptr<reference::IReference> getAsset() const override;
  virtual void setSubModel(const basyx::specificCollection_t<descriptor::SubModelDescriptor> submodels) const override;
  virtual basyx::specificCollection_t<descriptor::SubModelDescriptor> getSubModelDescriptors() const override;
  virtual basyx::specificCollection_t<IView> getViews() const override;
  virtual basyx::specificCollection_t<IConceptDictionary> getConceptDictionary() const override;
};

}
}

#endif
