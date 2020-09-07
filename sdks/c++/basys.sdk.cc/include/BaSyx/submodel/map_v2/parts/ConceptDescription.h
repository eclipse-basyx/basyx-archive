#ifndef BASYX_SUBMODEL_MAP_V2_PARTS_CONCEPTDESCRIPTION_H
#define BASYX_SUBMODEL_MAP_V2_PARTS_CONCEPTDESCRIPTION_H

#include <BaSyx/vab/ElementMap.h>
#include <BaSyx/submodel/api_v2/parts/IConceptDescription.h>
#include <BaSyx/submodel/api_v2/dataspecification/IDataSpecification.h>
#include <BaSyx/submodel/map_v2/qualifier/Identifiable.h>
#include <BaSyx/submodel/map_v2/common/ElementContainer.h>
#include <BaSyx/submodel/map_v2/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/map_v2/dataspecification/DataSpecification.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>


namespace basyx {
namespace submodel {
namespace map {

struct ConceptDescriptionPath
{
  static constexpr char ModelType[] = "ConceptDescription";
  static constexpr char IsCaseOf[] = "isCaseOf";
  static constexpr char EmbeddedDataSpecifications[] = "embeddedDataSpecifications";
};

class ConceptDescription
  : public api::IConceptDescription
  , public virtual Identifiable
  , public virtual HasDataSpecification
{
private:
  std::vector<std::unique_ptr<api::IReference>> is_case_of_refs;
  ElementContainer<api::IDataSpecification> embedded_data_specs;

public:
  ConceptDescription(const std::string & idShort,  const simple::Identifier & identifier);

  const std::vector<std::unique_ptr<api::IReference>> & getIsCaseOf() const override;
  const api::IElementContainer<api::IDataSpecification> & getEmbeddedDataSpecification() const override;

  //not inherited
  void addIsCaseOf(std::unique_ptr<Reference> reference);
  void addEmbeddedDataSpecification(std::unique_ptr<DataSpecification> data_specification);

  virtual KeyElements getKeyElementType() const override { return KeyElements::ConceptDescription; };
};

}
}
}
#endif /* BASYX_SUBMODEL_MAP_V2_PARTS_CONCEPTDESCRIPTION_H */
