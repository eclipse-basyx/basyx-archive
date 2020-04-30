#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_PROPERTY_IREFERENCEELEMENT_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_PROPERTY_IREFERENCEELEMENT_H

#include <BaSyx/submodel/api_v2/submodelelement/IDataElement.h>
#include <BaSyx/submodel/api_v2/reference/IReference.h>

namespace basyx {
namespace submodel {
namespace api {


class IReferenceElement : public virtual IDataElement
{
public:
	virtual ~IReferenceElement() = 0;

	virtual const IReference * const getValue() const = 0;
	virtual void setValue(const IReference & value) = 0;
};

inline IReferenceElement::~IReferenceElement() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_PROPERTY_IREFERENCEELEMENT_H */
