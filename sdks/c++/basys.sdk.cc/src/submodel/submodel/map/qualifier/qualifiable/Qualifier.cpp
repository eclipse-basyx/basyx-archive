/*
 * Qualifier.cpp
 *
 *      Author: wendel
 */

#include "Qualifier.h"

namespace basyx {
namespace submodel {

Qualifier::Qualifier()
{}

Qualifier::Qualifier(
	const std::string & qualifierType, 
	const basyx::object & qualifierValue, 
	const std::shared_ptr<IReference> & valueId) 
	
  : qualifierType {qualifierType}
  , qualifierValue {qualifierValue}
  , qualifierValueId {valueId}
{}

std::string Qualifier::getQualifierType() const
{
  return this->qualifierType;
}

basyx::object Qualifier::getQualifierValue() const
{
  return this->qualifierValue;
}

std::shared_ptr<IReference> Qualifier::getQualifierValueId() const
{
  return this->qualifierValueId;
}

std::shared_ptr<IReference> Qualifier::getSemanticId() const
{
  return this->semanticId;
}

void Qualifier::setQualifierType(const std::string & qualifierType)
{
  this->qualifierType = qualifierType;
}

void Qualifier::setQualifierValue(const basyx::object & qualifierValue)
{
  this->qualifierValue = qualifierValue;
}

void Qualifier::setQualifierValueId(const std::shared_ptr<IReference> & valueId)
{
  this->qualifierValueId = valueId;
}

void Qualifier::setSemanticId(const std::shared_ptr<IReference>& semanticId)
{
  this->semanticId = semanticId;
}


}
}
