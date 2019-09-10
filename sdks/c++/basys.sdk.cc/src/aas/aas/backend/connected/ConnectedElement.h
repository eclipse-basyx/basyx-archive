/*
 * ConnectedElement.h
 *
 *      Author: wendel
 */

#include "IConnectedElement.h"

#include <string>
#include <memory>

namespace basyx {
namespace aas {
namespace backend {

class ConnectedElement : public IConnectedElement
{
public:
  ConnectedElement(const std::string & path, const std::shared_ptr<vab::core::proxy::VABElementProxy> & proxy);

  virtual std::shared_ptr<vab::core::proxy::VABElementProxy> getProxy() const override;

private:
  std::shared_ptr<vab::core::proxy::VABElementProxy> proxy;
  std::string path;
};

}
}
}
