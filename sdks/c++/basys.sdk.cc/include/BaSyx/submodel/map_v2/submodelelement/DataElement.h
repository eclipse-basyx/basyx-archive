#ifndef BASYX_SUBMODEL_MAP_DATAELEMENT_H
#define BASYX_SUBMODEL_MAP_DATAELEMENT_H

#include <BaSyx/submodel/api_v2/submodelelement/IDataElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>

namespace basyx {
namespace submodel {
namespace map {

class DataElement
  : public virtual api::IDataElement
  , public SubmodelElement
{
public:
  DataElement(const std::string & idShort, ModelingKind kind = ModelingKind::Instance);
  inline DataElement(basyx::object obj) : SubmodelElement(obj){}

  virtual KeyElements getKeyElementType() const override { return KeyElements::DataElement; };
};

}
}
}
#endif //BASYX_SUBMODEL_MAP_DATAELEMENT_H
