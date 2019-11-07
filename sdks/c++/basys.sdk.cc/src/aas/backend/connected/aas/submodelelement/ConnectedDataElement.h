/*
 * ConnectedDataElement.h
 *
 *      Author: wendel
 */


#ifndef BASYX_METAMODEL_CONNECTEDDATAELEMENT_H_
#define BASYX_METAMODEL_CONNECTEDDATAELEMENT_H_

#include "vab/core/proxy/IVABElementProxy.h"
#include "aas/reference/IReference.h"
#include "aas/qualifier/qualifiable/IConstraint.h"
#include "backend/connected/aas/submodelelement/ConnectedSubmodelElement.h"
#include "aas/submodelelement/IDataElement.h"

namespace basyx {
namespace aas {
namespace backend {

class ConnectedDataElement : public connected::ConnectedSubmodelElement, public submodelelement::IDataElement
{

public:
  ConnectedDataElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
  ~ConnectedDataElement() = default;

};

}
}
}

#endif
