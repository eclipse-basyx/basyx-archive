/*
 * ConnectedDataElement.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/connected/submodelelement/ConnectedDataElement.h>
#include <BaSyx/shared/types.h>

#include <memory>


namespace basyx {
namespace submodel {

ConnectedDataElement::ConnectedDataElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedSubmodelElement(proxy)
{}

}
}
