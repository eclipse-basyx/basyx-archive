#include <BaSyx/submodel/simple/common/xsd_types/AnyURI.h>

namespace basyx {
namespace submodel {
namespace simple {

AnyURI::AnyURI(const std::string & uri)
  : uri{uri}
{}

const std::string &AnyURI::getUri() const
{
  return this->uri;
}

void AnyURI::setURI(const std::string &uri)
{
  this->uri = uri;
}


}
}
}
