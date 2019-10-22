/*
 * ConnectedRelationshipElement.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_CONNECTEDRELATIONSHIPELEMENT_H_
#define AAS_BACKEND_SUBMODELELEMENT_CONNECTEDRELATIONSHIPELEMENT_H_

#include "basyx/types.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "aas/submodelelement/IRelationshipElement.h"
#include "backend/connected/aas/submodelelement/ConnectedSubmodelElement.h"

#include <string>

namespace basyx {
namespace aas {
namespace backend {
namespace connected {


class ConnectedRelationshipElement : public ConnectedSubmodelElement, submodelelement::IRelationshipElement
{
public:
  ConnectedRelationshipElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
  ~ConnectedRelationshipElement() = default;
  
  // Inherited via IRelationshipElement
  virtual void setFirst(const basyx::any & first) override;
  virtual basyx::any getFirst() const override;
  virtual void setSecond(const basyx::any & second) override;
  virtual basyx::any getSecond() const override;
};


}
}
}
}

#endif
