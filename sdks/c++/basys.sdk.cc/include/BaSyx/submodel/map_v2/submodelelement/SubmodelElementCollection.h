#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_SUBMODELELEMENTCOLLECTION_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_SUBMODELELEMENTCOLLECTION_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElementCollection.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map_v2/common/ElementContainer.h>


namespace basyx {
namespace submodel {
namespace map {


class SubmodelElementCollection : 
	public SubmodelElement,
	public api::ISubmodelElementCollection
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
	SubmodelElementCollection(const std::string & idShort, Kind = Kind::Instance, bool ordered = false, bool allowDuplicates = false);
	virtual ~SubmodelElementCollection() = default;

	virtual api::IElementContainer<ISubmodelElement> & getSubmodelElements() override;

	virtual bool isOrdered() const override;
	virtual bool isAllowDuplicates() const override;
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_SUBMODELELEMENTCOLLECTION_H */
