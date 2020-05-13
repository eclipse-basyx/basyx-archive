#include <BaSyx/submodel/map_v2/constraint/Qualifier.h>


using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::map;

Qualifier::Qualifier(const std::string & qualifierType, const std::string & valueType)
{
	this->map.insertKey("qualifierType", qualifierType);
	this->map.insertKey("valueType", valueType);
	this->map.insertKey("semanticId", this->semanticId.getMap());
	this->map.insertKey("valueId", this->valueId.getMap());
};

Qualifier::Qualifier(const std::string & qualifierType,
	const std::string & valueType,
	const std::string & valueDataType,
	const api::IReference & valueId)
	: valueId(valueId)
{
	this->map.insertKey("valueDataType", valueDataType);
	this->map.insertKey("qualifierType", qualifierType);
	this->map.insertKey("valueType", valueType);
	this->map.insertKey("semanticId", semanticId.getMap());
	this->map.insertKey("valueId", this->valueId.getMap());
};

Qualifier::Qualifier(const api::IQualifier & qualifier)
	: Qualifier( qualifier.getQualifierType(), qualifier.getValueType() )
{
	if (qualifier.getValueId() != nullptr)
		this->valueId = qualifier.getValueId()->getKeys();

	if (qualifier.getValueDataType() != nullptr)
		this->setValueDataType(*qualifier.getValueDataType());
};

const std::string Qualifier::getQualifierType() const
{
	return this->map.getProperty("qualifierType").Get<std::string&>();
};

const std::string Qualifier::getValueType() const
{
	return this->map.getProperty("valueType").Get<std::string&>();
};

const std::string * const Qualifier::getValueDataType() const
{
	if (!this->map.hasProperty("valueDataType"))
		return nullptr;

	return &this->map.getProperty("valueDataType").Get<std::string&>();
};

void Qualifier::setValueDataType(const std::string & valueDataType)
{
	this->map.insertKey("valueDataType", valueDataType);
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
