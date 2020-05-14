#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_SUBMODELELEMENT_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_SUBMODELELEMENT_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>

#include <BaSyx/submodel/map_v2/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/map_v2/qualifier/Referable.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>


namespace basyx {
namespace submodel {
namespace map {

class SubmodelElement : 
	public virtual api::ISubmodelElement,
	public virtual vab::ElementMap,
	public Referable,
	public HasDataSpecification
{
private:
	Reference semanticId;
public:
	using vab::ElementMap::ElementMap;
	SubmodelElement(const std::string & idShort, Kind kind = Kind::Instance);

	virtual ~SubmodelElement() = default;

	// Inherited via IHasDataSemantics
	virtual const api::IReference & getSemanticId() const override;
	void setSemanticId(const api::IReference & reference);

	// Inherited via IHasKind
	virtual Kind getKind() const override;
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_SUBMODELELEMENT_H */
