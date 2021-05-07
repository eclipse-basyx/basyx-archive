#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IRANGE_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IRANGE_H

#include <string>

#include <BaSyx/submodel/api_v2/submodelelement/IDataElement.h>
#include <BaSyx/submodel/api_v2/submodelelement/property/XSDAnySimpleType.h>

/**
 * Mandatory members: valueType
 */
namespace basyx {
namespace submodel {

using DataTypeDef = std::string;

namespace api {

template<typename T>
class IRange
  : public virtual IDataElement
{
public:
  using ValueDataType = xsd_types::xsd_type<T>;
  virtual ~IRange() = 0;

  virtual DataTypeDef const & getDataTypeDef() const = 0;

  virtual const T * const getMin() = 0;
  virtual void setMin(std::unique_ptr<T>) = 0;

  virtual const T * const getMax() = 0;
  virtual void setMax(std::unique_ptr<T>) = 0;

  virtual KeyElements getKeyElementType() const override { return KeyElements::Range; };
};

template<typename T>
inline IRange<T>::~IRange() = default;

}
}
}
#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IRANGE_H */
