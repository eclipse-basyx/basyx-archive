/*
 * IDataElement.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IDATAELEMENT_H_
#define BASYX_METAMODEL_IDATAELEMENT_H_


#include "submodel/api/submodelelement/ISubmodelElement.h"

namespace basyx {
namespace submodel {

class IDataElement : public virtual ISubmodelElement
{
public:
  virtual ~IDataElement() = 0;
};

inline IDataElement::~IDataElement() = default;

}
}

#endif
