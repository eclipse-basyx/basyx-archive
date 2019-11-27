/*
 * Qualifier.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_QUALIFIER_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_QUALIFIER_H_

#include "Constraint.h"
#include "submodel/api/qualifier/qualifiable/IQualifier.h"
#include "submodel/map/qualifier/HasSemantics.h"
#include "basyx/object.h"

namespace basyx {
namespace submodel {

class Qualifier : public Constraint, public IQualifier
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
  virtual std::shared_ptr<IReference> getSemanticId() const override;

  // not inherited
  void setQualifierType(const std::string & qualifierType);
  void setQualifierValue(const basyx::object & qualifierValue);
  void setQualifierValueId(const std::shared_ptr<IReference> & valueId);
  void setSemanticId(const std::shared_ptr<IReference> & semanticId);

private:
	HasSemantics semantics;
	std::string qualifierType;
	basyx::object qualifierValue;
	std::shared_ptr<IReference> qualifierValueId, semanticId;
};

}
}

#endif
