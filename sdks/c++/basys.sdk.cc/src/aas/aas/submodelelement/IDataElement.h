/*
 * IDataElement.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IDATAELEMENT_H_
#define BASYX_METAMODEL_IDATAELEMENT_H_


#include "aas/submodelelement/ISubmodelElement.h"

namespace basyx {
namespace aas {
namespace submodelelement {

class IDataElement : public ISubmodelElement
{
public:
  virtual ~IDataElement() = default;
};

}
}
}

#endif
