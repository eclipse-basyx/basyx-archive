/*
 * VABElementProxy.h
 *
 *      Author: wendel
 */

#ifndef BACKENDS_PROVIDER_VAB_ELEMENT_PROXY_H_
#define BACKENDS_PROVIDER_VAB_ELEMENT_PROXY_H_


#include "api/IModelProvider.h"
#include "VABPath.h"
#include "types/BaSysTypes.h"

#include <string>
#include <memory>

class VABElementProxy
{
public:
  VABElementProxy(std::string address, std::shared_ptr<IModelProvider> provider);
  ~VABElementProxy();

  basyx::any readElementValue(const VABPath & elementPath);

  template<typename T>
  T readElementValue(const VABPath& elementPath)
  {
    auto value = this->provider->getModelPropertyValue(this->get_ablsolute_path(elementPath));

    return value.Get<T>();
  }

  void updateElementValue(const VABPath & elementPath, const basyx::any & newValue);
  void createElement(const VABPath & elementPath, const basyx::any & newValue);
  void deleteElement(const VABPath & elementPath);
  void deleteElement(const VABPath & elementPath, const basyx::any & value);

  basyx::any invoke(const VABPath & elementPath, basyx::objectCollection_t & parameter);

  template<typename T>
  T invoke(const VABPath& elementPath, basyx::objectCollection_t& parameter)
  {
    auto return_value = this->provider->invokeOperation(this->get_ablsolute_path(elementPath), parameter);
    
    return return_value.Get<T>();
  }


private:
  VABPath get_ablsolute_path(const VABPath & elementPath);

private:
  VABPath address;
  std::shared_ptr<IModelProvider> provider;
};

#endif
