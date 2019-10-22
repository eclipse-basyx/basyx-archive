/*
 * IReferenceElement.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IREFERENCEELEMENT_H_
#define BASYX_METAMODEL_IREFERENCEELEMENT_H_

#include "aas/reference/IReference.h"
#include "aas/submodelelement/property/IProperty.h"


namespace basyx {
namespace aas {
namespace submodelelement {
namespace property {

class IReferenceElement
{
public:
  virtual ~IReferenceElement() = default;

  virtual void setValue(const basyx::any & ref) = 0;
  virtual basyx::any getValue() const = 0;
};

}
}
}
}

#endif
