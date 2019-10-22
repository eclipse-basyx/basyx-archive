/*
 * IIdentifiable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IIdentifiable_H_
#define BASYX_METAMODEL_IIdentifiable_H_

#include "aas/identifier/IIdentifier.h"
#include "IAdministrativeInformation.h"
#include "basyx/types.h"

#include "aas/qualifier/IReferable.h"

#include <string>

namespace basyx {
namespace aas {
namespace qualifier {

class IIdentifiable : public IReferable
{
public:
  virtual ~IIdentifiable() = default;

  virtual basyx::any getAdministration() const = 0;
  virtual basyx::any getIdentification() const = 0;

};

}
}
}

#endif
