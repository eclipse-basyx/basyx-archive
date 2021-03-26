#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_SUBMODELELEMENT_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_SUBMODELELEMENT_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>

#include <BaSyx/submodel/map_v2/common/ModelType.h>
#include <BaSyx/submodel/map_v2/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/map_v2/qualifier/Qualifiable.h>
#include <BaSyx/submodel/map_v2/qualifier/Referable.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>

namespace basyx {
namespace submodel {
namespace map {

class SubmodelElement
  :	public virtual api::ISubmodelElement
  , public virtual vab::ElementMap
  , public virtual Qualifiable
  , public Referable
  , public HasDataSpecification
{
public:
  struct Path {
    static constexpr char Kind[] = "kind";
    static constexpr char SemanticId[] = "semanticId";
  };
private:
    Reference semanticId;

public:
    SubmodelElement(const std::string& idShort, ModelingKind kind = ModelingKind::Instance);

    virtual ~SubmodelElement() = default;

    // Inherited via IHasDataSemantics
    virtual const api::IReference& getSemanticId() const override;
    void setSemanticId(const api::IReference& reference) override;

    // Inherited via IHasKind
    virtual ModelingKind getKind() const override;

    virtual KeyElements getKeyElementType() const override { return KeyElements::SubmodelElement; };
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_SUBMODELELEMENT_H */
