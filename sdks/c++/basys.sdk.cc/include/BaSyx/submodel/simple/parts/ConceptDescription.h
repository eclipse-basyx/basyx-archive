#ifndef BASYX_SUBMODEL_SIMPLE_PARTS_CONCEPTDESCRIPTION_H
#define BASYX_SUBMODEL_SIMPLE_PARTS_CONCEPTDESCRIPTION_H

#include <BaSyx/submodel/api_v2/parts/IConceptDescription.h>
#include <BaSyx/submodel/simple/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/simple/qualifier/Identifiable.h>
#include <BaSyx/submodel/simple/common/ElementContainer.h>
#include <BaSyx/submodel/simple/dataspecification/DataSpecification.h>


namespace basyx {
namespace submodel {
namespace simple {

class ConceptDescription
  : public virtual api::IConceptDescription
{
private:
	Identifiable identifiable;
	HasDataSpecification dataSpec;
	std::vector<std::unique_ptr<api::IReference>> isCaseOf;
	ElementContainer<api::IDataSpecification> embeddedDataSpecs;
public:
	ConceptDescription(const std::string & idShort, const Identifier & identifier);

	~ConceptDescription() override = default;

	// Inherited via IConceptDescription
	const std::vector<std::unique_ptr<api::IReference>> & getIsCaseOf() const override;
	const api::IElementContainer<api::IDataSpecification> & getEmbeddedDataSpecification() const override;

	// Inherited via IHasDataSpecification
	void addDataSpecification(const Reference & reference) override;
	const std::vector<Reference> getDataSpecificationReference() const override;

	// Inherited via IReferable
	virtual const std::string & getIdShort() const override;
	virtual const std::string * const getCategory() const override;
	virtual void setCategory(const std::string & category) override;
	virtual simple::LangStringSet & getDescription() override;
	virtual const simple::LangStringSet & getDescription() const override;
	virtual void setParent(api::IReferable * parent) override;
	virtual IReferable * getParent() const override;
	virtual simple::Reference getReference() const override;

	// Inherited via IIdentifiable
	virtual const AdministrativeInformation & getAdministrativeInformation() const override;
	virtual AdministrativeInformation & getAdministrativeInformation() override;

	virtual Identifier getIdentification() const override;

	virtual bool hasAdministrativeInformation() const override;

	//not inherited
	void addIsCaseOf(std::unique_ptr<simple::Reference> reference);
	void addEmbeddedDataSpecification(std::unique_ptr<DataSpecification> data_specification);

        virtual Key getKey(bool local = true) const override { return identifiable.getKey(); }
	virtual KeyElements getKeyElementType() const override { return KeyElements::ConceptDescription; };
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_PARTS_CONCEPTDESCRIPTION_H */
