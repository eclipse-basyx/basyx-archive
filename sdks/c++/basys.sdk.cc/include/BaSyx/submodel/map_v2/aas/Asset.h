#ifndef BASYX_SUBMODEL_MAP_V2_AAS_ASSET_H
#define BASYX_SUBMODEL_MAP_V2_AAS_ASSET_H

#include <BaSyx/submodel/api_v2/aas/IAsset.h>

#include <BaSyx/submodel/map_v2/reference/Reference.h>
#include <BaSyx/submodel/map_v2/qualifier/Identifiable.h>
#include <BaSyx/submodel/map_v2/qualifier/HasDataSpecification.h>

namespace basyx {
namespace submodel {
namespace map {

class Asset : 
	public api::IAsset,
	public map::Identifiable,
	public map::HasDataSpecification,
	public virtual vab::ElementMap
{
private:
	Reference assetIdentificationModelRef;
	Reference billOfMaterialRef;
public:
	using vab::ElementMap::ElementMap;
	Asset(const std::string & idShort, const simple::Identifier & identifier, AssetKind kind = AssetKind::Instance);
	virtual ~Asset() = default;

	virtual AssetKind getKind();

	virtual api::IReference * const getAssetIdentificationModel() override;
	virtual void setAssetIdentificationModel(const api::IReference & assetIdentificationModelRef) override;

	virtual api::IReference * const getBillOfMaterial() override;
	virtual void setBillOfMaterial(const api::IReference & billOfMaterialRef) override;
};

}
}
}


#endif /* BASYX_SUBMODEL_MAP_V2_AAS_ASSET_H */
