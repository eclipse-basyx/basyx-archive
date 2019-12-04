/*
 * Qualifier.cpp
 *
 *      Author: wendel
 */

#include "Qualifier.h"
#include "submodel/map/reference/Reference.h"

namespace basyx {
namespace submodel {

Qualifier::Qualifier()
{}

Qualifier::Qualifier(
	const std::string & qualifierType, 
	const basyx::object & qualifierValue, 
	const std::shared_ptr<IReference> & valueId) : 
    vab::ElementMap{}
{
  this->setQualifierType(qualifierType);
  this->setQualifierValue(qualifierValue);
  this->setQualifierValueId(valueId);
}

std::string Qualifier::getQualifierType() const
{
  return this->map.getProperty(IQualifier::Path::QualifierType).GetStringContent();
}

basyx::object Qualifier::getQualifierValue() const
{
  return this->map.getProperty(IQualifier::Path::QualifierValue);
}

std::shared_ptr<IReference> Qualifier::getQualifierValueId() const
{
  return std::make_shared<Reference>(this->map.getProperty(IQualifier::Path::QualifierValueID));
}

void Qualifier::setQualifierType(const std::string & qualifierType)
{
  this->map.insertKey(IQualifier::Path::QualifierType, qualifierType);
}

void Qualifier::setQualifierValue(const basyx::object & qualifierValue)
{
  this->map.insertKey(IQualifier::Path::QualifierValue, qualifierValue);
}

void Qualifier::setQualifierValueId(const std::shared_ptr<IReference> & valueId)
{
  Reference reference{valueId};
  this->insertMapElement(IQualifier::Path::QualifierValueID, reference);
}

}
}
