#ifndef BASYX_SUBMODEL_SIMPLE_PARTS_CONCEPTDICTIONARY_H
#define BASYX_SUBMODEL_SIMPLE_PARTS_CONCEPTDICTIONARY_H

#include <BaSyx/submodel/api_v2/parts/IConceptDictionary.h>

#include <BaSyx/vab/ElementMap.h>
#include <BaSyx/submodel/simple/qualifier/Referable.h>
#include <BaSyx/submodel/simple/common/ElementContainer.h>
#include <BaSyx/submodel/simple/parts/ConceptDescription.h>

namespace basyx {
namespace submodel {
namespace simple {

class ConceptDictionary
    : public api::IConceptDictionary
{
private:
	Referable referable;
	ElementContainer<api::IConceptDescription> conceptDescriptions;
public:
	ConceptDictionary(const std::string & idShort);

	//inherited via IConceptDictionary
	const api::IElementContainer<api::IConceptDescription> & getConceptDescriptions() const override;

	// inherited via IReferable
	virtual const std::string & getIdShort() const override;
	virtual const std::string * const getCategory() const override;
	virtual void setCategory(const std::string & category) override;
	virtual simple::LangStringSet & getDescription() override;
	virtual const simple::LangStringSet & getDescription() const override;
	virtual void setParent(api::IReferable * parent) override;
	virtual IReferable * getParent() const override;
	virtual simple::Reference getReference() const override;

	//not inherited
	void addConceptDescription(std::unique_ptr<ConceptDescription> description);

        virtual Key getKey(bool local = true) const override { return referable.getKey(); }
	virtual KeyElements getKeyElementType() const override { return KeyElements::ConceptDictionary; };
};

}
}
}
#endif /* BASYX_SUBMODEL_SIMPLE_PARTS_CONCEPTDICTIONARY_H */
