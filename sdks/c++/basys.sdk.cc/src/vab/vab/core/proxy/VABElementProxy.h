/*
 * VABElementProxy.h
 *
 *      Author: wendel
 */

#ifndef VAB_CORE_PROXY_VABELEMENTPROXY_H
#define VAB_CORE_PROXY_VABELEMENTPROXY_H

#include "IVABElementProxy.h"

#include "vab/core/util/VABPath.h"
#include "vab/core/IModelProvider.h"
#include "basyx/types.h"

#include <memory>
#include <string>

namespace basyx {
namespace vab {
namespace core {
namespace proxy {

class VABElementProxy : public IVABElementProxy {
public:
  VABElementProxy(std::string address, std::shared_ptr<IModelProvider> provider);
  ~VABElementProxy();

  virtual basyx::any readElementValue(const VABPath& elementPath) override;
  virtual void updateElementValue(const VABPath& elementPath, const basyx::any& newValue) override;
  virtual void createElement(const VABPath& elementPath, const basyx::any& newValue) override;
  virtual void deleteElement(const VABPath& elementPath) override;
  virtual void deleteElement(const VABPath& elementPath, const basyx::any& value) override;
  virtual basyx::any invoke(const VABPath& elementPath, basyx::objectCollection_t& parameter) override;

private:
  VABPath get_ablsolute_path(const VABPath& elementPath);

private:
  VABPath address;
  std::shared_ptr<IModelProvider> provider;
};

}
}
}
}

#endif /* VAB_CORE_PROXY_VABELEMENTPROXY_H */
