/*
 * DataElement.h
 *
 *      Author: wendel
 */

#ifndef IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_DATAELEMENT_H_
#define IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_DATAELEMENT_H_

#include "SubmodelElement.h"
#include "submodel/api/submodelelement/IDataElement.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace submodelelement {

class DataElement : public SubmodelElement, public aas::submodelelement::IDataElement
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
  DataElement(const basyx::object::object_map_t & map);
};

}
}
}
}
}

#endif
