/*
 * ConnectedVABModelMap.cpp
 *
 *      Author: wendel
 */

#include "ConnectedVABModelMap.h"

namespace basyx {
namespace submodel {
namespace backend {

ConnectedVABModelMap::ConnectedVABModelMap(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedElement(proxy)
{}

}
}
}