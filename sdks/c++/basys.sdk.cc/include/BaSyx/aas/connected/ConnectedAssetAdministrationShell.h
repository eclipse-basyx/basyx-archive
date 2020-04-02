/*
 * ConnectedAssetAdministrationShell.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_CONNECTED_CONNECTEDASSETADMINISTRATIONSHELL_H_
#define AAS_BACKEND_CONNECTED_CONNECTEDASSETADMINISTRATIONSHELL_H_

#include "aas/api/metamodel/IAssetAdministrationShell.h"
#include "submodel/connected/ConnectedVABModelMap.h"
#include "aas/api/manager/IAssetAdministrationShellManager.h"

namespace basyx {
namespace aas {
namespace backend {

class ConnectedAssetAdministrationShell : public api::IAssetAdministrationShell, public submodel::backend::ConnectedVABModelMap
{
public:
  ConnectedAssetAdministrationShell(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy, std::shared_ptr<api::manager::IAssetAdministrationShellManager> manager);
	~ConnectedAssetAdministrationShell() = default;

private:
  std::shared_ptr<api::manager::IAssetAdministrationShellManager> manager;

  // Inherited via IAssetAdministrationShell
  virtual basyx::specificCollection_t<submodel::IReference> getDataSpecificationReferences() const override;
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  virtual submodel::Description getDescription() const override;
  virtual std::shared_ptr<submodel::IReference> getParent() const override;
  virtual std::shared_ptr<submodel::IAdministrativeInformation> getAdministration() const override;
  virtual std::shared_ptr<submodel::IIdentifier> getIdentification() const override;
  virtual basyx::specificMap_t<submodel::ISubModel> getSubModels() const override;
  virtual void addSubModel(const descriptor::SubModelDescriptor & subModelDescriptor) override;
  virtual std::shared_ptr<security::ISecurity> getSecurity() const override;
  virtual std::shared_ptr<submodel::IReference> getDerivedFrom() const override;
  virtual std::shared_ptr<aas::IAsset> getAsset() const override;
  virtual void setSubmodels(const basyx::specificCollection_t<descriptor::SubModelDescriptor> & submodels) override;
  virtual basyx::specificCollection_t<descriptor::SubModelDescriptor> getSubModelDescriptors() const override;
  virtual basyx::specificCollection_t<IView> getViews() const override;
  virtual basyx::specificCollection_t<IConceptDictionary> getConceptDictionary() const override;
};

}
}
}

#endif
