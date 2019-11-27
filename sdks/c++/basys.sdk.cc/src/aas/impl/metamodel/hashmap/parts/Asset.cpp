/*
 * Asset.cpp
 *
 *      Author: wendel
 */

#include "Asset.h"

namespace basyx {
namespace aas {
namespace parts {

Asset::Asset()
{}

Asset::Asset(std::shared_ptr<reference::IReference> submodel)
{}

basyx::specificCollection_t<reference::IReference> Asset::getDataSpecificationReferences() const
{
  return basyx::specificCollection_t<reference::IReference>();
}

submodel::metamodel::map::qualifier::haskind::Kind Asset::getHasKindReference() const
{
  return submodel::metamodel::map::qualifier::haskind::Kind();
}

void Asset::setHasKindReference(const submodel::metamodel::map::qualifier::haskind::Kind & kind)
{}

std::string Asset::getIdShort() const
{
  return std::string();
}

void Asset::setIdShort(const std::string & idShort)
{}

std::string Asset::getCategory() const
{
  return std::string();
}

void Asset::setCategory(const std::string & category)
{}

qualifier::impl::Description Asset::getDescription() const
{
  return qualifier::impl::Description("","");
}

void Asset::setDescription(const qualifier::impl::Description & description)
{}

std::shared_ptr<reference::IReference> Asset::getParent() const
{
  return std::shared_ptr<reference::IReference>();
}

void Asset::setParent(const std::shared_ptr<reference::IReference>& parentReference)
{}

std::shared_ptr<qualifier::IAdministrativeInformation> Asset::getAdministration() const
{
  return nullptr;
}

void Asset::setAdministration(const std::shared_ptr<qualifier::IAdministrativeInformation>& administration)
{}

std::shared_ptr<identifier::IIdentifier> Asset::getIdentification() const
{
  return std::shared_ptr<identifier::IIdentifier>();
}

void Asset::setIdentification(const std::shared_ptr<identifier::IIdentifier>& identification)
{}

std::shared_ptr<reference::IReference> Asset::getAssetIdentificationModel() const
{
  return std::shared_ptr<reference::IReference>();
}

void Asset::setAssetIdentificationModel(const std::shared_ptr<reference::IReference>& submodel)
{}

void Asset::setId(const std::string & id)
{}

}
}
}
