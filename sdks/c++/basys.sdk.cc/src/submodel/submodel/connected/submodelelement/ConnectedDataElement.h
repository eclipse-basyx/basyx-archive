/*
 * ConnectedDataElement.h
 *
 *      Author: wendel
 */


#ifndef BASYX_METAMODEL_CONNECTEDDATAELEMENT_H_
#define BASYX_METAMODEL_CONNECTEDDATAELEMENT_H_

#include "vab/core/proxy/IVABElementProxy.h"

#include "submodel/api/reference/IReference.h"
#include "submodel/api/qualifier/qualifiable/IConstraint.h"
#include "submodel/connected/submodelelement/ConnectedSubmodelElement.h"
#include "submodel/api/submodelelement/IDataElement.h"

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
