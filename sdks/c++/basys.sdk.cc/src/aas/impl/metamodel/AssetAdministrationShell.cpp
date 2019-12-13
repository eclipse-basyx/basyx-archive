/*
 * AssetAdministrationShell.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/aas/impl/metamodel/AssetAdministrationShell.h>

using namespace basyx::submodel;

namespace basyx {
namespace aas {

AssetAdministrationShell::AssetAdministrationShell()
{}

AssetAdministrationShell::AssetAdministrationShell(
	std::shared_ptr<Reference> derivedFrom, 
	std::shared_ptr<security::Security> security, 
	std::shared_ptr<Asset> asset, 
	basyx::specificCollection_t<descriptor::SubModelDescriptor> submodels, 
	basyx::specificCollection_t<IConceptDictionary> dictionaries, basyx::specificCollection_t<IView> views)
{}

void AssetAdministrationShell::setEndpoint(const std::string & endpoint, const std::string & endpointType)
{
}

basyx::object::list_t<basyx::object::hash_map_t<std::string>> AssetAdministrationShell::getEndpoints()
{
	return basyx::object::list_t<basyx::object::hash_map_t<std::string>>();
}

basyx::specificCollection_t<submodel::IReference> AssetAdministrationShell::getDataSpecificationReferences() const
{
	return basyx::specificCollection_t<submodel::IReference>();
}

void AssetAdministrationShell::setDataSpecificationReferences(const basyx::specificCollection_t<submodel::IReference>& references)
{
}

std::string AssetAdministrationShell::getIdShort() const
{
	return std::string();
}

std::string AssetAdministrationShell::getCategory() const
{
	return std::string();
}

void AssetAdministrationShell::setCategory(const std::string & category)
{
}

submodel::Description AssetAdministrationShell::getDescription() const
{
	return submodel::Description("","");
}

void AssetAdministrationShell::setDescription(const submodel::Description & description)
{
}

std::shared_ptr<submodel::IReference> AssetAdministrationShell::getParent() const
{
	return std::shared_ptr<submodel::IReference>();
}

void AssetAdministrationShell::setParent(const std::shared_ptr<submodel::IReference>& parent)
{
}

std::shared_ptr<submodel::IAdministrativeInformation> AssetAdministrationShell::getAdministration() const
{
	return std::shared_ptr<submodel::IAdministrativeInformation>();
}

void AssetAdministrationShell::setAdministration(const std::shared_ptr<submodel::IAdministrativeInformation>& administrativeInformation)
{
}

std::shared_ptr<submodel::IIdentifier> AssetAdministrationShell::getIdentification() const
{
	return std::shared_ptr<submodel::IIdentifier>();
}

void AssetAdministrationShell::setIdentification(const std::shared_ptr<submodel::IIdentifier>& identification)
{
}

basyx::specificMap_t<submodel::ISubModel> AssetAdministrationShell::getSubModels() const
{
	return basyx::specificMap_t<submodel::ISubModel>();
}

void AssetAdministrationShell::addSubModel(const descriptor::SubModelDescriptor & subModelDescriptor)
{
}

std::shared_ptr<security::ISecurity> AssetAdministrationShell::getSecurity() const
{
	return std::shared_ptr<security::ISecurity>();
}

void AssetAdministrationShell::setSecurity(const std::shared_ptr<security::ISecurity>& security)
{
}

std::shared_ptr<submodel::IReference> AssetAdministrationShell::getDerivedFrom() const
{
	return std::shared_ptr<submodel::IReference>();
}

void AssetAdministrationShell::setDerivedFrom(const std::shared_ptr<submodel::IReference>& derivedFrom)
{
}

std::shared_ptr<IAsset> AssetAdministrationShell::getAsset() const
{
	return std::shared_ptr<IAsset>();
}

void AssetAdministrationShell::setAsset(const std::shared_ptr<IAsset>& asset)
{
}

void AssetAdministrationShell::setSubmodels(const basyx::specificCollection_t<descriptor::SubModelDescriptor>& submodels)
{
}

basyx::specificCollection_t<descriptor::SubModelDescriptor> AssetAdministrationShell::getSubModelDescriptors() const
{
	return basyx::specificCollection_t<descriptor::SubModelDescriptor>();
}

basyx::specificCollection_t<IView> AssetAdministrationShell::getViews() const
{
	return basyx::specificCollection_t<IView>();
}

void AssetAdministrationShell::setViews(const basyx::specificCollection_t<IView>& views)
{
}

basyx::specificCollection_t<IConceptDictionary> AssetAdministrationShell::getConceptDictionary() const
{
	return basyx::specificCollection_t<IConceptDictionary>();
}

void AssetAdministrationShell::setConceptDictionary(const basyx::specificCollection_t<IConceptDictionary>& dictionaries)
{
}

void AssetAdministrationShell::addConceptDescription(const std::shared_ptr<IConceptDescription>& description)
{
}

}
}
