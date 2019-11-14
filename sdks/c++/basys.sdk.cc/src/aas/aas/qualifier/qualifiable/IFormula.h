/*
 * IFormula.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IFormula_H_
#define BASYX_METAMODEL_IFormula_H_

#include "aas/reference/IReference.h"
#include "basyx/types.h"

#include <vector>


namespace basyx {
namespace aas {
namespace qualifier {
namespace qualifiable {

class IFormula
{
public:
  virtual ~IFormula() = default;

  virtual basyx::specificCollection_t<reference::IReference> getDependsOn() const = 0;
};

}
}
}
}

#endif

