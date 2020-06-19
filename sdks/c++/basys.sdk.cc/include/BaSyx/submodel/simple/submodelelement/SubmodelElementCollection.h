#ifndef BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_SUBMODELELEMENTCOLLECTION_H
#define BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_SUBMODELELEMENTCOLLECTION_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElementCollection.h>
#include <BaSyx/submodel/simple/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/simple/common/ElementContainer.h>


namespace basyx {
namespace submodel {
namespace simple {


class SubmodelElementCollection : 
	public SubmodelElement,
	public api::ISubmodelElementCollection
{
private:
	ElementContainer<ISubmodelElement> elementContainer;
	bool ordered;
	bool allowDuplicates;
public:
	SubmodelElementCollection(const std::string & idShort, ModelingKind = ModelingKind::Instance, bool ordered = false, bool allowDuplicates = false);
	virtual ~SubmodelElementCollection() = default;

	virtual api::IElementContainer<ISubmodelElement> & getSubmodelElements() override;

	virtual bool isOrdered() const override;
	virtual bool isAllowDuplicates() const override;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_SUBMODELELEMENTCOLLECTION_H */
