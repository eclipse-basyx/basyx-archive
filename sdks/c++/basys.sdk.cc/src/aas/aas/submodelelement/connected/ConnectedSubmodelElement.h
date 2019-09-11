/*
 * ConnectedSubmodelElement.h
 *
 *      Author: wendel
 */

#include "aas/submodelelement/ISubmodelElement.h"
#include "aas/backend/connected/ConnectedElement.h"
#include "vab/core/proxy/VABElementProxy.h"

namespace basyx {
namespace aas {
namespace submodelelement {
namespace connected {

class ConnectedSubmodelElement : public backend::ConnectedElement, public ISubmodelElement
{
public:
  ConnectedSubmodelElement(std::shared_ptr<vab::core::proxy::VABElementProxy> proxy);
};

}
}
}
}