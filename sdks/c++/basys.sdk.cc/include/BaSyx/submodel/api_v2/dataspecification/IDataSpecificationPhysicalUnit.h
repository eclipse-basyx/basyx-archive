#ifndef BASYX_API_V2_SDK_IDATASPECIFICATIONPHYSICALUNIT_H
#define BASYX_API_V2_SDK_IDATASPECIFICATIONPHYSICALUNIT_H

#include <string>

#include <BaSyx/submodel/api_v2/common/ILangStringSet.h>

namespace basyx {
namespace submodel {
namespace api {

class IDataSpecificationPhysicalUnit
{
public:
  virtual ~IDataSpecificationPhysicalUnit() = 0;

  virtual std::string & getUnitName() const = 0;
  virtual void setUnitName(const std::string & unitName) = 0;

  virtual std::string & getUnitSymbol() const = 0;
  virtual void setUnitSymbol(const std::string & unitName) = 0;

  virtual ILangStringSet & getDefinition() const = 0;
  virtual void setDefinition(const ILangStringSet & unitName) = 0;

  virtual std::string * getSiNotation() const = 0;
  virtual std::string * getSiName() const = 0;
  virtual std::string * getDinNotation() const = 0;
  virtual std::string * getEceName() const = 0;
  virtual std::string * getEceCode() const = 0;
  virtual std::string * getNistName() const = 0;
  virtual std::string * getSourceOfDefinition() const = 0;
  virtual std::string * getConversionFactor() const = 0;
  virtual std::string * getRegistrationAuthorityId() const = 0;
  virtual std::string * getSupplier() const = 0;

};

inline IDataSpecificationPhysicalUnit::~IDataSpecificationPhysicalUnit() = default;

}
}
}
#endif //BASYX_API_V2_SDK_IDATASPECIFICATIONPHYSICALUNIT_H
