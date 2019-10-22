/*
 * IVABElementContainer.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_HASHMAP_IVABELEMENTCONTAINER_H_
#define AAS_IMPL_HASHMAP_IVABELEMENTCONTAINER_H_

#include "basyx/types.h"

namespace basyx {
namespace aas {
namespace impl {

class IVABElementContainer
{
public:
  ~IVABElementContainer() = default;

  virtual void addSubModelElement(const basyx::any & element) = 0;
  virtual basyx::objectMap_t getDataElements() const = 0;
  virtual basyx::objectMap_t getOperations() const = 0;
};

}
}
}

#endif
