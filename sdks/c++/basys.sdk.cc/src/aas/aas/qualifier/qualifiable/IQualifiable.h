/*
 * IQualifiable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IQualifiable_H_
#define BASYX_METAMODEL_IQualifiable_H_


#include "IConstraint.h"
#include "basyx/types.h"

#include <vector>
#include <memory>

namespace basyx {
namespace aas {
namespace qualifier {
namespace qualifiable {

class IQualifiable
{
public:
  virtual ~IQualifiable() = default;

  virtual basyx::specificCollection_t<IConstraint> getQualifier() const = 0;
};

}
}
}
}

#endif

