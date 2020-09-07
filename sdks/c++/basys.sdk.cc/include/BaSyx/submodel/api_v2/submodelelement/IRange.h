#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IRANGE_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IRANGE_H

#include <string>

#include <BaSyx/submodel/api_v2/submodelelement/IDataElement.h>

/**
 * Mandatory members: valueType
 */
namespace basyx {
namespace submodel {

//todo clearify xsd type
//using ValueDataType = xsd_type;
using ValueDataType = std::string;
using DataTypeDef = std::string;

namespace api {

class IRange
  : public virtual IDataElement
{
  virtual ~IRange() = 0;

  virtual DataTypeDef const & getDataTypeDef() const = 0;
  virtual void setDataTypeDef(const DataTypeDef & dataTypeDef) = 0;

  virtual ValueDataType getMin() = 0;
  virtual ValueDataType getMax() = 0;

  virtual KeyElements getKeyElementType() const override { return KeyElements::Range; };
};

inline IRange::~IRange() = default;

}
}
}
#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IRANGE_H */
