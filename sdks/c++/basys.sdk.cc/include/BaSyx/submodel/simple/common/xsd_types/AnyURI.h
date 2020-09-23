#ifndef BASYX_SIMPLE_SDK_ANYURI_H
#define BASYX_SIMPLE_SDK_ANYURI_H

#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {
namespace simple {

class AnyURI
{
private:
  std::string uri;

public:
  AnyURI(const std::string & uri);

  const std::string & getUri() const;
  void setURI(const std::string & uri);
};

}
}
}
#endif //BASYX_MAP_V2_SDK_ANYURI_H
