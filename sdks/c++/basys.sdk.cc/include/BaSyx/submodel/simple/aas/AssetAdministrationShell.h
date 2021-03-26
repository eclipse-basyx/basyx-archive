#ifndef BASYX_SUBMODEL_SIMPLE_AAS_ASSETADMINISTRATIONSHELL_H
#define BASYX_SUBMODEL_SIMPLE_AAS_ASSETADMINISTRATIONSHELL_H

#include <BaSyx/submodel/api_v2/aas/IAssetAdministrationShell.h>
#include <BaSyx/submodel/api_v2/parts/IConceptDescription.h>

#include <BaSyx/submodel/simple/aas/Asset.h>
#include <BaSyx/submodel/simple/reference/Reference.h>


#include <BaSyx/submodel/simple/common/ElementContainer.h>

namespace basyx {
namespace submodel {
namespace simple { 

class AssetAdministrationShell : public api::IAssetAdministrationShell
{
public:
	using asset_t = basyx::submodel::simple::Asset;
private:
	Identifiable identifiable;
	HasDataSpecification dataSpecification;

	Reference derivedFrom;
	Asset asset;
	ElementContainer<api::IConceptDescription> conceptDictionary;
	ElementContainer<api::ISubModel> submodels;
public:
	virtual ~AssetAdministrationShell() = default;
public:
	AssetAdministrationShell(const std::string & idShort, const Identifier & identifier, const Asset & asset);

	ElementContainer<api::IConceptDescription> & getConceptDictionary();

	// Inherited via IAssetAdministrationShell
	virtual api::IAsset & getAsset() override;
	virtual Reference * getDerivedFrom() override;
	virtual void setDerivedFrom(const api::IReference & reference) override;
	virtual SubmodelContainer_t & getSubmodels() override;

	// Inherited via IReferable
	virtual const std::string & getIdShort() const override;
	virtual const std::string * const getCategory() const override;
	virtual void setCategory(const std::string & category) override;
	virtual simple::LangStringSet & getDescription() override;
	virtual const simple::LangStringSet & getDescription() const override;
	virtual void setParent(api::IReferable * parent) override;
	virtual IReferable * getParent() const override;
	virtual simple::Reference getReference() const override;

	// Inherited via IIdentifiable
	virtual const api::IAdministrativeInformation & getAdministrativeInformation() const override;
	virtual api::IAdministrativeInformation & getAdministrativeInformation() override;

	virtual Identifier getIdentification() const override;

	virtual bool hasAdministrativeInformation() const override;

	// Inherited via IHasDataSpecification
	virtual void addDataSpecification(const Reference & reference) override;
	virtual const std::vector<Reference> getDataSpecificationReference() const override;

	// Inherited via IModelType
	virtual ModelTypes GetModelType() const override;
	virtual KeyElements getKeyElementType() const override { return KeyElements::AssetAdministrationShell; };
	virtual Key getKey(bool local = true) const override;
};

}
}
}


#endif /* BASYX_SUBMODEL_SIMPLE_AAS_ASSETADMINISTRATIONSHELL_H */
