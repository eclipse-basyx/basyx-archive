#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_PROPERTY_REFERENCEELEMENT_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_PROPERTY_REFERENCEELEMENT_H

#include <BaSyx/submodel/api_v2/submodelelement/property/IReferenceElement.h>
#include <BaSyx/submodel/map_v2/common/ModelType.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>
#include <BaSyx/submodel/map_v2/submodelelement/DataElement.h>

namespace basyx {
namespace submodel {
namespace map {

class ReferenceElement
  : public virtual api::IReferenceElement
  , public DataElement
  , public ModelType<ModelTypes::ReferenceElement>
{
public:
  struct Path {
    static constexpr char Value[] = "value";
    static constexpr char Kind[] = "kind";
  };
private:
    std::unique_ptr<Reference> value;
public:
    ReferenceElement(const std::string& idShort, ModelingKind kind = ModelingKind::Instance);
    ReferenceElement(basyx::object);

    virtual ~ReferenceElement() = default;

    virtual const api::IReference* const getValue() const override;
    virtual void setValue(const api::IReference& value) override;

    virtual KeyElements getKeyElementType() const override { return KeyElements::ReferenceElement; };
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_PROPERTY_REFERENCEELEMENT_H */
