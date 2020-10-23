#include <BaSyx/submodel/map_v2/aas/AssetAdministrationShell.h>

using namespace basyx::submodel;
using namespace basyx::submodel::map;
using namespace basyx::submodel::api;

constexpr char AssetAdministrationShell::Path::Submodels[];
constexpr char AssetAdministrationShell::Path::Asset[];

AssetAdministrationShell::AssetAdministrationShell(const std::string & idShort, const simple::Identifier & identifier, const Asset & asset)
	: Identifiable(idShort, identifier)
	, asset(asset)
	, submodels(this)
	, conceptDictionary(this)
{
	this->map.insertKey(Path::Submodels, submodels.getKeyMap());
	this->map.insertKey(Path::Asset, asset.getMap());
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