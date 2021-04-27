#ifndef BASYX_SUBMODEL_SIMPLE_RANGE_H
#define BASYX_SUBMODEL_SIMPLE_RANGE_H

#include <BaSyx/submodel/api_v2/submodelelement/IRange.h>
#include <BaSyx/submodel/simple/submodelelement/DataElement.h>

namespace basyx {
namespace submodel {
namespace simple {

template<typename T>
class Range
  : public virtual api::IRange<T>
  , public DataElement
{
public:
  using ValueDataType = typename api::IRange<T>::ValueDataType;
private:
  DataTypeDef dataTypeDef;
  std::unique_ptr<T> min, max;

public:
  ~Range() override = default;

  Range(const std::string & idShort, ModelingKind kind = ModelingKind::Instance)
    : DataElement(idShort, kind)
    , dataTypeDef(ValueDataType::getDataTypeDef())
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
#endif //BASYX_SUBMODEL_RANGE_H
