/*
 * IIdentifiable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IIdentifiable_H_
#define BASYX_METAMODEL_IIdentifiable_H_

#include <BaSyx/submodel/api/identifier/IIdentifier.h>
#include <BaSyx/submodel/api/qualifier/IAdministrativeInformation.h>
#include <BaSyx/shared/types.h>

#include <BaSyx/submodel/api/qualifier/IReferable.h>

#include <string>
#include <memory>

namespace basyx {
namespace submodel {

class IIdentifiable 
  : public virtual IReferable
{
public:
  struct Path
  {
    static constexpr char Administration[] = "administration";
    static constexpr char Identification[] = "identification";
  };
public:
  virtual ~IIdentifiable() = default;

  virtual std::shared_ptr<IAdministrativeInformation> getAdministration() const = 0;
  virtual std::shared_ptr<IIdentifier> getIdentification() const = 0;
};

}
}

#endif
