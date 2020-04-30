#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODEL_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODEL_H


#include <BaSyx/submodel/api_v2/ISubModel.h>
#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>
#include <BaSyx/submodel/map_v2/qualifier/Identifiable.h>
#include <BaSyx/submodel/map_v2/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>

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
	public virtual vab::ElementMap
{
private:
	Reference semanticId;
	ElementContainer<api::ISubmodelElement> elementContainer;
public:
	SubModel(const std::string & idShort, const simple::Identifier & identifier);

	virtual ~SubModel() = default;
public:
	virtual api::IElementContainer<api::ISubmodelElement> & submodelElements();

	// Inherited via IHasKind
	virtual Kind getKind() const override;

	// Inherited via IHasSemantics
	virtual api::IReference & getSemanticId() override;
};


}
}
}


#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODEL_H */
