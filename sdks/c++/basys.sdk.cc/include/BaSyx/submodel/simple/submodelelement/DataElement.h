#ifndef BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_DATAELEMENT_H
#define BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_DATAELEMENT_H

#include <BaSyx/submodel/api_v2/submodelelement/IDataElement.h>
#include <BaSyx/submodel/simple/submodelelement/SubmodelElement.h>

namespace basyx {
namespace submodel {
namespace simple {

class DataElement
  : public virtual api::IDataElement
  , public SubmodelElement
{
public:
	~DataElement() = default;

	DataElement(const std::string & idShort, ModelingKind kind = ModelingKind::Instance);
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_DATAELEMENT_H */
