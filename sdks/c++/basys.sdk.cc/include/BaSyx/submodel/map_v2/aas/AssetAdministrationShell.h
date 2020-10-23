#ifndef BASYX_SUBMODEL_MAP_V2_AAS_ASSETADMINISTRATIONSHELL_H
#define BASYX_SUBMODEL_MAP_V2_AAS_ASSETADMINISTRATIONSHELL_H

#include <BaSyx/submodel/api_v2/aas/IAssetAdministrationShell.h>
#include <BaSyx/submodel/api_v2/parts/IConceptDescription.h>
#include <BaSyx/submodel/api_v2/identifier/Identifier.h>

#include <BaSyx/submodel/map_v2/aas/Asset.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>

#include <BaSyx/submodel/map_v2/common/ModelType.h>
#include <BaSyx/submodel/map_v2/common/ElementContainer.h>

namespace basyx {
namespace submodel {
namespace map { 

class AssetAdministrationShell : 
	public api::IAssetAdministrationShell,
	public map::Identifiable,
	public map::HasDataSpecification,
	public map::ModelType<ModelTypes::AssetAdministrationShell>,
	public virtual vab::ElementMap
{
public:
  struct Path {
    static constexpr char Submodels[] = "submodels";
    static constexpr char Asset[] = "asset";
  };
private:
	Reference derivedFrom;
	Asset asset;
	ElementContainer<api::IConceptDescription> conceptDictionary;
	ElementContainer<api::ISubModel> submodels;
public:
	virtual ~AssetAdministrationShell() = default;
public:
	AssetAdministrationShell(const std::string & idShort, const simple::Identifier & identifier, const Asset & asset);

	ElementContainer<api::IConceptDescription> & getConceptDictionary();

	// Inherited via IAssetAdministrationShell
	virtual api::IAsset & getAsset() override;
	virtual api::IReference * getDerivedFrom() override;
	virtual void setDerivedFrom(const api::IReference & reference) override;
	virtual SubmodelContainer_t & getSubmodels() override;

	virtual KeyElements getKeyElementType() const override { return KeyElements::AssetAdministrationShell; };
};

}
}
}


#endif /* BASYX_SUBMODEL_MAP_V2_AAS_ASSETADMINISTRATIONSHELL_H */
