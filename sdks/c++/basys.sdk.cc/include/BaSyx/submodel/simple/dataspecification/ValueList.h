#ifndef BASYX_C_SDK_SIMPLE_VALUELIST_H
#define BASYX_C_SDK_SIMPLE_VALUELIST_H

#include <BaSyx/submodel/api_v2/dataspecification/IValueList.h>

namespace basyx {
namespace submodel {
namespace simple {

class ValueList
    : public api::IValueList
{
public:
  ValueList() = default;
  explicit ValueList(const std::vector<simple::ValueReferencePair> & list);

  void addValueReferencePair(const simple::ValueReferencePair & valueRefPair) override;
  std::vector<simple::ValueReferencePair> getValueReferencePairs() override;

private:
  std::vector<simple::ValueReferencePair> list;
};

}
}
}

#endif //BASYX_C_SDK_VALUELIST_H
