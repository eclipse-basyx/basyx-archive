/*
 * Qualifier.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/qualifier/qualifiable/Qualifier.h>
#include <BaSyx/submodel/map/reference/Reference.h>
#include <BaSyx/submodel/map/modeltype/ModelType.h>

namespace basyx {
namespace submodel {

Qualifier::Qualifier() :
  vab::ElementMap{ModelType{IQualifier::Path::Modeltype}}
{
  this->setQualifierType("Type not specified");
}

Qualifier::Qualifier(
	  const std::string & qualifierType, 
	  const basyx::object & qualifierValue, 
	  const IReference & valueId) 
  : vab::ElementMap{ModelType{IQualifier::Path::Modeltype}}
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
  this->map.insertKey(IQualifier::Path::QualifierType, qualifierType, true);
}

void Qualifier::setQualifierValue(const basyx::object & qualifierValue)
{
  this->map.insertKey(IQualifier::Path::QualifierValue, qualifierValue, true);
}

void Qualifier::setQualifierValueId(const IReference & valueId)
{
  Reference reference{valueId};
  this->insertMapElement(IQualifier::Path::QualifierValueID, reference);
}

}
}
