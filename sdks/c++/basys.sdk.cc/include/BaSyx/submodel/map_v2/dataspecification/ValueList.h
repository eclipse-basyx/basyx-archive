#ifndef BASYX_C_SDK_VALUELIST_H
#define BASYX_C_SDK_VALUELIST_H

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
  ValueList();
  explicit ValueList(const std::vector<simple::ValueReferencePair> & list);

  void addValueReferencePair(const simple::ValueReferencePair & valueRefPair) override;
  std::vector<simple::ValueReferencePair> getValueReferencePairs() override;
};

}
}
}

#endif //BASYX_C_SDK_VALUELIST_H
