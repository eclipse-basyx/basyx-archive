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

class IReferenceElement : public property::IProperty
{
public:
  virtual ~IReferenceElement() = default;
};

}
}
}

#endif
