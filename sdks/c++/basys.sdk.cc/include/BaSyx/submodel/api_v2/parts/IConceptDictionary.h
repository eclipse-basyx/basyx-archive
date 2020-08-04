#ifndef BASYX_API_V2_SDK_ICONCEPTDICTIONARY_H
#define BASYX_API_V2_SDK_ICONCEPTDICTIONARY_H

#include <BaSyx/submodel/api_v2/parts/IConceptDescription.h>

namespace basyx {
namespace submodel {
namespace api {

class IConceptDictionary
    : public virtual IReferable
{
public:
  virtual ~IConceptDictionary() = 0;

  virtual const IElementContainer<IConceptDescription> & getConceptDescriptions() const = 0;
};

inline IConceptDictionary::~IConceptDictionary() = default;

}
}
}
#endif //BASYX_API_V2_SDK_ICONCEPTDICTIONARY_H
