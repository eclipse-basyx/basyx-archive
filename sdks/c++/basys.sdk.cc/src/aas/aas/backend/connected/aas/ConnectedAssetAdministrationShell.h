/*
 * ConnectedAssetAdministrationShell.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_CONNECTED_CONNECTEDASSETADMINISTRATIONSHELL_H_
#define AAS_BACKEND_CONNECTED_CONNECTEDASSETADMINISTRATIONSHELL_H_

#include "api/metamodel/aas/IAssetAdministrationShell.h"
#include "backend/connected/aas/ConnectedVABModelMap.h"
#include "api/manager/IAssetAdministrationShellManager.h"

namespace basyx {
namespace aas {
namespace backend {

class ConnectedAssetAdministrationShell : public api::IAssetAdministrationShell, public ConnectedVABModelMap
{
public:
  ConnectedAssetAdministrationShell(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy, std::shared_ptr<api::manager::IAssetAdministrationShellManager> manager);
	~ConnectedAssetAdministrationShell() = default;

private:
  std::shared_ptr<api::manager::IAssetAdministrationShellManager> manager;

  // Inherited via IAssetAdministrationShell
  virtual basyx::specificCollection_t<reference::IReference> getDataSpecificationReferences() const override;
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  virtual qualifier::impl::Description getDescription() const override;
  virtual std::shared_ptr<reference::IReference> getParent() const override;
  virtual std::shared_ptr<qualifier::IAdministrativeInformation> getAdministration() const override;
  virtual std::shared_ptr<identifier::IIdentifier> getIdentification() const override;
  virtual basyx::specificMap_t<ISubModel> getSubModels() const override;
  virtual void addSubModel(const descriptor::SubModelDescriptor & subModelDescriptor) override;
  virtual std::shared_ptr<security::ISecurity> getSecurity() const override;
  virtual std::shared_ptr<reference::IReference> getDerivedFrom() const override;
  virtual std::shared_ptr<parts::IAsset> getAsset() const override;
  virtual void setSubmodels(const basyx::specificCollection_t<descriptor::SubModelDescriptor> & submodels) override;
  virtual basyx::specificCollection_t<descriptor::SubModelDescriptor> getSubModelDescriptors() const override;
  virtual basyx::specificCollection_t<IView> getViews() const override;
  virtual basyx::specificCollection_t<IConceptDictionary> getConceptDictionary() const override;
};

}
}
}

#endif
