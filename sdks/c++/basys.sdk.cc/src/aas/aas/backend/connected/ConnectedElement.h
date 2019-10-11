/*
 * ConnectedElement.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_CONNECTEDELEMENT_H_
#define BASYX_METAMODEL_CONNECTEDELEMENT_H_


#include "vab/core/proxy/VABElementProxy.h"
#include "aas/submodelelement/IElement.h"

#include "basyx/types.h"

#include <string>
#include <memory>

namespace basyx {
namespace aas {
namespace backend {

class ConnectedElement : IElement
{
public:
  ConnectedElement(const std::shared_ptr<vab::core::proxy::IVABElementProxy> & proxy);
  ConnectedElement(const std::shared_ptr<vab::core::proxy::IVABElementProxy> & proxy, std::shared_ptr<basyx::objectMap_t> & local_values);

  virtual std::shared_ptr<vab::core::proxy::IVABElementProxy> getProxy() const;

  basyx::any getLocalValue(const std::string & path) const;
  void setLocalValue(const std::string & path, const basyx::any value);
  void updateLocalValue(const std::string & path, const basyx::any value);

  // Inherited via IElement
  virtual void setId(const std::string & id) override;
  virtual std::string getId() const override;


private:
  std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy;
  std::shared_ptr<basyx::objectMap_t> local_map;

protected:
  std::string getProxyValue(const std::string & path) const;
  void setProxyValue(const std::string & path, const basyx::any value) const;

};

}
}
}

#endif // !BASYX_METAMODEL_CONNECTEDELEMENT_H_
