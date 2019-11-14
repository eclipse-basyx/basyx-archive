/*
 * Qualifier.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_QUALIFIER_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_QUALIFIER_H_

#include "Constraint.h"
#include "aas/qualifier/qualifiable/IQualifier.h"
#include "impl/metamodel/hashmap/aas/qualifier/HasSemantics.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {
namespace qualifiable {

class Qualifier : public Constraint, public aas::qualifier::qualifiable::IQualifier
{
public:
  ~Qualifier() = default;

  // constructors
  Qualifier();
  Qualifier(const std::string & qualifierType, const basyx::any & qualifierValue, const std::shared_ptr<aas::reference::IReference> & valueId);

  // Inherited via IQualifier
  virtual std::string getQualifierType() const override;
  virtual basyx::any getQualifierValue() const override;
  virtual std::shared_ptr<aas::reference::IReference> getQualifierValueId() const override;
  virtual std::shared_ptr<aas::reference::IReference> getSemanticId() const override;

  // not inherited
  void setQualifierType(const std::string & qualifierType);
  void setQualifierValue(const basyx::any & qualifierValue);
  void setQualifierValueId(const std::shared_ptr<aas::reference::IReference> & valueId);
  void setSemanticId(const std::shared_ptr<aas::reference::IReference> & semanticId);

private:
  HasSemantics semantics;
  std::string qualifierType;
  basyx::any qualifierValue;
  std::shared_ptr<aas::reference::IReference> qualifierValueId, semanticId;
};

}
}
}
}
}
}

#endif
