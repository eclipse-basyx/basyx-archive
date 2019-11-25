/*
 * VABProxyMock.cpp
 *
 *      Author: wendel
 */

#include "vab/core/proxy/IVABElementProxy.h"
#include <string>

namespace basyx {
namespace vab {
namespace core {
namespace proxy {
namespace mockups {

enum ProxyType
{
  Map,
  Default,
  Collection,
  ByteArray,
  Bool
};

template<ProxyType t>
class VABProxyMockUp : public IVABElementProxy
{
public:

  virtual basyx::object readElementValue(const VABPath & elementPath) override
  {
    this->readElementValue_calls++;

    this->getElementCallValues.push_back(elementPath);

    if ( t == ProxyType::Default )
      return basyx::object("called with " + elementPath.toString());
    if ( t == ProxyType::Map )
      return basyx::object(this->map);
    if ( t == ProxyType::Collection )
      return basyx::object(this->collection);
    if ( t == ProxyType::ByteArray )
      return "";
    if ( t == ProxyType::Bool )
      return basyx::object(true);
  }


  virtual void updateElementValue(const VABPath & elementPath, const basyx::object & newValue) override
  {
    this->updateElementValue_calls++;

    this->updateElementCallValues.push_back(std::make_pair(elementPath.toString(), newValue));
  }

  virtual void createElement(const VABPath & elementPath, const basyx::object & newValue) override
  {
    this->createElement_calls++;

    this->createElementCallValues.push_back(std::make_pair(elementPath.toString(), newValue));
  }

  virtual void deleteElement(const VABPath & elementPath) override
  {
    this->deleteElement_calls++;

    this->removeElementCallValues.push_back(elementPath.toString());
  }

  virtual void deleteElement(const VABPath & elementPath, basyx::object & value) override
  {
    this->deleteElement2_calls++;

    this->deleteElementCallValues.push_back(std::make_pair(elementPath.toString(), value));
  }

  virtual basyx::object invoke(const VABPath & elementPath, basyx::object & parameter) override
  {
    this->invoke_calls++;
    this->invokeCallParameter = parameter;
    return basyx::object("called with " + elementPath.toString());
  }

  virtual std::shared_ptr<IVABElementProxy> getDeepProxy(const VABPath & elementPath) override
  {
    getDeepProxyCalls++;
    return std::shared_ptr<IVABElementProxy>();
  }

  virtual VABPath getAddressPath() const override
  {
    VABProxyMockUp* ptr = const_cast<VABProxyMockUp*> (this);
    ptr->getAddressPathCalls++;
    return VABPath("");
  }

  int overallMockCalls()
  {
    return readElementValue_calls + updateElementValue_calls + createElement_calls + deleteElement_calls + deleteElement2_calls + invoke_calls + getDeepProxyCalls + getAddressPathCalls;
  }

  int readElementValue_calls = 0;
  int updateElementValue_calls = 0;
  int createElement_calls = 0;
  int deleteElement_calls = 0;
  int deleteElement2_calls = 0;
  int invoke_calls = 0;
  int getDeepProxyCalls = 0;
  int getAddressPathCalls = 0;

  std::vector<std::pair<std::string, basyx::object>> updateElementCallValues;
  std::vector<std::pair<std::string, basyx::object>> createElementCallValues;
  std::vector<std::pair<std::string, basyx::object>> deleteElementCallValues;
  std::vector<std::string> getElementCallValues;
  std::vector<std::string> removeElementCallValues;
  basyx::object::object_map_t map;
  basyx::object::object_list_t collection;
  basyx::object invokeCallParameter;

};

using VABProxyMock = VABProxyMockUp<ProxyType::Default>;
using VABProxyMockCollection = VABProxyMockUp<ProxyType::Collection>;
using VABProxyMockByteArray = VABProxyMockUp<ProxyType::ByteArray>;
using VABProxyMockMap = VABProxyMockUp<ProxyType::Map>;
using VABProxyMockBool = VABProxyMockUp<ProxyType::Bool>;

}
}
}
}
}
