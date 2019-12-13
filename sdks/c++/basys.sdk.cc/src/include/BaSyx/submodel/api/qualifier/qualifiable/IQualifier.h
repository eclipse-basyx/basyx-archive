/*
 * IQualifier.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IQualifier_H_
#define BASYX_METAMODEL_IQualifier_H_

#include <BaSyx/submodel/api/reference/IReference.h>
#include <BaSyx/submodel/api/qualifier/IHasSemantics.h>
#include <BaSyx/shared/types.h>
#include <BaSyx/shared/object.h>
#include <BaSyx/submodel/api/qualifier/qualifiable/IConstraint.h>

#include <string>

namespace basyx {
namespace submodel {

class IQualifier 
  : public IHasSemantics
  , public virtual IConstraint
{
public:
  struct Path {
    static constexpr char Qualifier[] = "qualifier";
    static constexpr char QualifierType[] = "qualifierType";
    static constexpr char QualifierValue[] = "qualifierValue";
    static constexpr char QualifierValueID[] = "qualifierValueId";
    static constexpr char Modeltype[] = "Qualifier";
  };

public:
  virtual ~IQualifier() = default;

  virtual std::string getQualifierType() const = 0;
  virtual basyx::object getQualifierValue() const = 0;
  virtual std::shared_ptr<IReference> getQualifierValueId() const = 0;
};

}
}

#endif

