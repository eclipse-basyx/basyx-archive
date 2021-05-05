#ifndef BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_PROPERTY_REFERENCEELEMENT_H
#define BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_PROPERTY_REFERENCEELEMENT_H

#include <BaSyx/submodel/api_v2/submodelelement/property/IReferenceElement.h>
#include <BaSyx/submodel/simple/submodelelement/DataElement.h>
#include <BaSyx/submodel/simple/reference/Reference.h>

namespace basyx {
namespace submodel {
namespace simple {


class ReferenceElement
  : public virtual api::IReferenceElement
  , public DataElement
{
private:
	Reference value;
public:
	ReferenceElement(const std::string & idShort, ModelingKind kind = ModelingKind::Instance);
	virtual ~ReferenceElement() = default;

	virtual const api::IReference * const getValue() const override;
	virtual void setValue(const api::IReference & value) override;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_PROPERTY_REFERENCEELEMENT_H */
