/*
 * ConnectedDataElement.h
 *
 *      Author: wendel
 */


#ifndef BASYX_METAMODEL_CONNECTEDDATAELEMENT_H_
#define BASYX_METAMODEL_CONNECTEDDATAELEMENT_H_

#include <BaSyx/vab/core/proxy/IVABElementProxy.h>

#include <BaSyx/submodel/api/reference/IReference.h>
#include <BaSyx/submodel/api/qualifier/qualifiable/IConstraint.h>
#include <BaSyx/submodel/connected/submodelelement/ConnectedSubmodelElement.h>
#include <BaSyx/submodel/api/submodelelement/IDataElement.h>

namespace basyx {
namespace submodel {

class ConnectedDataElement : public ConnectedSubmodelElement, public IDataElement
{

public:
  ConnectedDataElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
  ~ConnectedDataElement() = default;

};

}
}

#endif
