/*
 * ReferenceElement.h
 *
 *      Author: wendel
 */

#ifndef IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_REFERENCEELEMENT_H_
#define IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_REFERENCEELEMENT_H_

#include <BaSyx/submodel/map/submodelelement/DataElement.h>
#include <BaSyx/submodel/api/submodelelement/IReferenceElement.h>

namespace basyx {
namespace submodel {

class ReferenceElement : 
  public virtual vab::ElementMap, 
  public DataElement, 
  public IReferenceElement
{
public:
  ~ReferenceElement() = default;

  // construtors
  ReferenceElement();
  ReferenceElement(const IReference & reference);
  ReferenceElement(const basyx::object & map);

  // Inherited via IReferenceElement
  virtual void setValue(const IReference & ref);
  virtual std::shared_ptr<IReference> getValue() const override;
};

}
}

#endif
