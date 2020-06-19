#ifndef BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_PROPERTY_REFERENCEELEMENT_H
#define BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_PROPERTY_REFERENCEELEMENT_H

#include <BaSyx/submodel/api_v2/submodelelement/property/IReferenceElement.h>
#include <BaSyx/submodel/simple/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/simple/reference/Reference.h>

namespace basyx {
namespace submodel {
namespace simple {


class ReferenceElement : 
	public api::IReferenceElement,
	public SubmodelElement
{
private:
	Reference value;
public:
	ReferenceElement(const std::string & idShort, ModelingKind kind = ModelingKind::Instance);
	virtual ~ReferenceElement() = default;

	virtual const api::IReference * const getValue() const = 0;
	virtual void setValue(const api::IReference & value) = 0;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_PROPERTY_REFERENCEELEMENT_H */
