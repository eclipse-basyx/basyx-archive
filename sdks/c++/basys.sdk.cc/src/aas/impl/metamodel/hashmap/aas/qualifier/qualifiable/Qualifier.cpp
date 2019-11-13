/*
 * Qualifier.cpp
 *
 *      Author: wendel
 */

#include "Qualifier.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {
namespace qualifiable {

Qualifier::Qualifier()
{}

Qualifier::Qualifier(const std::string & qualifierType, const basyx::any & qualifierValue, const std::shared_ptr<aas::reference::IReference> & valueId) :
  qualifierType {qualifierType},
  qualifierValue {qualifierValue},
  qualifierValueId {valueId}
{}

std::string Qualifier::getQualifierType() const
{
  return this->qualifierType;
}

basyx::any Qualifier::getQualifierValue() const
{
  return this->qualifierValue;
}

std::shared_ptr<aas::reference::IReference> Qualifier::getQualifierValueId() const
{
  return this->qualifierValueId;
}

std::shared_ptr<aas::reference::IReference> Qualifier::getSemanticId() const
{
  return this->semanticId;
}

void Qualifier::setQualifierType(const std::string & qualifierType)
{
  this->qualifierType = qualifierType;
}

void Qualifier::setQualifierValue(const basyx::any & qualifierValue)
{
  this->qualifierValue = qualifierValue;
}

void Qualifier::setQualifierValueId(const std::shared_ptr<aas::reference::IReference> & valueId)
{
  this->qualifierValueId = valueId;
}

void Qualifier::setSemanticId(const std::shared_ptr<aas::reference::IReference>& semanticId)
{
  this->semanticId = semanticId;
}


}
}
}
}
}
}
