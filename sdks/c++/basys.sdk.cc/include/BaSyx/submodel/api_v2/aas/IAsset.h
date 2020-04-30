#ifndef BASYX_SUBMODEL_API_V2_AAS_IASSET_H
#define BASYX_SUBMODEL_API_V2_AAS_IASSET_H

#include <BaSyx/submodel/api_v2/qualifier/IHasDataSpecification.h>
#include <BaSyx/submodel/simple/qualifier/Identifiable.h>

#include <BaSyx/submodel/api_v2/ISubModel.h>

namespace basyx {
namespace submodel {

enum class AssetKind
{
	Type,
	Instance,
	Unknown
};

struct AssetKindUtil
{
	inline static std::string toString(AssetKind assetKind)
	{
		switch (assetKind)
		{
		case AssetKind::Type:
			return "Type";
			break;
		case AssetKind::Instance:
			return "Instance";
			break;
		default:
			return "Unknown";
			break;
		};
	};

	inline static AssetKind fromString(const std::string & str)
	{
		const std::unordered_map<std::string, AssetKind> table = {
			{"Instance", AssetKind::Instance},
			{"Type", AssetKind::Type}
		};

		if (table.find(str) != table.end())
			return table.at(str);

		return AssetKind::Unknown;
	};
};

namespace api {

class IAsset : 
	public virtual IHasDataSpecification, 
	public virtual IIdentifiable
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
