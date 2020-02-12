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

#include <vector>

namespace basyx {
namespace aas {

class IView : 
	public submodel::IHasDataSpecification,
	public submodel::IHasSemantics, 
	public submodel::IReferable
{
public:
	

	virtual ~IView() = default;

	virtual void setContainedElement(std::vector<submodel::IReference> references) = 0;
	virtual std::vector<submodel::IReference> getContainedElement() const = 0;
};

}
}

#endif

