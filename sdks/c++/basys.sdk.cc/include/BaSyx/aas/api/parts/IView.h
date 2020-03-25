/*
 * IView.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IView_H_
#define BASYX_METAMODEL_IView_H_


#include <BaSyx/submodel/api/qualifier/IHasSemantics.h>
#include <BaSyx/submodel/api/qualifier/IHasDataSpecification.h>
#include <BaSyx/submodel/api/qualifier/IReferable.h>
#include <BaSyx/submodel/api/reference/IReference.h>

namespace basyx {
namespace aas {

class IView : 
	public submodel::IHasDataSpecification,
	public submodel::IHasSemantics, 
	public submodel::IReferable
{
public:
  struct Path
  {
    static constexpr char ContainedElement[] = "containedElement";
    static constexpr char ModelType[] = "View";
  };
public:
	virtual ~IView() = default;

	virtual void setContainedElements(const basyx::specificCollection_t<submodel::IReference> & references) = 0;
	virtual basyx::specificCollection_t<submodel::IReference> getContainedElements() const = 0;
};

}
}

#endif

