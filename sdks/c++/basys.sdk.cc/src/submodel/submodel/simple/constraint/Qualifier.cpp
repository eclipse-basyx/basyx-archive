#include <BaSyx/submodel/simple/constraint/Qualifier.h>


using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::simple;

Qualifier::Qualifier(const std::string & qualifierType, const std::string & valueType)
	: qualifierType(qualifierType), valueType(valueType)
{
};

Qualifier::Qualifier(const std::string & qualifierType,
	const std::string & valueType,
	const std::string & valueDataType,
	const api::IReference & valueId)
	: qualifierType(qualifierType)
	, valueType(valueType)
	, valueDataType(valueDataType)
	, valueId(valueId.getKeys())
{
};

Qualifier::Qualifier(const api::IQualifier & qualifier)
	: Qualifier( qualifier.getQualifierType(), qualifier.getValueType() )
{
	if (qualifier.getValueId() != nullptr)
		this->valueId = qualifier.getValueId()->getKeys();

	if (qualifier.getValueDataType() != nullptr)
		this->valueDataType = *qualifier.getValueDataType();
};

const std::string Qualifier::getQualifierType() const
{
	return this->qualifierType;
};

const std::string Qualifier::getValueType() const
{
	return this->valueType;
};

const std::string * const Qualifier::getValueDataType() const
{
	if (this->valueDataType.empty())
		return nullptr;

	return &this->valueDataType;
};

void Qualifier::setValueDataType(const std::string & valueDataType)
{
	this->valueDataType = valueDataType;
};

const IReference * const Qualifier::getValueId() const
{
	if (this->valueId.empty())
		return nullptr;

	return &this->valueId;
};

void Qualifier::setValueId(const IReference & reference)
{
	this->valueId = reference.getKeys();
};


bool Qualifier::operator!=(const Qualifier & other) const
{
	return this->qualifierType != other.qualifierType
		&& this->valueDataType != other.valueDataType
		&& this->valueType != other.valueType
		&& this->valueId != other.valueId;
};

bool Qualifier::operator!=(const IQualifier & other) const
{
	return this->getQualifierType() != other.getQualifierType()
		&& this->getValueType() != other.getValueType()
		&& this->getValueDataType() != other.getValueDataType()
		&& this->getValueId() != other.getValueId();
};

IReference & Qualifier::getSemanticId()
{
	return this->semanticId;
}

void Qualifier::setSemanticId(const IReference & reference)
{
	this->semanticId = reference;
}

ModelTypes Qualifier::GetModelType() const
{
	return ModelTypes::Qualifier;
};