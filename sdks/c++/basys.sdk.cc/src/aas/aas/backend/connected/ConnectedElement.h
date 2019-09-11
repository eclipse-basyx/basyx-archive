/*
 * ConnectedElement.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_CONNECTEDELEMENT_H_
#define BASYX_METAMODEL_CONNECTEDELEMENT_H_


#include "vab/core/proxy/VABElementProxy.h"

#include "basyx/types.h"

#include <string>
#include <memory>

namespace basyx {
namespace aas {
namespace backend {

class ConnectedElement
{
public:
  ConnectedElement(const std::shared_ptr<vab::core::proxy::VABElementProxy> & proxy);

  virtual std::shared_ptr<vab::core::proxy::VABElementProxy> getProxy() const;

  basyx::any getLocalValue(const std::string & path) const;
  void setLocalValue(const std::string & path, const basyx::any value);
  void updateLocalValue(const std::string & path, const basyx::any value);

private:
  std::shared_ptr<vab::core::proxy::VABElementProxy> proxy;
  basyx::objectMap_t local_map;
};

}
}
}

#endif // !BASYX_METAMODEL_CONNECTEDELEMENT_H_
