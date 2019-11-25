/*
 * IVABElementContainer.h
 *
 *      Author: wendel
 */

#ifndef AAS_VAB_IVABELEMENTCONTAINER_
#define AAS_VAB_IVABELEMENTCONTAINER_


#include "basyx/types.h"
#include "aas/submodelelement/ISubmodelElement.h"


namespace basyx {
namespace aas {
namespace vab {

class IVABElementContainer
{

public:
  virtual ~IVABElementContainer() = default;

  virtual void addSubModelElement(submodelelement::ISubmodelElement element) = 0;

  virtual basyx::object::object_map_t getDataElements() = 0;

  virtual basyx::object::object_map_t getOperations() = 0;

};

}
}
}

#endif