/*
 * IReferenceElement.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IREFERENCEELEMENT_H_
#define BASYX_METAMODEL_IREFERENCEELEMENT_H_

#include <BaSyx/submodel/api/reference/IReference.h>
#include <BaSyx/submodel/api/submodelelement/property/IProperty.h>

namespace basyx {
namespace submodel {

class IReferenceElement
{
public:
  struct Path
  {
    static constexpr char Modeltype[] = "ReferenceElement";
  };

public:
  virtual ~IReferenceElement() = default;

  virtual std::shared_ptr<IReference> getValue() const = 0;
};

}
}

#endif
