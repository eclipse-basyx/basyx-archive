#ifndef BASYX_SUBMODEL_MAP_V2_DATASPECIFICATION_VALUELIST_H
#define BASYX_SUBMODEL_MAP_V2_DATASPECIFICATION_VALUELIST_H

#include <BaSyx/submodel/api_v2/dataspecification/IValueList.h>
#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {
namespace map {

class ValueList
  : public api::IValueList
  , public vab::ElementMap
{
public:
  struct Path {
    static constexpr char Value[] = "value";
    static constexpr char ValueId[] = "valueId";
  };

public:
  ValueList();
  explicit ValueList(const std::vector<simple::ValueReferencePair> & list);

  void addValueReferencePair(const simple::ValueReferencePair & valueRefPair) override;
  std::vector<simple::ValueReferencePair> getValueReferencePairs() override;
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_DATASPECIFICATION_VALUELIST_H */
