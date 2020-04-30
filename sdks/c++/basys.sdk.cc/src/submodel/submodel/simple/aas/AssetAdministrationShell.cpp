#include <BaSyx/submodel/simple/aas/AssetAdministrationShell.h>
#include <BaSyx/submodel/simple/reference/Reference.h>

using namespace basyx::submodel::simple;
using namespace basyx::submodel::api;

AssetAdministrationShell::AssetAdministrationShell(const std::string & idShort, const Identifier & identifier, const Asset & asset)
	: identifiable(idShort, identifier)
	, asset(asset)
{
	
};


ElementContainer<IConceptDescription> & AssetAdministrationShell::getConceptDictionary()
{
	return this->conceptDictionary;
};


IAsset & basyx::submodel::simple::AssetAdministrationShell::getAsset()
{
	return this->asset;
}

Reference * basyx::submodel::simple::AssetAdministrationShell::getDerivedFrom()
{
	return &this->derivedFrom;
}

void basyx::submodel::simple::AssetAdministrationShell::setDerivedFrom(const IReference & derivedFrom)
{
//	this->derivedFrom = dynamic_cast<Reference>(derivedFrom);
}

IAssetAdministrationShell::SubmodelContainer_t & basyx::submodel::simple::AssetAdministrationShell::getSubmodels()
{
	return this->submodels;
}

const std::string & AssetAdministrationShell::getIdShort() const
{
	return this->identifiable.getIdShort();
}

const std::string * const AssetAdministrationShell::getCategory() const
{
	return this->identifiable.getCategory();
};

void AssetAdministrationShell::setCategory(const std::string & category)
{
	this->identifiable.setCategory(category);
}

LangStringSet & AssetAdministrationShell::getDescription()
{
	return this->identifiable.getDescription();
}

const LangStringSet & AssetAdministrationShell::getDescription() const
{
	return this->identifiable.getDescription();
}

const IReferable * const AssetAdministrationShell::getParent() const
{
	return this->identifiable.getParent();
}

const AdministrativeInformation & AssetAdministrationShell::getAdministrativeInformation() const
{
	return this->identifiable.getAdministrativeInformation();
}

AdministrativeInformation & AssetAdministrationShell::getAdministrativeInformation()
{
	return this->identifiable.getAdministrativeInformation();
}

Identifier AssetAdministrationShell::getIdentification() const
{
	return this->identifiable.getIdentification();
}

bool AssetAdministrationShell::hasAdministrativeInformation() const
{
	return this->identifiable.hasAdministrativeInformation();
};


void AssetAdministrationShell::addDataSpecification(const Reference & reference)
{
	this->dataSpecification.addDataSpecification(reference);
}

const std::vector<Reference> AssetAdministrationShell::getDataSpecificationReference() const
{
	return this->dataSpecification.getDataSpecificationReference();
}
