/*
 * DataElement.h
 *
 *      Author: wendel
 */

#ifndef IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_DATAELEMENT_H_
#define IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_DATAELEMENT_H_

#include <BaSyx/submodel/map/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/api/submodelelement/IDataElement.h>

namespace basyx {
namespace submodel {

class DataElement :
  public virtual vab::ElementMap,
	public virtual SubmodelElement, 
	public virtual IDataElement
{
public:
	~DataElement() = default;

	// constructors
	DataElement();
	DataElement(basyx::object object);
  DataElement(const IDataElement & other);
};

}
}

#endif
