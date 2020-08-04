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
  , public Identifiable
{
private:
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

  //not inherited
  void addIsCaseOf(std::unique_ptr<simple::Reference> reference);
  void addEmbeddedDataSpecification(std::unique_ptr<DataSpecification> data_specification);
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_PARTS_CONCEPTDESCRIPTION_H */
