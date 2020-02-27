/*
 * ISubmodelElement.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_ISUBMODELELEMENT_H_
#define BASYX_METAMODEL_ISUBMODELELEMENT_H_


#include <BaSyx/submodel/api/IElement.h>
#include <BaSyx/submodel/api/qualifier/IHasDataSpecification.h>
#include <BaSyx/submodel/api/qualifier/IReferable.h>
#include <BaSyx/submodel/api/qualifier/qualifiable/IQualifiable.h>
#include <BaSyx/submodel/api/qualifier/IHasSemantics.h>
#include <BaSyx/submodel/api/qualifier/IHasKind.h>

namespace basyx {
namespace submodel {

class ISubmodelElement :
//	public virtual IElement,
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
