/*
 * Asset.cpp
 *
 *      Author: wendel
 */

#include "Asset.h"

namespace basyx {
namespace aas {
namespace parts {

basyx::specificCollection_t<reference::IReference> Asset::getDataSpecificationReferences() const
{
  return basyx::specificCollection_t<reference::IReference>();
}

submodel::metamodel::map::qualifier::haskind::Kind Asset::getHasKindReference() const
{
  return submodel::metamodel::map::qualifier::haskind::Kind();
}

std::string Asset::getIdShort() const
{
  return std::string();
}

std::string Asset::getCategory() const
{
  return std::string();
}

qualifier::impl::Description Asset::getDescription() const
{
  return qualifier::impl::Description("","");
}

std::shared_ptr<reference::IReference> Asset::getParent() const
{
  return std::shared_ptr<reference::IReference>();
}

std::shared_ptr<qualifier::IAdministrativeInformation> Asset::getAdministration() const
{
  return nullptr;
}

std::shared_ptr<identifier::IIdentifier> Asset::getIdentification() const
{
  return std::shared_ptr<identifier::IIdentifier>();
}

std::shared_ptr<reference::IReference> Asset::getAssetIdentificationModel() const
{
  return std::shared_ptr<reference::IReference>();
}

void Asset::setAssetIdentificationModel(const std::shared_ptr<reference::IReference>& submodel)
{}

}
}
}
