/*
 * VABProxyMock.cpp
 *
 *      Author: wendel
 */

#include "vab/core/proxy/IVABElementProxy.h"

namespace basyx {
namespace vab {
namespace core {
namespace proxy {
namespace mockups {

class VABProxyMock : public IVABElementProxy
{
public:
  virtual basyx::any readElementValue(const VABPath & elementPath) override
  {
    this->readElementValue_calls++;

    return basyx::any("called with " + elementPath.toString());
  }

  virtual void updateElementValue(const VABPath & elementPath, const basyx::any & newValue) override
  {
    this->updateElementValue_calls++;

    this->updateElementCallValues.push_back(std::make_pair(elementPath.toString(), newValue));
  }

  virtual void createElement(const VABPath & elementPath, const basyx::any & newValue) override
  {
    this->createElement_calls++;
  }

  virtual void deleteElement(const VABPath & elementPath) override
  {
    this->deleteElement_calls++;
  }

  virtual void deleteElement(const VABPath & elementPath, const basyx::any & value) override
  {
    this->deleteElement2_calls++;
  }

  virtual basyx::any invoke(const VABPath & elementPath, basyx::objectCollection_t & parameter) override
  {
    this->invoke_calls++;
    return basyx::any();
  }

  int overallMockCalls()
  {
    return readElementValue_calls + updateElementValue_calls + createElement_calls + deleteElement_calls + deleteElement2_calls + invoke_calls;
  }

  int readElementValue_calls = 0;
  int updateElementValue_calls = 0;
  int createElement_calls = 0;
  int deleteElement_calls = 0;
  int deleteElement2_calls = 0;
  int invoke_calls = 0;

  std::vector<std::pair<std::string, basyx::any>> updateElementCallValues;
};

}
}
}
}
}
