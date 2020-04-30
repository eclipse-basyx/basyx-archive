#include <BaSyx/submodel/simple/SubModel.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::simple;

SubModel::SubModel(const std::string & idShort, const Identifier & identifier)
	: kind(Kind::Instance)
	, identifiable(idShort, identifier)
{
};

IElementContainer<ISubmodelElement> & SubModel::submodelElements()
{
	return this->elementContainer;
}

Kind SubModel::getKind() const
{
	return this->kind;
}

const std::string & SubModel::getIdShort() const
{
	return this->identifiable.getIdShort();
}

const std::string * const SubModel::getCategory() const
{
	return this->identifiable.getCategory();
};

LangStringSet & SubModel::getDescription()
{
	return this->identifiable.getDescription();
}

const LangStringSet & SubModel::getDescription() const
{
	return this->identifiable.getDescription();
}

const IReferable * const SubModel::getParent() const
{
	return this->identifiable.getParent();
}

const AdministrativeInformation & SubModel::getAdministrativeInformation() const
{
	return this->identifiable.getAdministrativeInformation();
}

Identifier SubModel::getIdentification() const
{
	return this->identifiable.getIdentification();
}

AdministrativeInformation & SubModel::getAdministrativeInformation()
{
	return this->identifiable.getAdministrativeInformation();
}

bool SubModel::hasAdministrativeInformation() const
{
	return this->identifiable.hasAdministrativeInformation();
};

void SubModel::addDataSpecification(const Reference & reference)
{
	this->dataSpecification.addDataSpecification(reference);
}

const std::vector<Reference> SubModel::getDataSpecificationReference() const
{
	return this->dataSpecification.getDataSpecificationReference();
}

IReference & SubModel::getSemanticId()
{
	return this->semanticId;
}
