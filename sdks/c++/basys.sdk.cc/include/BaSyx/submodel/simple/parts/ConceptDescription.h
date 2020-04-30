#ifndef BASYX_SUBMODEL_SIMPLE_PARTS_CONCEPTDESCRIPTION_H
#define BASYX_SUBMODEL_SIMPLE_PARTS_CONCEPTDESCRIPTION_H

#include <BaSyx/submodel/api_v2/parts/IConceptDescription.h>
#include <BaSyx/submodel/simple/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/simple/qualifier/Identifiable.h>
#include <BaSyx/submodel/simple/common/ElementContainer.h>


namespace basyx {
namespace submodel {
namespace simple {

class ConceptDescription : public api::IConceptDescription
{
public:
	HasDataSpecification dataSpec;
	Identifiable ident;
	std::vector<Reference> isCaseOf;
	ElementContainer<api::IDataSpecification> embeddedDataSpecs;
public:
	ConceptDescription(const std::string & idShort, const Identifier & identifier);

	virtual ~ConceptDescription() = default;

	// Inherited via IConceptDescription
	virtual std::vector<Reference> & getIsCaseOf() override;
	virtual api::IElementContainer<api::IDataSpecification> & getEmbeddedDataSpecification() override;

	// Inherited via IHasDataSpecification
	virtual void addDataSpecification(const Reference & reference) override;
	virtual const std::vector<Reference> getDataSpecificationReference() const override;

	// Inherited via IIdentifiable
	virtual const std::string & getIdShort() const override;
	virtual const std::string * const getCategory() const override;
	virtual simple::LangStringSet & getDescription() override;
	virtual const simple::LangStringSet & getDescription() const override;
	virtual const IReferable * const getParent() const override;
	virtual const AdministrativeInformation & getAdministrativeInformation() const override;
	virtual AdministrativeInformation & getAdministrativeInformation() override;
	virtual Identifier getIdentification() const override;
	virtual bool hasAdministrativeInformation() const override;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_PARTS_CONCEPTDESCRIPTION_H */
