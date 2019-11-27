/*
 * ConnectedDataElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedDataElement.h"
#include "basyx/types.h"

#include <memory>


namespace basyx {
namespace aas {
namespace backend {

ConnectedDataElement::ConnectedDataElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedSubmodelElement(proxy)
{}

}
}
}
