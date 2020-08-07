#ifndef BASYX_SUBMODEL_API_V2_QUALIFIER_IIDENTIFIABLE_H
#define BASYX_SUBMODEL_API_V2_QUALIFIER_IIDENTIFIABLE_H

#include <BaSyx/submodel/simple/identifier/Identifier.h>
#include <BaSyx/submodel/api_v2/qualifier/IAdministrativeInformation.h>

#include <BaSyx/submodel/api_v2/qualifier/IReferable.h>

#include <string>
#include <memory>

namespace basyx {
namespace submodel {
namespace api {

class IIdentifiable : public virtual IReferable
{
public:
  virtual ~IIdentifiable() = 0;

  virtual const IAdministrativeInformation & getAdministrativeInformation() const = 0;
  virtual IAdministrativeInformation & getAdministrativeInformation() = 0;

  virtual simple::Identifier getIdentification() const = 0;

  virtual bool hasAdministrativeInformation() const = 0;
};

inline IIdentifiable::~IIdentifiable() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_QUALIFIER_IIDENTIFIABLE_H */
