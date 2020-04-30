#include <BaSyx/submodel/map_v2/aas/AssetAdministrationShell.h>

using namespace basyx::submodel;
using namespace basyx::submodel::map;
using namespace basyx::submodel::api;

AssetAdministrationShell::AssetAdministrationShell(const std::string & idShort, const simple::Identifier & identifier, const Asset & asset)
	: Identifiable(idShort, identifier)
	, asset(asset)
{
	this->map.insertKey("submodels", submodels.getMap());
	this->map.insertKey("asset", asset.getMap());
};


ElementContainer<IConceptDescription> & AssetAdministrationShell::getConceptDictionary()
{
	return this->conceptDictionary;
};


api::IAsset & AssetAdministrationShell::getAsset()
{
	return asset;
};

IReference * AssetAdministrationShell::getDerivedFrom()
{
	return &derivedFrom;
};

void AssetAdministrationShell::setDerivedFrom(const api::IReference & reference)
{
	// TODO:
};

AssetAdministrationShell::SubmodelContainer_t & AssetAdministrationShell::getSubmodels()
{
	return this->submodels;
};