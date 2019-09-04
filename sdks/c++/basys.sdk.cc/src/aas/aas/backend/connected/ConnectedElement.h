/*
 * ConnectedElement.h
 *
 *      Author: wendel
 */

#include "IConnectedElement.h"

#include <string>
#include <memory>

class ConnectedElement : public IConnectedElement
{
public:
  ConnectedElement(const std::string & path, const std::shared_ptr<VABElementProxy> & proxy);

  virtual std::shared_ptr<VABElementProxy> getProxy() const override;

private:
  std::shared_ptr<VABElementProxy> proxy;
  std::string path;
};