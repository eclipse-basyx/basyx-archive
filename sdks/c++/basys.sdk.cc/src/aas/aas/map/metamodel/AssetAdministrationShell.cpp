/*
 * AssetAdministrationShell.cpp
 *
 *      Author: wendel
 */

#include "BaSyx/aas/map/metamodel/AssetAdministrationShell.h"

#include <BaSyx/aas/map/security/Security.h>
#include <BaSyx/submodel/map/reference/Reference.h>

namespace basyx {
namespace aas {

using namespace submodel;

AssetAdministrationShell::AssetAdministrationShell()
  : vab::ElementMap()
  , ModelType(IAssetAdministrationShell::Path::ModelType)
{}

AssetAdministrationShell::AssetAdministrationShell(basyx::object obj)
  : vab::ElementMap(obj)
  , ModelType(IAssetAdministrationShell::Path::ModelType)
{}

AssetAdministrationShell::AssetAdministrationShell(std::shared_ptr<submodel::IReference> parentAAS)
  : vab::ElementMap()
  , ModelType(IAssetAdministrationShell::Path::ModelType)
{
  this->setDerivedFrom(parentAAS);
}

std::shared_ptr<ISecurity> AssetAdministrationShell::getSecurity() const
{
  return std::make_shared<Security>(this->map.getProperty(IAssetAdministrationShell::Path::Security));
}

void AssetAdministrationShell::setSecurity(std::shared_ptr<ISecurity> security) const
{
  this->map.insertKey(IAssetAdministrationShell::Path::Security, Security(*security).getMap());
}

std::shared_ptr<submodel::IReference> AssetAdministrationShell::getDerivedFrom() const
{
  return std::make_shared<Reference>(this->map.getProperty(IAssetAdministrationShell::Path::DerivedFrom));
}

void AssetAdministrationShell::setDerivedFrom(std::shared_ptr<submodel::IReference> derived_from) const
{
  this->map.insertKey(IAssetAdministrationShell::Path::DerivedFrom, Reference(*derived_from).getMap());
}

}
}