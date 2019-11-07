/*
 * IAssetAdministrationShellManager.h
 *
 *      Author: wendel
 */

#ifndef IASSETADMINISTRATIONSHELLMANAGER_H_
#define IASSETADMINISTRATIONSHELLMANAGER_H_

#include <string>
#include "api/metamodel/aas/IAssetAdministrationShell.h"
#include "api/modelurn/ModelUrn.h"
#include "aas/ISubModel.h"

namespace basyx {
namespace aas {
namespace api {
namespace manager {

class IAssetAdministrationShellManager
{
public:
  ~IAssetAdministrationShellManager() = default;
  virtual std::shared_ptr<IAssetAdministrationShell> retrieveAAS(ModelUrn aasUrn) = 0;
  virtual basyx::specificCollection_t<IAssetAdministrationShell> retrieveAASAll() = 0;
  virtual void createAAS(std::shared_ptr<IAssetAdministrationShell> aas, ModelUrn urn) = 0;
  virtual void deleteAAS(std::string id) = 0;
  virtual std::shared_ptr<ISubModel> retrieveSubModel(ModelUrn aasUrn, std::string subModelId) = 0;
  virtual void createSubModel(ModelUrn aasUrn, std::shared_ptr<ISubModel> submodel) = 0;
};

}
}
}
}

#endif
