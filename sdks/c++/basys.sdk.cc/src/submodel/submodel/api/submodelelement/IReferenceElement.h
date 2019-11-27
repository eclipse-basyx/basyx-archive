/*
 * IReferenceElement.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IREFERENCEELEMENT_H_
#define BASYX_METAMODEL_IREFERENCEELEMENT_H_

#include "submodel/api/reference/IReference.h"
#include "submodel/api/submodelelement/property/IProperty.h"

namespace basyx {
namespace aas {
namespace submodelelement {

class IReferenceElement
{
public:
  virtual ~IReferenceElement() = default;

  virtual void setValue(const std::shared_ptr<reference::IReference> & ref) = 0;
  virtual std::shared_ptr<reference::IReference> getValue() const = 0;
};

}
}
}

#endif
