#ifndef BASYX_API_V2_SDK_IADMINISTRATIVEINFORMATION_H
#define BASYX_API_V2_SDK_IADMINISTRATIVEINFORMATION_H

#include <BaSyx/submodel/api_v2/qualifier/IHasDataSpecification.h>

#include <string>

namespace basyx {
namespace submodel {
namespace api {

class IAdministrativeInformation
  : public virtual IHasDataSpecification
{
public:
  virtual ~IAdministrativeInformation() = 0;

  virtual void setVersion(const std::string & version) = 0;
  virtual void setRevision(const std::string & revision) = 0;

  virtual bool hasVersion() const = 0;
  virtual bool hasRevision() const = 0;

  virtual const std::string * const getVersion() const = 0;
  virtual const std::string * const getRevision() const = 0;
};

inline IAdministrativeInformation::~IAdministrativeInformation() = default;

}
}
}
#endif //BASYX_API_V2_SDK_IADMINISTRATIVEINFORMATION_H
