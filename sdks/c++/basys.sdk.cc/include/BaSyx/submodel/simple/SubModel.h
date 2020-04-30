#ifndef BASYX_SUBMODEL_SIMPLE_SUBMODEL_H
#define BASYX_SUBMODEL_SIMPLE_SUBMODEL_H


#include <BaSyx/submodel/api_v2/ISubModel.h>
#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>
#include <BaSyx/submodel/simple/qualifier/Identifiable.h>
#include <BaSyx/submodel/simple/qualifier/HasDataSpecification.h>

#include <BaSyx/submodel/simple/common/ElementContainer.h>

#include <memory>
#include <vector>
#include <unordered_map>


namespace basyx {
namespace submodel {
namespace simple {


class SubModel : public api::ISubModel
{
private:
	Identifiable identifiable;
	Kind kind;
	HasDataSpecification dataSpecification;
	Reference semanticId;

	ElementContainer<api::ISubmodelElement> elementContainer;
public:
	SubModel(const std::string & idShort, const Identifier & identifier);

	virtual ~SubModel() = default;
public:
	virtual api::IElementContainer<api::ISubmodelElement> & submodelElements();

	// Inherited via IHasKind
	virtual Kind getKind() const override;

	// Inherited via IReferable
	virtual const std::string & getIdShort() const override;
	virtual const std::string * const getCategory() const override;
	virtual simple::LangStringSet & getDescription() override;
	virtual const simple::LangStringSet & getDescription() const override;
	virtual const IReferable * const getParent() const override;

	// Inherited via IIdentifiable
	virtual const AdministrativeInformation & getAdministrativeInformation() const override;
	virtual AdministrativeInformation & getAdministrativeInformation() override;

	virtual Identifier getIdentification() const override;

	virtual bool hasAdministrativeInformation() const;

	// Inherited via IHasDataSpecification
	virtual void addDataSpecification(const Reference & reference) override;
	virtual const std::vector<Reference> getDataSpecificationReference() const override;

	// Inherited via IHasSemantics
	virtual api::IReference & getSemanticId() override;
};


}
}
}


#endif /* BASYX_SUBMODEL_SIMPLE_SUBMODEL_H */
