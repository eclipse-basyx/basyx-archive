/*
 * Security.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_SECURITY_SECURITY_H_
#define AAS_IMPL_METAMODEL_HASHMAP_SECURITY_SECURITY_H_

#include "aas/security/ISecurity.h"
#include "basyx/types.h"

namespace basyx {
namespace aas {
namespace security {

class Security : public ISecurity
{
public:
  ~Security() = default;

  // Inherited via ISecurity
  virtual basyx::object getAccessControlPolicyPoints() const override;
  virtual void setAccessControlPolicyPoints(const basyx::object & obj) override;
  virtual basyx::object getTrustAnchor() const override;
  virtual void setTrustAnchor(const basyx::object & obj) override;
};

}
}
}

#endif
