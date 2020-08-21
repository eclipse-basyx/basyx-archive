#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_SUBMODELELEMENTCOLLECTION_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_SUBMODELELEMENTCOLLECTION_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElementCollection.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map_v2/common/ElementContainer.h>
#include <BaSyx/submodel/map_v2/common/ModelType.h>


namespace basyx {
namespace submodel {
namespace map {


class SubmodelElementCollection : 
	public api::ISubmodelElementCollection,
	public SubmodelElement,
	public ModelType<ModelTypes::SubmodelElementCollection>
{
public:
	struct Path {
		static constexpr char Value[] = "value";
		static constexpr char Ordered[] = "ordered";
		static constexpr char AllowDuplicates[] = "allowDuplicates";
	};
private:
	ElementContainer<ISubmodelElement> elementContainer;
public:
	SubmodelElementCollection(const std::string & idShort, ModelingKind Kind = ModelingKind::Instance, bool ordered = false, bool allowDuplicates = false);
	virtual ~SubmodelElementCollection() = default;

	virtual api::IElementContainer<ISubmodelElement> & getSubmodelElements() override;

	virtual bool isOrdered() const override;
	virtual bool isAllowDuplicates() const override;

	virtual KeyElements getKeyElementType() const override { return KeyElements::SubmodelElementCollection; };
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_SUBMODELELEMENTCOLLECTION_H */
