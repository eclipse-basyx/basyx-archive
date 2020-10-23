#ifndef BASYX_SUBMODEL_API_V2_DATASPECIFICATION_IEMBEDDEDDATASPECIFICATION_H
#define BASYX_SUBMODEL_API_V2_DATASPECIFICATION_IEMBEDDEDDATASPECIFICATION_H

#include <BaSyx/submodel/api_v2/reference/IReference.h>

namespace basyx {
namespace submodel {
namespace api {

class IEmbeddedDataSpecification
{
public:
  virtual ~IEmbeddedDataSpecification() = 0;

  virtual IReference * getDataSpecifaction() const = 0;
};

inline IEmbeddedDataSpecification::~IEmbeddedDataSpecification() = default;

}
}
}
#endif /* BASYX_SUBMODEL_API_V2_DATASPECIFICATION_IEMBEDDEDDATASPECIFICATION_H */
