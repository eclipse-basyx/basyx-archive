#ifndef BASYX_SUBMODEL_MAP_RANGE_H
#define BASYX_SUBMODEL_MAP_RANGE_H

#include <BaSyx/submodel/api_v2/submodelelement/IRange.h>
#include <BaSyx/submodel/map_v2/submodelelement/DataElement.h>

namespace basyx {
namespace submodel {
namespace map {

template<typename T>
class Range 
  : public api::IRange<T>
  , public DataElement
{
public:
  struct Path {
    static constexpr char X[] = "kind";
  };

  Range(const std::string & idShort, ModelingKind kind = ModelingKind::Instance)
    : DataElement(idShort, kind)
  {}

  const DataTypeDef & getDataTypeDef() const override
  {
    return this->dataTypeDef;
  }

  void setMin(std::unique_ptr<T> min) override
  {
    this->min = std::move(min);
  }

  const T * const getMin() override
  {
    return this->min.get();
  }

  void setMax(std::unique_ptr<T> max) override
  {
    this->max = std::move(max);
  }

  const T * const getMax() override
  {
    return this->max.get();
  }
};

}
}
}
#endif //BASYX_SUBMODEL_MAP_RANGE_H
