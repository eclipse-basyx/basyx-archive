/*
 * SubmodelElement.h
 *
 *      Author: wendel
 */

#ifndef IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_SUBMODELELEMENT_H_
#define IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_SUBMODELELEMENT_H_

#include <BaSyx/submodel/api/submodelelement/ISubmodelElement.h>

#include <BaSyx/submodel/map/qualifier/HasKind.h>
#include <BaSyx/submodel/map/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/map/qualifier/HasSemantics.h>
#include <BaSyx/submodel/map/qualifier/Referable.h>
#include <BaSyx/submodel/map/qualifier/qualifiable/Qualifiable.h>
#include <BaSyx/submodel/map/modeltype/ModelType.h>

#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {

class SubmodelElement :
  public virtual vab::ElementMap,
	public virtual ModelType,
	public virtual HasDataSpecification,
	public virtual HasKind,
	public virtual HasSemantics,
	public virtual Qualifiable,
	public virtual Referable,
	public virtual ISubmodelElement
{
public:
	~SubmodelElement() = default;

	// constructors
	SubmodelElement();

	using vab::ElementMap::ElementMap;

	/**
	* Constructs an SubmodelElement object from a map given that the map contains all required elements.
	* 
	* @param submodelElementMap the map representig the submodel.
	*/
//	SubmodelElement(basyx::object object);

  SubmodelElement(const ISubmodelElement & abstractSubmodelElement);
};

}
}

#endif
