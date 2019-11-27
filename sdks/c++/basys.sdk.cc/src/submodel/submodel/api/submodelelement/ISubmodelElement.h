/*
 * ISubmodelElement.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_ISUBMODELELEMENT_H_
#define BASYX_METAMODEL_ISUBMODELELEMENT_H_


#include "submodel/api/IElement.h"
#include "submodel/api/qualifier/IHasDataSpecification.h"
#include "submodel/api/qualifier/IReferable.h"
#include "submodel/api/qualifier/qualifiable/IQualifiable.h"
#include "submodel/api/qualifier/IHasSemantics.h"
#include "submodel/api/qualifier/IHasKind.h"

namespace basyx {
namespace submodel {

class ISubmodelElement : 
	public virtual IHasDataSpecification, 
	public virtual IReferable,
	public virtual IQualifiable,
	public virtual IHasSemantics,
	public virtual IHasKind
{
public:
	struct Path {
		static constexpr char ModelType[] = "SubmodelElement";
	};
public:
  virtual ~ISubmodelElement() = default;
};

}
}

#endif
