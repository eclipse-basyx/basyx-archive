#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODEL_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODEL_H


#include <BaSyx/submodel/api_v2/ISubModel.h>
#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>
#include <BaSyx/submodel/map_v2/qualifier/Identifiable.h>
#include <BaSyx/submodel/map_v2/qualifier/Qualifiable.h>
#include <BaSyx/submodel/map_v2/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>

#include <BaSyx/submodel/map_v2/common/ModelType.h>
#include <BaSyx/submodel/map_v2/common/ElementContainer.h>

#include <memory>
#include <vector>
#include <unordered_map>


namespace basyx {
namespace submodel {
namespace map {

class SubModel : 
	public virtual api::ISubModel,
	public Identifiable,
	public HasDataSpecification,
	public Qualifiable,
	public ModelType<ModelTypes::Submodel>,
	public virtual vab::ElementMap
{
public:
  struct Path {
    static constexpr char SubmodelElements[] = "submodelElements";
    static constexpr char SemanticId[] = "semanticId";
    static constexpr char Kind[] = "kind";
  };
private:
	Reference semanticId;
	ElementContainer<api::ISubmodelElement> elementContainer;
public:
	SubModel(const std::string & idShort, const simple::Identifier & identifier, ModelingKind kind = ModelingKind::Instance);

	virtual ~SubModel() = default;
public:
	virtual api::IElementContainer<api::ISubmodelElement> & submodelElements() override;
	virtual const api::IElementContainer<api::ISubmodelElement> & submodelElements() const override;

	// Inherited via IHasKind
	virtual ModelingKind getKind() const override;

	// Inherited via IHasSemantics
	virtual const api::IReference & getSemanticId() const override;
	virtual void setSemanticId(const api::IReference & semanticId) override;

	virtual KeyElements getKeyElementType() const override { return KeyElements::Submodel; };
};


}
}
}


#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODEL_H */
