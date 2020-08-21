#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IDATAELEMENT_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IDATAELEMENT_H


#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>

namespace basyx {
namespace submodel {
namespace api {

class IDataElement : public virtual ISubmodelElement
{
public:
  virtual ~IDataElement() = 0;

  virtual KeyElements getKeyElementType() const override { return KeyElements::DataElement; };
};

inline IDataElement::~IDataElement() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IDATAELEMENT_H */
