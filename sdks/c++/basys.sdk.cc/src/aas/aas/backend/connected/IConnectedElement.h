/*
 * IConnectedElement.h
 *
 *      Author: wendel
 */

#include "backends/provider/vab/VABElementProxy.h"

class IConnectedElement
{
public:
  virtual ~IConnectedElement() = default;

  virtual std::shared_ptr<VABElementProxy> getProxy() const = 0;
};