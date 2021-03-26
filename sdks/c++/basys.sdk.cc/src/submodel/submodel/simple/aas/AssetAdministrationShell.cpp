#include <BaSyx/submodel/simple/aas/AssetAdministrationShell.h>
#include <BaSyx/submodel/simple/reference/Reference.h>
#include <BaSyx/submodel/enumerations/ModelTypes.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::simple;

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

IReferable * AssetAdministrationShell::getParent() const
{
	return this->identifiable.getParent();
}

void AssetAdministrationShell::setParent(IReferable * parent)
{
	this->identifiable.setParent(parent);
}


const api::IAdministrativeInformation & AssetAdministrationShell::getAdministrativeInformation() const
{
	return this->identifiable.getAdministrativeInformation();
}

api::IAdministrativeInformation & AssetAdministrationShell::getAdministrativeInformation()
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

simple::Reference AssetAdministrationShell::getReference() const
{
	return this->identifiable.getReference();
};


ModelTypes AssetAdministrationShell::GetModelType() const
{
	return ModelTypes::AssetAdministrationShell;
}

simple::Key AssetAdministrationShell::getKey(bool local) const
{
	return this->identifiable.getKey();
};