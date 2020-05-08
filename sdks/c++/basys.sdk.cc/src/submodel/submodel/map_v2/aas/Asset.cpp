#include <BaSyx/submodel/map_v2/aas/Asset.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::map;

Asset::Asset(const std::string & idShort, const simple::Identifier & identifier, AssetKind kind)
	: Identifiable(idShort, identifier)
	, billOfMaterialRef()
	, assetIdentificationModelRef()
	, vab::ElementMap()
{
	this->map.insertKey("kind", AssetKind_::to_string(kind));
	this->map.insertKey("assetIdentificationModelRef", assetIdentificationModelRef.getMap());
	this->map.insertKey("billOfMaterialRef", billOfMaterialRef.getMap());
};

AssetKind Asset::getKind()
{
	return AssetKind_::from_string(this->map.getProperty("kind").Get<std::string&>());
};

IReference * const Asset::getAssetIdentificationModel()
{
	return &this->assetIdentificationModelRef;
};

void Asset::setAssetIdentificationModel(const api::IReference & assetIdentificationModelRef)
{
//	this->assetIdentificationModelRef = assetIdentificationModelRef;
};

IReference * const Asset::getBillOfMaterial()
{
	return &this->billOfMaterialRef;
};

void Asset::setBillOfMaterial(const api::IReference & billOfMaterialRef)
{
//	this->billOfMaterialRef = billOfMaterialRef;
};