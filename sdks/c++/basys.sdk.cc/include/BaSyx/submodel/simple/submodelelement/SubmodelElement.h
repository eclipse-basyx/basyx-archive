#ifndef BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_SUBMODELELEMENT_H
#define BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_SUBMODELELEMENT_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>

#include <BaSyx/submodel/simple/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/api_v2/qualifier/IHasKind.h>
#include <BaSyx/submodel/simple/qualifier/Referable.h>


namespace basyx {
namespace submodel {
namespace simple {

class SubmodelElement : public virtual api::ISubmodelElement
{
private:
	HasDataSpecification dataSpecification;
	ModelingKind kind;
	Reference semanticId;
	Referable referable;
public:
	SubmodelElement(const std::string & idShort, ModelingKind kind = ModelingKind::Instance);

	virtual ~SubmodelElement() = default;

	// Inherited via IHasDataSemantics
	virtual const api::IReference & getSemanticId() const override;
	void setSemanticId(Reference reference);

	// Inherited via IHasDataSpecification
	virtual void addDataSpecification(const Reference & reference) override;
	virtual const std::vector<Reference> getDataSpecificationReference() const override;

	// Inherited via IReferable
	virtual const std::string & getIdShort() const override;
	virtual const std::string * const getCategory() const override;
	virtual simple::LangStringSet & getDescription() override;
	virtual const simple::LangStringSet & getDescription() const override;
	virtual IReferable * getParent() const override;

	// Inherited via IHasKind
	virtual ModelingKind getKind() const override;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_SUBMODELELEMENT_H */
