/*
 * IFormula.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IFormula_H_
#define BASYX_METAMODEL_IFormula_H_

#include "submodel/api/reference/IReference.h"
#include "basyx/types.h"
#include "IConstraint.h"

#include <vector>


namespace basyx {
namespace submodel {

class IFormula : public IConstraint
{
public:
  struct Path {
    static constexpr char Dependson[] = "dependsOn";
    static constexpr char Modeltype[] = "Formula";
  };

public:
  virtual ~IFormula() = default;

  virtual basyx::specificCollection_t<IReference> getDependsOn() const = 0;
};

}
}

#endif

