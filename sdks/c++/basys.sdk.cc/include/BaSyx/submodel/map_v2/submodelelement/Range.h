#ifndef BASYX_SUBMODEL_MAP_RANGE_H
#define BASYX_SUBMODEL_MAP_RANGE_H

#include <BaSyx/submodel/api_v2/submodelelement/IRange.h>
#include <BaSyx/submodel/map_v2/submodelelement/DataElement.h>

namespace basyx {
namespace submodel {
namespace map {

template<typename T>
class Range 
  : public virtual api::IRange<T>
  , public DataElement
{
public:
  using ValueDataType = typename api::IRange<T>::ValueDataType;

private:
  std::unique_ptr<T> min, max;

public:
  Range(const std::string & idShort, ModelingKind kind = ModelingKind::Instance)
    : DataElement(idShort, kind)
  {
    this->map.template insertKey("dataTypeDef", ValueDataType::getDataTypeDef());
  }

  const DataTypeDef & getDataTypeDef() const override
  {
    return *this->map.getProperty("dataTypeDef").template GetPtr<DataTypeDef>();
  }

  void setMin(std::unique_ptr<T> min) override
  {
    this->map.insertKey("min", ValueDataType::getXSDRepresentation(*min));
    this->min = std::move(min);
  }

  const T * const getMin() override
  {
    return this->min.get();
  }

  void setMax(std::unique_ptr<T> max) override
  {
    this->map.insertKey("max", ValueDataType::getXSDRepresentation(*max));
    this->max = std::move(max);
  }

  const T * const getMax() override
  {
    return this->max.get();
  }

  KeyElements getKeyElementType() const override { return KeyElements::Range; };

  ModelTypes GetModelType() const override
  {
    return ModelTypes::Range;
  }
};

}
}
}
#endif //BASYX_SUBMODEL_MAP_RANGE_H
