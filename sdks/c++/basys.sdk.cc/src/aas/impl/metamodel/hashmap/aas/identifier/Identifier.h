/*
 * Identifier.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_IDENTIFIER_H_
#define AAS_IMPL_METAMODEL_IDENTIFIER_H_

#include "aas/identifier/IIdentifier.h"
#include "basyx/types.h"

namespace basyx {
namespace aas {
namespace identifier {
namespace impl {

class Identifier : public IIdentifier
{
public:
  ~Identifier() = default;

  Identifier();
  Identifier(const std::string & id, const std::string & idType);
  Identifier(const basyx::objectMap_t & map);

  // Inherited via IIdentifier
  virtual std::string getIdType() const override;
  virtual std::string getId() const override;

private:
  std::string id, idType;
};

}
}
}
}

#endif
