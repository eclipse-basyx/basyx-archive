/*
 * IConnectedElement.h
 *
 *      Author: wendel
 */

#include "vab/core/proxy/VABElementProxy.h"

namespace basyx {
namespace aas {
namespace backend {
class IConnectedElement
{
public:
  virtual ~IConnectedElement() = default;

  virtual std::shared_ptr<vab::core::proxy::VABElementProxy> getProxy() const = 0;
};

}
}
}