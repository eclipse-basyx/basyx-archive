/*
 * Asset.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/aas/map/parts/Asset.h>

#include <BaSyx/submodel/map/reference/Reference.h>

namespace basyx {
namespace aas {

using namespace submodel;

Asset::Asset()
  : ModelType(IAsset::Path::ModelType)
{}

Asset::Asset(const std::shared_ptr<submodel::IReference> & submodel)
  : ModelType(IAsset::Path::ModelType)
{
  this->setAssetIdentificationModel(submodel);
}

std::shared_ptr<submodel::IReference> Asset::getAssetIdentificationModel() const
{
  return std::make_shared<Reference>(this->map.getProperty(IAsset::Path::AssetIdentificationModel));
}

std::shared_ptr<submodel::IReference> Asset::getBillOfMaterial() const
{
  return std::make_shared<Reference>(this->map.getProperty(IAsset::Path::BillOfMaterial));
}


void Asset::setAssetIdentificationModel(const std::shared_ptr<submodel::IReference>& submodel)
{
  this->map.insertKey(IAsset::Path::AssetIdentificationModel, submodel::Reference(*submodel).getMap());
}

void Asset::setBillOfMaterial(const std::shared_ptr<submodel::IReference>& submodel)
{
  this->map.insertKey(IAsset::Path::BillOfMaterial, submodel::Reference(*submodel).getMap());
}

}
}