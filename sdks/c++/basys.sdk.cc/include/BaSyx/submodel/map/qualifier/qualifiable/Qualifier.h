/*
 * Qualifier.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_QUALIFIER_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_QUALIFIER_H_

#include <BaSyx/submodel/map/qualifier/qualifiable/Constraint.h>
#include <BaSyx/submodel/api/qualifier/qualifiable/IQualifier.h>
#include <BaSyx/submodel/map/qualifier/HasSemantics.h>
#include <BaSyx/shared/object.h>

namespace basyx {
namespace submodel {

class Qualifier 
  : public Constraint
  , public IQualifier
  , public HasSemantics
  , public virtual basyx::vab::ElementMap
{
public:
  ~Qualifier() = default;

  // constructors
  Qualifier();
  Qualifier(
	  const std::string & qualifierType, 
	  const basyx::object & qualifierValue, 
	  const std::shared_ptr<IReference> & valueId);

  // Inherited via IQualifier
  virtual std::string getQualifierType() const override;
  virtual basyx::object getQualifierValue() const override;
  virtual std::shared_ptr<IReference> getQualifierValueId() const override;

  // not inherited
  void setQualifierType(const std::string & qualifierType);
  void setQualifierValue(const basyx::object & qualifierValue);
  void setQualifierValueId(const std::shared_ptr<IReference> & valueId);
};

}
}

#endif
