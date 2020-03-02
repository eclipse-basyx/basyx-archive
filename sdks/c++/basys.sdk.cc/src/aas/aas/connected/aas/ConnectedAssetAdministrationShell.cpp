/*
 * ConnectedAssetAdministrationShell.cpp
 *
 *      Author: wendel
 */

#include "ConnectedAssetAdministrationShell.h"


namespace basyx {
namespace aas {
namespace backend {

ConnectedAssetAdministrationShell::ConnectedAssetAdministrationShell(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy, std::shared_ptr<api::manager::IAssetAdministrationShellManager> manager) :
  ConnectedVABModelMap(proxy),
  manager(manager)
{}

basyx::specificCollection_t<submodel::IReference> ConnectedAssetAdministrationShell::getDataSpecificationReferences() const
{
  return basyx::specificCollection_t<submodel::IReference>();
}

std::string ConnectedAssetAdministrationShell::getIdShort() const
{
  return std::string();
}

std::string ConnectedAssetAdministrationShell::getCategory() const
{
  return std::string();
}

submodel::Description ConnectedAssetAdministrationShell::getDescription() const
{
  return submodel::Description("","");
}

std::shared_ptr<submodel::IReference> ConnectedAssetAdministrationShell::getParent() const
{
  return std::shared_ptr<submodel::IReference>();
}

std::shared_ptr<submodel::IAdministrativeInformation> ConnectedAssetAdministrationShell::getAdministration() const
{
  return nullptr;
}

std::shared_ptr<submodel::IIdentifier> ConnectedAssetAdministrationShell::getIdentification() const
{
  return std::shared_ptr<submodel::IIdentifier>();
}

basyx::specificMap_t<submodel::ISubModel> ConnectedAssetAdministrationShell::getSubModels() const
{
  return basyx::specificMap_t<submodel::ISubModel>();
}

void ConnectedAssetAdministrationShell::addSubModel(const descriptor::SubModelDescriptor & subModelDescriptor)
{}

std::shared_ptr<security::ISecurity> ConnectedAssetAdministrationShell::getSecurity() const
{
  return std::shared_ptr<security::ISecurity>();
}

std::shared_ptr<submodel::IReference> ConnectedAssetAdministrationShell::getDerivedFrom() const
{
  return std::shared_ptr<submodel::IReference>();
}

std::shared_ptr<aas::IAsset> ConnectedAssetAdministrationShell::getAsset() const
{
  return std::shared_ptr<aas::IAsset>();
}

void ConnectedAssetAdministrationShell::setSubmodels(const basyx::specificCollection_t<descriptor::SubModelDescriptor> & submodels)
{}

basyx::specificCollection_t<descriptor::SubModelDescriptor> ConnectedAssetAdministrationShell::getSubModelDescriptors() const
{
  return basyx::specificCollection_t<descriptor::SubModelDescriptor>();
}

basyx::specificCollection_t<IView> ConnectedAssetAdministrationShell::getViews() const
{
  return basyx::specificCollection_t<IView>();
}

basyx::specificCollection_t<IConceptDictionary> ConnectedAssetAdministrationShell::getConceptDictionary() const
{
  return basyx::specificCollection_t<IConceptDictionary>();
}


}
}
}

