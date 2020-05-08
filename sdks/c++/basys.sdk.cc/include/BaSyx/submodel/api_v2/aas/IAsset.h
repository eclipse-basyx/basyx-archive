#ifndef BASYX_SUBMODEL_API_V2_AAS_IASSET_H
#define BASYX_SUBMODEL_API_V2_AAS_IASSET_H

#include <BaSyx/submodel/api_v2/qualifier/IHasDataSpecification.h>
#include <BaSyx/submodel/api_v2/common/IModelType.h>

#include <BaSyx/submodel/api_v2/ISubModel.h>
#include <BaSyx/submodel/enumerations/AssetKind.h>
#include <BaSyx/submodel/simple/qualifier/Identifiable.h>


namespace basyx {
namespace submodel {

namespace api {

class IAsset : 
	public virtual IHasDataSpecification, 
	public virtual IIdentifiable,
	public virtual IModelType
{
public:
	virtual ~IAsset() = 0;

	virtual AssetKind getKind() = 0;
	virtual IReference * const getAssetIdentificationModel() = 0;
	virtual void setAssetIdentificationModel(const IReference & assetIdentificationModel) = 0;

	virtual IReference * const getBillOfMaterial() = 0;
	virtual void setBillOfMaterial(const IReference & billOfMaterial) = 0;
};

inline IAsset::~IAsset() = default;

}
}
}


#endif /* BASYX_SUBMODEL_API_V2_AAS_IASSET_H */
