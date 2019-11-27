/*
 * AssetAdministrationShell.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_ASSETADMINISTRATIONSHELL_H_
#define AAS_IMPL_METAMODEL_HASHMAP_ASSETADMINISTRATIONSHELL_H_

#include "aas/api/metamodel/aas/IAssetAdministrationShell.h"
#include "aas/aas/parts/IConceptDescription.h"
#include "aas/aas/security/ISecurity.h"
#include "submodel/map/reference/Reference.h"
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
  AssetAdministrationShell(std::shared_ptr<submodel::Reference> derivedFrom, std::shared_ptr<security::Security> security, std::shared_ptr<Asset> asset,
    basyx::specificCollection_t<descriptor::SubModelDescriptor> submodels, basyx::specificCollection_t<IConceptDictionary> dictionaries,
    basyx::specificCollection_t<IView> views);


  // Inherited via IAssetAdministrationShell	
  void setEndpoint(const std::string & endpoint, const std::string & endpointType);
  basyx::object::list_t<basyx::object::hash_map_t<std::string>> getEndpoints();
  virtual basyx::specificCollection_t<submodel::IReference> getDataSpecificationReferences() const override;
  void setDataSpecificationReferences(const basyx::specificCollection_t<submodel::IReference>& references);
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  void setCategory(const std::string & category);
  virtual submodel::Description getDescription() const override;
  void setDescription(const submodel::Description & description);
  virtual std::shared_ptr<submodel::IReference> getParent() const override;
  void setParent(const std::shared_ptr<submodel::IReference> & parent);
  virtual std::shared_ptr<submodel::IAdministrativeInformation> getAdministration() const override;
  void setAdministration(const std::shared_ptr<submodel::IAdministrativeInformation> & administrativeInformation);
  virtual std::shared_ptr<submodel::IIdentifier> getIdentification() const override;
  void setIdentification(const std::shared_ptr<submodel::IIdentifier> & identification);
  virtual basyx::specificMap_t<submodel::api::ISubModel> getSubModels() const override;
  virtual void addSubModel(const descriptor::SubModelDescriptor & subModelDescriptor) override;
  virtual std::shared_ptr<security::ISecurity> getSecurity() const override;
  void setSecurity(const std::shared_ptr<security::ISecurity> & security);
  virtual std::shared_ptr<submodel::IReference> getDerivedFrom() const override;
  void setDerivedFrom(const std::shared_ptr<submodel::IReference> & derivedFrom);
  virtual std::shared_ptr<IAsset> getAsset() const override;
  void setAsset(const std::shared_ptr<IAsset> & asset);
  virtual void setSubmodels(const basyx::specificCollection_t<descriptor::SubModelDescriptor> & submodels) override;
  virtual basyx::specificCollection_t<descriptor::SubModelDescriptor> getSubModelDescriptors() const override;
  virtual basyx::specificCollection_t<IView> getViews() const override;
  void setViews(const basyx::specificCollection_t<IView> & views);
  virtual basyx::specificCollection_t<IConceptDictionary> getConceptDictionary() const override;
  void setConceptDictionary(const basyx::specificCollection_t<IConceptDictionary> & dictionaries);

  /**
   * Allows addition of a concept description to the concept dictionary
   *
   * @param description
  */
   void addConceptDescription(const std::shared_ptr<IConceptDescription> & description);

private:
  //std::shared_ptr<reference::IReference> derivedFrom;
  //std::shared_ptr<security::ISecurity> security;
  //std::shared_ptr<parts::IAsset> asset;
  //basyx::specificCollection_t<descriptor::SubModelDescriptor> submodels;
  //basyx::specificCollection_t<IConceptDictionary> dictionaries;
  //basyx::specificCollection_t<IView> views;
  //std::string endpoint, endpointType, idShort, category;
  //basyx::specificCollection_t<reference::IReference> dataSpecificationReferences;
  //qualifier::impl::Description description;
  //std::shared_ptr<reference::IReference> parent;
  //std::shared_ptr<submodel::api::qualifier::IAdministrativeInformation> administration;
  //std::shared_ptr<submodel::IIdentifier> identification;
};

}
}

#endif
