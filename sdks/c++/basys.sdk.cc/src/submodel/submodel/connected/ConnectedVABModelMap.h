/*
 * ConnectedVABModelMap.h
 *
 *      Author: wendel
 */

#ifndef CONNECTEDVABMODELMAP_H_
#define CONNECTEDVABMODELMAP_H_

#include "ConnectedElement.h"

namespace basyx {
namespace aas {
namespace backend {

class ConnectedVABModelMap : public ConnectedElement
{
public:
  ConnectedVABModelMap(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
  ~ConnectedVABModelMap() = default;
};

}
}
}

#endif
