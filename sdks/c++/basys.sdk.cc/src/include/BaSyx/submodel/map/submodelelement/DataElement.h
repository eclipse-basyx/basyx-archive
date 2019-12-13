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
	public SubmodelElement, 
	public virtual IDataElement
{
public:
	~DataElement() = default;

	// constructors
	DataElement();

	/**
	* Constructs a DataElement from a given list, provided that all needed elements present in list.
	*
	* @param map the map containig all elements.
	*/
	DataElement(basyx::object object);
};

}
}

#endif
