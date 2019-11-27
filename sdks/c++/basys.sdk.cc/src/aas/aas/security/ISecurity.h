/*
 * ISecurity.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_ISecurity_H_
#define BASYX_METAMODEL_ISecurity_H_

#include "basyx/object.h"

namespace basyx {
namespace aas {
namespace security {


class ISecurity
{
public:
  virtual ~ISecurity() = default;

  virtual basyx::object getAccessControlPolicyPoints() const = 0;
  virtual void setAccessControlPolicyPoints(const basyx::object & obj) = 0;

  virtual basyx::object getTrustAnchor() const = 0;
  virtual void setTrustAnchor(const basyx::object & obj) = 0;
};

}
}
}

#endif
