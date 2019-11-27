/*
 * IFormula.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IFormula_H_
#define BASYX_METAMODEL_IFormula_H_

#include "submodel/api/reference/IReference.h"
#include "basyx/types.h"

#include <vector>


namespace basyx {
namespace submodel {

class IFormula
{
public:
  virtual ~IFormula() = default;

  virtual basyx::specificCollection_t<IReference> getDependsOn() const = 0;
};

}
}

#endif

