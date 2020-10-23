#include <BaSyx/submodel/simple/SubModel.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::simple;

SubModel::SubModel(const std::string & idShort, const Identifier & identifier)
	: kind(ModelingKind::Instance)
	, identifiable(idShort, identifier)
{
};

IElementContainer<ISubmodelElement> & SubModel::submodelElements()
{
	return this->elementContainer;
}

const IElementContainer<ISubmodelElement> & SubModel::submodelElements() const
{
	return this->elementContainer;
}

ModelingKind SubModel::getKind() const
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

void SubModel::setCategory(const std::string & category)
{
	this->identifiable.setCategory(category);
};

LangStringSet & SubModel::getDescription()
{
	return this->identifiable.getDescription();
}

const LangStringSet & SubModel::getDescription() const
{
	return this->identifiable.getDescription();
}

IReferable * SubModel::getParent() const
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

const IReference & SubModel::getSemanticId() const
{
	return this->semanticId;
}

void SubModel::setSemanticId(const IReference & semanticId)
{
	this->semanticId = semanticId;
}

void SubModel::addFormula(const api::IFormula & formula)
{
	this->qualifiable.addFormula(formula);
};

void SubModel::addQualifier(const api::IQualifier & qualifier)
{
	this->qualifiable.addQualifier(qualifier);
};

std::vector<simple::Formula> SubModel::getFormulas() const
{
	return this->qualifiable.getFormulas();
};

std::vector<simple::Qualifier> SubModel::getQualifiers() const
{
	return this->qualifiable.getQualifiers();
};

