#ifndef BASYX_SUBMODEL_API_V2_QUALIFIER_IIDENTIFIABLE_H
#define BASYX_SUBMODEL_API_V2_QUALIFIER_IIDENTIFIABLE_H

#include <BaSyx/submodel/simple/identifier/Identifier.h>
//#include <BaSyx/submodel/api_v2/qualifier/IAdministrativeInformation.h>

#include <BaSyx/submodel/api_v2/qualifier/IReferable.h>

#include <string>
#include <memory>

namespace basyx {
namespace submodel {

namespace simple { class AdministrativeInformation; }

namespace api {

class IIdentifiable : public virtual IReferable
{
public:
  virtual ~IIdentifiable() = default;

  virtual const simple::AdministrativeInformation & getAdministrativeInformation() const = 0;
  virtual simple::AdministrativeInformation & getAdministrativeInformation() = 0;

  virtual simple::Identifier getIdentification() const = 0;

  virtual bool hasAdministrativeInformation() const = 0;
};

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_QUALIFIER_IIDENTIFIABLE_H */
